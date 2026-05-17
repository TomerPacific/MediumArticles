package com.example.contactpicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A stateless component to show a custom contact selection list.
 */
@Composable
fun LegacyContactPickerUI(
    contactList: List<ContactEntry>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onToggleSelection: (String) -> Unit,
    onSelectionComplete: (List<ContactEntry>) -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Text("Select Contacts", style = MaterialTheme.typography.titleLarge)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newQuery -> onSearchQueryChange(newQuery) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search contacts...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(contactList, key = { contactItem -> contactItem.id }) { contactItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggleSelection(contactItem.id) }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = contactItem.isSelected,
                        onCheckedChange = { onToggleSelection(contactItem.id) }
                    )
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(contactItem.name, style = MaterialTheme.typography.bodyLarge)
                        contactItem.phoneNumber?.let { phone ->
                            Text(
                                text = phone,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    val selectedItems = contactList.filter { contact -> contact.isSelected }
                    onSelectionComplete(selectedItems)
                }
            ) {
                Text("Done")
            }
        }
    }
}
