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
 * A custom ActivityResultContract to pick contacts.
 * Input: Boolean (isMultipleSelection)
 * Output: List of selected contact URIs.
 */
class PickContactsContract : ActivityResultContract<Boolean, List<Uri>>() {
    override fun createIntent(context: Context, input: Boolean): Intent {
        return if (Build.VERSION.SDK_INT >= 37) {
            Intent(ACTION_PICK_CONTACTS).apply {
                val requestedFields = arrayListOf(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )

                putStringArrayListExtra("android.provider.extra.PICK_CONTACTS_REQUESTED_DATA_FIELDS", requestedFields)

                if (input) {
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
            }
        } else {
            Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
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
