package org.example.lightsensormonitor

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    val lightSensorMonitor = IosLightSensorMonitor()
    CompositionLocalProvider(LocalLightSensorMonitor provides lightSensorMonitor) {
        App()
    }
}