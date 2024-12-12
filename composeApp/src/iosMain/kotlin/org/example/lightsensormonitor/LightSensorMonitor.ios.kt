package org.example.lightsensormonitor

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import platform.SensorKit.SRAmbientLightSample
import platform.SensorKit.SRSensorAmbientLightSensor
import platform.SensorKit.SRSensorReader
import kotlin.random.Random

class IosLightSensorMonitor : LightSensorMonitor {

    private val random = Random.Default

    override val value: Flow<Float?> = flow {
        while (true) {
            // Emulate Android emulator slider values
            emit(random.nextInt(40000 * 8).toFloat() / 8)
            delay(1000)
        }
    }
}
