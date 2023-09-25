package com.tomerpacific.locationpermissions

import  android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.tomerpacific.locationpermissions.ui.theme.LocationPermissionsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val locationPermissionsAlreadyGranted by remember { mutableStateOf(checkPermission()) }
            var showDialog by remember {mutableStateOf(false)}
            var shouldShowPermissionRationale by remember { mutableStateOf(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) }

            val locationPermissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    val permissionsGranted = permissions.values.reduce { acc, b ->
                        acc && b
                    }

                    if (!permissionsGranted) {
                        shouldShowPermissionRationale = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                        showDialog = true
                    }
                })

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(key1 = lifecycleOwner, effect = {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START && !locationPermissionsAlreadyGranted) {
                        launcher.launch(locationPermissions)
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }
            )

            LocationPermissionsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (shouldShowPermissionRationale && showDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                showDialog = false
                            },
                            title = {
                                Text("Permission Required")
                            },
                            text = {
                                Text("You need to approve this permission in order to...")
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    showDialog = false
                                }) {
                                    Text("Confirm")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showDialog = false
                                }) {
                                    Text("Deny")
                                }
                            })
                        }
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}