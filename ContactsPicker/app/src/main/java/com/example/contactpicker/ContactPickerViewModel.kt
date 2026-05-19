package com.example.contactpicker

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactPickerViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val SELECTION_LIMIT = 10
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
                if (selectedLegacyCount >= SELECTION_LIMIT) return
            }
            
            legacyContactsList[targetIndex] = contact.copy(isSelected = !contact.isSelected)
        }
    }

    private fun resolveContactDetails(contactUri: Uri): List<ContactEntry> {
        val contactList = mutableListOf<ContactEntry>()
        
        // 1. Try treating it as a Data URI (contains Phone.NUMBER and DISPLAY_NAME).
        // This works for the modern Session URI (API 17+) and legacy ACTION_PICK from Phone table.
        val dataProjection = arrayOf(
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        
        var foundWithDataQuery = false
        try {
            context.contentResolver.query(contactUri, dataProjection, null, null, null)?.use { cursor ->
                val idIdx = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
                val nameIdx = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME_PRIMARY)
                val phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                
                while (cursor.moveToNext()) {
                    val id = if (idIdx != -1) cursor.getString(idIdx) ?: "" else ""
                    val name = if (nameIdx != -1) cursor.getString(nameIdx) ?: "Unknown" else "Unknown"
                    val phone = if (phoneIdx != -1) cursor.getString(phoneIdx) else null
                    contactList.add(ContactEntry(id, name, phone))
                    foundWithDataQuery = true
                }
            }
        } catch (e: SecurityException) {
            // No permission to query Data table (though picker URI usually grants it)
        } catch (e: Exception) {
            // Column names might be different for legacy Contacts URI
        }

        if (!foundWithDataQuery) {
            // 2. Fallback: Treat it as a Contacts URI (only has _ID and DISPLAY_NAME).
            val contactProjection = arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
            )
            try {
                context.contentResolver.query(contactUri, contactProjection, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val idIdx = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                        val nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                        
                        val id = if (idIdx != -1) cursor.getString(idIdx) ?: "" else ""
                        val name = if (nameIdx != -1) cursor.getString(nameIdx) ?: "Unknown" else "Unknown"
                        
                        // 3. Only attempt to query for phone numbers if we have permission.
                        // This prevents crashes on devices where the picker returned a Contact URI but we don't have READ_CONTACTS.
                        val phone = if (hasReadContactsPermission()) fetchPhoneNumberForContact(id) else null
                        contactList.add(ContactEntry(id, name, phone))
                    }
                }
            } catch (e: Exception) {
                // Final fallback if everything fails
            }
        }
        
        return contactList
    }

    private fun hasReadContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, 
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun fetchPhoneNumberForContact(contactId: String): String? {
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val selection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"
        val selectionArgs = arrayOf(contactId)
        
        return try {
            context.contentResolver.query(
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
        } catch (e: SecurityException) {
            null // Permission was revoked or missing
        }
    }

    private fun loadAllContacts(): List<ContactEntry> {
        if (!hasReadContactsPermission()) return emptyList()

        val contactList = mutableListOf<ContactEntry>()
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )
        
        try {
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
                    val contactId = if (idColumnIndex != -1) cursor.getString(idColumnIndex) else ""
                    val contactName = if (nameColumnIndex != -1) cursor.getString(nameColumnIndex) ?: "Unknown" else "Unknown"
                    if (contactId.isNotEmpty()) {
                        contactList.add(ContactEntry(contactId, contactName))
                    }
                }
            }
        } catch (e: SecurityException) {
            // Permission revoked or missing
        } catch (e: Exception) {
            // Other query errors
        }
        return contactList
    }
}
