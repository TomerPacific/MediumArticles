package com.example.contactpicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.provider.ContactsPickerSessionContract.ACTION_PICK_CONTACTS
import androidx.activity.result.contract.ActivityResultContract

/**
 * A custom ActivityResultContract to pick contacts (API 17+).
 * Enhanced to request specific data fields when using the modern API.
 */
class PickContactsContract : ActivityResultContract<Boolean, List<Uri>>() {
    override fun createIntent(context: Context, input: Boolean): Intent {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CINNAMON_BUN) {
            Intent(ACTION_PICK_CONTACTS).apply {
                // Request phone numbers specifically
                val requestedFields = arrayListOf(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                // Use the modern extra key for requested fields
                putStringArrayListExtra("android.provider.extra.PICK_CONTACTS_REQUESTED_DATA_FIELDS", requestedFields)
                
                if (input) {
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    // Optional: limit to 10 contacts
                    putExtra("android.provider.extra.PICK_CONTACTS_SELECTION_LIMIT", 10)
                }
            }
        } else {
            Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Uri> {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            val uris = mutableListOf<Uri>()
            intent.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    clipData.getItemAt(i).uri?.let { uris.add(it) }
                }
            } ?: intent.data?.let { uris.add(it) }
            uris.distinct()
        } else {
            emptyList()
        }
    }
}
