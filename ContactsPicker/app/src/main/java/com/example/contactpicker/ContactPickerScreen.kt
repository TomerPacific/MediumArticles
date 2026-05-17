package com.example.contactpicker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContactPickerScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactPickerViewModel = viewModel()
) {
    val context = LocalContext.current
    var isPermissionRationaleVisible by remember { mutableStateOf(false) }

    val modernPickerLauncher = rememberLauncherForActivityResult(
        contract = PickContactsContract()
    ) { resultUris ->
        viewModel.onModernContactsPicked(resultUris)
    }

    val legacyPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) viewModel.openLegacyPicker()
    }

    if (isPermissionRationaleVisible) {
        PermissionRationaleDialog(
            onConfirm = {
                isPermissionRationaleVisible = false
                legacyPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            },
            onDismiss = { isPermissionRationaleVisible = false }
        )
    }

    if (viewModel.showLegacyPicker) {
        LegacyContactPickerUI(
            contactList = viewModel.filteredLegacyContacts,
            selectedCount = viewModel.selectedLegacyCount,
            searchQuery = viewModel.searchQuery,
            onSearchQueryChange = { query -> viewModel.updateSearchQuery(query) },
            onToggleSelection = { contactId -> viewModel.toggleLegacyContactSelection(contactId) },
            onSelectionComplete = { viewModel.onLegacySelectionComplete() },
            onCancel = { viewModel.closeLegacyPicker() }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Contacts Picker",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = if (Build.VERSION.SDK_INT >= 37) {
                    "Using Modern Privacy API"
                } else {
                    "Legacy Compatibility Mode"
                },
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedButton(
                onClick = { modernPickerLauncher.launch(false) },
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(0.8f)
            ) {
                Text("Pick One Contact")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= 37) {
                        modernPickerLauncher.launch(true)
                    } else {
                        val status = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_CONTACTS
                        )
                        if (status == PackageManager.PERMISSION_GRANTED) {
                            viewModel.openLegacyPicker()
                        } else {
                            isPermissionRationaleVisible = true
                        }
                    }
                },
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(0.8f)
            ) {
                Text("Pick Multiple (Max 10)")
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Selected: ${viewModel.selectedContacts.size}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            if (viewModel.selectedContacts.isEmpty()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("No contacts selected", color = MaterialTheme.colorScheme.outline)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.selectedContacts) { selectedContact ->
                        SelectedContactItem(selectedContact)
                    }
                }
            }
        }
    }
}

@Composable
fun SelectedContactItem(contact: ContactEntry) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = contact.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            contact.phoneNumber?.let { phone ->
                Text(
                    text = phone,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun PermissionRationaleDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Contact Access Required") },
        text = { 
            Text("To select multiple contacts on this version of Android, we need permission to read your contacts. We will only use this to let you choose from the list.") 
        },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Allow") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
