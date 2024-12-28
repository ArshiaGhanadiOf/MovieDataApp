package com.arshia.moviedatademo

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arshia.moviedatademo.helper.ConnectionStatus
import com.arshia.moviedatademo.ui.screens.mainscreen.MainScreen
import com.arshia.moviedatademo.ui.screen.mainscreen.component.connectivityStatus
import com.arshia.moviedatademo.ui.theme.MovieDataDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                detectDarkMode = { true })
        )
        setContent {
            val connection by connectivityStatus()
            val isConnected = connection === ConnectionStatus.Available

            MovieDataDemoTheme {
                MainScreen(isConnected)
            }
        }
    }
}