package com.example.contactpicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsPickerSessionContract.ACTION_PICK_CONTACTS
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.contactpicker.ui.theme.ContactPickerTheme

/**
 * A custom ActivityResultContract to pick multiple contacts.
 * This utilizes the new Android 15+ Contacts Picker API (ContactsPickerSessionContract).
 */
class PickMultipleContacts : ActivityResultContract<Unit?, List<Uri>>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        // ACTION_PICK_CONTACTS is available starting in Android 17 (API 37)
        return if (Build.VERSION.SDK_INT >= 37) {
            Intent(ACTION_PICK_CONTACTS)
        } else {
            // Fallback to legacy single-contact picker for older versions
            Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Uri> {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            val uris = mutableListOf<Uri>()
            // Results are returned via ClipData for multiple selections
            intent.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    clipData.getItemAt(i).uri?.let { uris.add(it) }
                }
            } ?: intent.data?.let { uris.add(it) } // Fallback to single result
            
            uris.distinct()
        } else {
            emptyList()
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactPickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactPickerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ContactPickerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var selectedContactNames by remember { mutableStateOf<List<String>>(emptyList()) }

    // Launcher for the new Android 15+ Contacts Picker API
    val pickMultipleContactsLauncher = rememberLauncherForActivityResult(
        contract = PickMultipleContacts()
    ) { uris ->
        val names = mutableListOf<String>()
        uris.forEach { contactUri ->
            // The URIs returned by the picker have temporary read permissions.
            val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
            context.contentResolver.query(contactUri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        names.add(cursor.getString(nameIndex))
                    }
                }
            }
        }
        selectedContactNames = names
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Multi-Contact Picker",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Using Android 15 Contacts Picker API",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { pickMultipleContactsLauncher.launch(null) },
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(0.8f)
        ) {
            val buttonText = if (Build.VERSION.SDK_INT >= 37) "Select Multiple Contacts" else "Select Contact"
            Text(buttonText)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Selected Contacts (${selectedContactNames.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        if (selectedContactNames.isEmpty()) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No contacts selected",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedContactNames) { name ->
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Note: This app uses the new system Contact Picker API on Android 17+ for multi-selection without permissions. On older versions, it falls back to single selection.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = TextAlign.Center
        )
    }
}
