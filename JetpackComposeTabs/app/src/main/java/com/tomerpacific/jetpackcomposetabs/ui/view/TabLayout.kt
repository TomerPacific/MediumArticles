package com.tomerpacific.jetpackcomposetabs.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.tomerpacific.jetpackcomposetabs.MainViewModel

@Composable
fun TabLayout(viewModel: MainViewModel) {
    val tabIndex = viewModel.index.observeAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex.value!!) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex.value!! == index,
                    onClick = { viewModel.updateTabIndex(index) },
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Default.Home, contentDescription = null)
                            1 -> Icon(imageVector = Icons.Default.Info, contentDescription = null)
                            2 -> Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                        }
                    }
                )
            }
        }

        when (tabIndex.value) {
            0 -> HomeScreen(viewModel = viewModel)
            1 -> AboutScreen(viewModel = viewModel)
            2 -> SettingsScreen(viewModel = viewModel)
        }
    }
}