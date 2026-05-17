package com.example.contactpicker

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import androidx.compose.runtime.derivedStateOf
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

    var selectedContacts by mutableStateOf<List<ContactEntry>>(emptyList())
        private set

    var showLegacyPicker by mutableStateOf(false)
        private set

    var searchQuery by mutableStateOf("")
        private set

    private val legacyContacts = mutableStateListOf<ContactEntry>()

    val filteredLegacyContacts by derivedStateOf {
        if (searchQuery.isEmpty()) legacyContacts
        else legacyContacts.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    private val context: Context get() = getApplication<Application>().applicationContext

    fun onModernContactsPicked(uris: List<Uri>) {
        viewModelScope.launch {
            val contacts = withContext(Dispatchers.IO) {
                uris.flatMap { contactUri -> resolveContactDetails(contactUri) }
            }
            selectedContacts = contacts
        }
    }

    fun onLegacySelectionComplete(selected: List<ContactEntry>) {
        selectedContacts = selected
        showLegacyPicker = false
        searchQuery = ""
    }

    fun openLegacyPicker() {
        viewModelScope.launch {
            val contacts = withContext(Dispatchers.IO) { loadAllContacts() }
            legacyContacts.clear()
            legacyContacts.addAll(contacts)
            showLegacyPicker = true
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun closeLegacyPicker() {
        showLegacyPicker = false
        searchQuery = ""
    }

    fun toggleLegacyContactSelection(contactId: String) {
        val index = legacyContacts.indexOfFirst { it.id == contactId }
        if (index != -1) {
            val contact = legacyContacts[index]
            legacyContacts[index] = contact.copy(isSelected = !contact.isSelected)
        }
    }

    private fun resolveContactDetails(contactUri: Uri): List<ContactEntry> {
        val list = mutableListOf<ContactEntry>()
        val projection = arrayOf(
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        context.contentResolver.query(contactUri, projection, null, null, null)?.use { cursor ->
            val idIdx = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
            val nameIdx = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME_PRIMARY)
            val phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            
            while (cursor.moveToNext()) {
                val id = if (idIdx != -1) cursor.getString(idIdx) else ""
                val name = if (nameIdx != -1) cursor.getString(nameIdx) ?: "Unknown" else "Unknown"
                val phone = if (phoneIdx != -1) cursor.getString(phoneIdx) else null
                list.add(ContactEntry(id, name, phone))
            }
        }
        return list
    }

    private fun loadAllContacts(): List<ContactEntry> {
        val list = mutableListOf<ContactEntry>()
        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY),
            null, null, "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} ASC"
        )

        cursor?.use {
            val idIdx = it.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIdx = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            while (it.moveToNext()) {
                val id = it.getString(idIdx)
                val name = it.getString(nameIdx) ?: "Unknown"
                list.add(ContactEntry(id, name))
            }
        }
        return list
    }
}
