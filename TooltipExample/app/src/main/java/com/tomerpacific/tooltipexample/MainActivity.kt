package com.tomerpacific.tooltipexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tomerpacific.tooltipexample.ui.theme.TooltipExampleTheme
import kotlinx.coroutines.launch


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
                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Row(modifier = Modifier.fillMaxWidth().height(intrinsicSize = IntrinsicSize.Max),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text("Basic Tooltip")
                            BasicTooltip()
                        }
                        Row(modifier = Modifier.fillMaxWidth().height(intrinsicSize = IntrinsicSize.Max),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text("Rich Tooltip")
                            RichTooltip()
                        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTooltip() {
    val tooltipPosition = TooltipDefaults.rememberRichTooltipPositionProvider()
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()


    TooltipBox(positionProvider = tooltipPosition,
        tooltip = {
                  RichTooltip(
                      title = { Text("RichTooltip") },
                      caretSize = TooltipDefaults.caretSize,
                      action = {
                          TextButton(onClick = {
                              scope.launch {
                                  tooltipState.dismiss()
                                  tooltipState.onDispose()
                              }
                          }) {
                              Text("Dismiss")
                          }
                      }
                  ) {

                  }
        },
        state = tooltipState) {
        IconButton(onClick = {  }) {
            Icon(imageVector = Icons.Filled.Call, contentDescription = "Localized Description")
        }
    }
}
