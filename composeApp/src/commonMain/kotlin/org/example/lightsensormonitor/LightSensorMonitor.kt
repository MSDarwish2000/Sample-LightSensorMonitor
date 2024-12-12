package org.example.lightsensormonitor

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.Flow

/** Simple light sensor monitor */
interface LightSensorMonitor {
    val value: Flow<Float?>
}

/** In real-world applications, this composition local is expected to be replaced with business layer DI */
val LocalLightSensorMonitor = staticCompositionLocalOf<LightSensorMonitor> {
    error("No LightSensorMonitor provided")
}
