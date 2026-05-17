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

    companion object {
        private const val SELECTION_LIMIT = 10
    }

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

    val selectedLegacyCount by derivedStateOf {
        legacyContactsList.count { it.isSelected }
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

    fun onLegacySelectionComplete() {
        selectedContacts = legacyContactsList.filter { it.isSelected }
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
            
            // If selecting a new contact, enforce the limit
            if (!contact.isSelected) {
                val currentSelectedCount = legacyContactsList.count { it.isSelected }
                if (currentSelectedCount >= SELECTION_LIMIT) return
            }

            legacyContactsList[targetIndex] = contact.copy(isSelected = !contact.isSelected)
        }
    }

    private fun resolveContactDetails(contactUri: Uri): List<ContactEntry> {
        val contactList = mutableListOf<ContactEntry>()
        
        // Android 17+ Session URIs have a specific authority
        val isSessionUri = contactUri.authority == "com.android.contacts.picker.sessions"
        
        if (android.os.Build.VERSION.SDK_INT >= 37 && isSessionUri) {
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
                    contactList.add(ContactEntry(id, name, phone))
                }
            }
        } else {
            // Legacy URI or URI from Intent.ACTION_PICK (ContactsContract.Contacts.CONTENT_URI)
            val projection = arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
            )
            context.contentResolver.query(contactUri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val idIdx = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                    val nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                    
                    val id = cursor.getString(idIdx)
                    val name = cursor.getString(nameIdx) ?: "Unknown"
                    
                    // Now query for phone numbers using the ID
                    val phone = fetchPhoneNumberForContact(id)
                    contactList.add(ContactEntry(id, name, phone))
                }
            }
        }
        
        return contactList
    }

    private fun fetchPhoneNumberForContact(contactId: String): String? {
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val selection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"
        val selectionArgs = arrayOf(contactId)
        
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                if (phoneIdx != -1) cursor.getString(phoneIdx) else null
            } else null
        }
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
