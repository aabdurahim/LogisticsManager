package com.example.logisticsdeliverymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.logisticsdeliverymanager.navigation.AppNavigation
import com.example.logisticsdeliverymanager.ui.theme.LogisticsDeliveryManagerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LogisticsDeliveryManagerTheme {
                AppNavigation()
            }
        }
    }
}
