package com.tomerpacific.tooltipexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tomerpacific.tooltipexample.ui.theme.TooltipExampleTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TooltipExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        BasicTooltip()
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BasicTooltip() {
    val tooltipPosition = TooltipDefaults.rememberPlainTooltipPositionProvider()
    val tooltipState = rememberBasicTooltipState(isPersistent = false)

    BasicTooltipBox(positionProvider = tooltipPosition,
        tooltip =  { Text("Hello World") } ,
        state = tooltipState) {
        IconButton(onClick = { /* Icon button's click event */ }) {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Localized Description")
        }
    }
}
