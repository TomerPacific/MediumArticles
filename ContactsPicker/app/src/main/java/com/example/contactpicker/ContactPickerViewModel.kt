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

    private val legacyContactsList = mutableStateListOf<ContactEntry>()

    val filteredLegacyContacts by derivedStateOf {
        if (searchQuery.isEmpty()) legacyContactsList
        else legacyContactsList.filter { contact ->
            contact.name.contains(searchQuery, ignoreCase = true)
        }
    }

    private val context: Context get() = getApplication<Application>().applicationContext

    fun onModernContactsPicked(resultUris: List<Uri>) {
        viewModelScope.launch {
            val resolvedContacts = withContext(Dispatchers.IO) {
                resultUris.flatMap { contactUri -> resolveContactDetails(contactUri) }
            }
            selectedContacts = resolvedContacts
        }
    }

    fun onLegacySelectionComplete(selectedItems: List<ContactEntry>) {
        selectedContacts = selectedItems
        showLegacyPicker = false
        searchQuery = ""
    }

    fun openLegacyPicker() {
        viewModelScope.launch {
            val allContacts = withContext(Dispatchers.IO) { loadAllContacts() }
            legacyContactsList.clear()
            legacyContactsList.addAll(allContacts)
            showLegacyPicker = true
        }
    }

    fun updateSearchQuery(newQuery: String) {
        searchQuery = newQuery
    }

    fun closeLegacyPicker() {
        showLegacyPicker = false
        searchQuery = ""
    }

    fun toggleLegacyContactSelection(contactId: String) {
        val targetIndex = legacyContactsList.indexOfFirst { it.id == contactId }
        if (targetIndex != -1) {
            val contact = legacyContactsList[targetIndex]
            legacyContactsList[targetIndex] = contact.copy(isSelected = !contact.isSelected)
        }
    }

    private fun resolveContactDetails(contactUri: Uri): List<ContactEntry> {
        val contactList = mutableListOf<ContactEntry>()
        val projection = arrayOf(
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        context.contentResolver.query(contactUri, projection, null, null, null)?.use { cursor ->
            val idColumnIndex = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
            val nameColumnIndex = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME_PRIMARY)
            val phoneColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            
            while (cursor.moveToNext()) {
                val contactId = if (idColumnIndex != -1) cursor.getString(idColumnIndex) else ""
                val contactName = if (nameColumnIndex != -1) {
                    cursor.getString(nameColumnIndex) ?: "Unknown"
                } else "Unknown"
                val phoneNumber = if (phoneColumnIndex != -1) cursor.getString(phoneColumnIndex) else null
                
                contactList.add(ContactEntry(contactId, contactName, phoneNumber))
            }
        }
        return contactList
    }

    private fun loadAllContacts(): List<ContactEntry> {
        val contactList = mutableListOf<ContactEntry>()
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )
        
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} ASC"
        )?.use { cursor ->
            val idColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            
            while (cursor.moveToNext()) {
                val contactId = cursor.getString(idColumnIndex)
                val contactName = cursor.getString(nameColumnIndex) ?: "Unknown"
                contactList.add(ContactEntry(contactId, contactName))
            }
        }
        return contactList
    }
}
