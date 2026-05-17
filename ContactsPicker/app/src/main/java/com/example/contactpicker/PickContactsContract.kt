package com.example.contactpicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContract

/**
 * A custom ActivityResultContract to pick contacts.
 * Input: Boolean (isMultipleSelection)
 * Output: List of selected contact URIs.
 */
class PickContactsContract : ActivityResultContract<Boolean, List<Uri>>() {
    override fun createIntent(context: Context, input: Boolean): Intent {
        val modernAction = "android.provider.action.PICK_CONTACTS"
        val modernIntent = Intent(modernAction).apply {
            type = ContactsContract.Contacts.CONTENT_TYPE
            
            val requestedFields = arrayListOf(
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            putStringArrayListExtra(
                "android.provider.extra.PICK_CONTACTS_REQUESTED_DATA_FIELDS",
                requestedFields
            )
            
            if (input) {
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                putExtra("android.provider.extra.PICK_CONTACTS_SELECTION_LIMIT", 10)
            }
        }

        // Check if the modern picker is available on this specific device build
        val isModernPickerAvailable = Build.VERSION.SDK_INT >= 37 && 
                modernIntent.resolveActivity(context.packageManager) != null

        return if (isModernPickerAvailable) {
            modernIntent
        } else {
            if (input) {
                // Legacy multiple selection (handled via custom UI in our app)
                Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            } else {
                // Legacy single selection: Pick the phone number directly
                Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            }
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Uri> {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            val contactUris = mutableListOf<Uri>()
            intent.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    clipData.getItemAt(i).uri?.let { uri ->
                        contactUris.add(uri)
                    }
                }
            } ?: intent.data?.let { uri ->
                contactUris.add(uri)
            }
            contactUris.distinct()
        } else {
            emptyList()
        }
    }
}
