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
import androidx.compose.material3.OutlinedButton
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
 * A custom ActivityResultContract to pick contacts.
 * Input: Boolean (true for multiple selection, false for single selection)
 * Output: List of selected contact URIs.
 */
class PickContactsContract : ActivityResultContract<Boolean, List<Uri>>() {
    override fun createIntent(context: Context, input: Boolean): Intent {
        return if (Build.VERSION.SDK_INT >= 37) {
            Intent(ACTION_PICK_CONTACTS).apply {
                if (input) {
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
            }
        } else {
            // Fallback to legacy picker (always single selection in this fallback)
            Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Uri> {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            val uris = mutableListOf<Uri>()
            // Results for multiple selection are returned via ClipData
            intent.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    clipData.getItemAt(i).uri?.let { uris.add(it) }
                }
            } ?: intent.data?.let { uris.add(it) } // Single result
            
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

    // Launcher for the Contact Picker API
    val pickContactsLauncher = rememberLauncherForActivityResult(
        contract = PickContactsContract()
    ) { uris ->
        val names = mutableListOf<String>()
        uris.forEach { contactUri ->
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
            text = "System Contact Picker",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Modern Privacy-Focused Selection",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Option 1: Single Selection
        OutlinedButton(
            onClick = { pickContactsLauncher.launch(false) },
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text("Select Single Contact")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Option 2: Multiple Selection (Only supported on Android 17+)
        Button(
            onClick = { pickContactsLauncher.launch(true) },
            enabled = Build.VERSION.SDK_INT >= 37,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text("Select Multiple Contacts")
        }
        
        if (Build.VERSION.SDK_INT < 37) {
            Text(
                text = "(Multi-select requires Android 17+)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
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
            text = "The new Contact Picker API allows sharing specific contacts without needing full READ_CONTACTS permission.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = TextAlign.Center
        )
    }
}
