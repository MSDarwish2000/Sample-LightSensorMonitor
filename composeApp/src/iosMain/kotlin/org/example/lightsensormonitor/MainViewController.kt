package org.example.lightsensormonitor

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import androidx.compose.runtime.remember

fun MainViewController() = ComposeUIViewController {
    val lightSensorMonitor = remember { IosLightSensorMonitor() }
    CompositionLocalProvider(LocalLightSensorMonitor provides lightSensorMonitor) {
        App()
    }
}
