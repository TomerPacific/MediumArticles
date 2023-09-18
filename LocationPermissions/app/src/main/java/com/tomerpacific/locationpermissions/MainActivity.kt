package com.tomerpacific.locationpermissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.tomerpacific.locationpermissions.ui.theme.LocationPermissionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationPermissionsAlreadyGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        setContent {
            LocationPermissionsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!locationPermissionsAlreadyGranted) {
                        RequestLocationPermissions()
                    }

                }
            }
        }
    }

    @Composable
    fun RequestLocationPermissions() {




        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

                } else -> {
                    requestPermissions(locationPermissions, 1)
                }
            }
        }

        locationPermissionRequest.launch(locationPermissions)
    }
}