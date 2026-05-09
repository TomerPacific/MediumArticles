package com.example.contactpicker

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactPickerViewModel(application: Application) : AndroidViewModel(application) {

    var selectedContactNames by mutableStateOf<List<String>>(emptyList())
        private set

    var showLegacyPicker by mutableStateOf(false)
        private set

    val legacyContacts = mutableStateListOf<ContactEntry>()

    private val context: Context get() = getApplication<Application>().applicationContext

    fun onModernContactsPicked(uris: List<Uri>) {
        viewModelScope.launch {
            val names = withContext(Dispatchers.IO) {
                uris.mapNotNull { contactUri ->
                    resolveContactName(contactUri)
                }
            }
            selectedContactNames = names
        }
    }

    fun onLegacySelectionComplete(selected: List<ContactEntry>) {
        selectedContactNames = selected.map { it.name }
        showLegacyPicker = false
    }

    fun openLegacyPicker() {
        viewModelScope.launch {
            val contacts = withContext(Dispatchers.IO) {
                loadAllContacts()
            }
            legacyContacts.clear()
            legacyContacts.addAll(contacts)
            showLegacyPicker = true
        }
    }

    fun closeLegacyPicker() {
        showLegacyPicker = false
    }

    fun toggleLegacyContactSelection(index: Int) {
        if (index in legacyContacts.indices) {
            val contact = legacyContacts[index]
            legacyContacts[index] = contact.copy(isSelected = !contact.isSelected)
        }
    }

    private fun resolveContactName(contactUri: Uri): String? {
        val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
        return context.contentResolver.query(contactUri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                if (nameIndex != -1) cursor.getString(nameIndex) else null
            } else null
        }
    }

    private fun loadAllContacts(): List<ContactEntry> {
        val list = mutableListOf<ContactEntry>()
        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME),
            null, null, "${ContactsContract.Contacts.DISPLAY_NAME} ASC"
        )

        cursor?.use {
            val idIdx = it.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIdx = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            while (it.moveToNext()) {
                val id = it.getString(idIdx)
                val name = it.getString(nameIdx) ?: "Unknown"
                list.add(ContactEntry(id, name))
            }
        }
        return list
    }
}
