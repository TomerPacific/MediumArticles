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
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContactPickerScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactPickerViewModel = viewModel()
) {
    val context = LocalContext.current

    // Launcher for the modern API (17+)
    val pickContactsLauncher = rememberLauncherForActivityResult(
        contract = PickContactsContract()
    ) { uris ->
        viewModel.onModernContactsPicked(uris)
    }

    // Permission Launcher for legacy access
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.openLegacyPicker()
        }
    }

    if (viewModel.showLegacyPicker) {
        LegacyContactPickerUI(
            contacts = viewModel.legacyContacts,
            onToggleSelection = { index -> viewModel.toggleLegacyContactSelection(index) },
            onSelectionComplete = { selected -> viewModel.onLegacySelectionComplete(selected) },
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
                text = "Contact Selection Demo",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(48.dp))

            OutlinedButton(
                onClick = { pickContactsLauncher.launch(false) },
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(0.8f)
            ) {
                Text("Select Single Contact (API 11+)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= 37) {
                        pickContactsLauncher.launch(true)
                    } else {
                        val status = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
                        if (status == PackageManager.PERMISSION_GRANTED) {
                            viewModel.openLegacyPicker()
                        } else {
                            permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                        }
                    }
                },
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(0.8f)
            ) {
                val label = if (Build.VERSION.SDK_INT >= 37) "Modern Multi-Select (API 17+)" else "Legacy Multi-Select (Needs Permission)"
                Text(label)
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Selected: ${viewModel.selectedContactNames.size}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            if (viewModel.selectedContactNames.isEmpty()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("No contacts selected", color = MaterialTheme.colorScheme.outline)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.selectedContactNames) { name ->
                        Text(
                            text = name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Before API 17, multi-selection requires the READ_CONTACTS permission and a custom UI. API 17+ uses the system picker without needing permissions.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}
