package org.example.lightsensormonitor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

// TODO: Check the "aggressive" error handling
class AndroidLightSensorMonitor(context: Context) : LightSensorMonitor {

    private val sensorManager = context.getSystemService<SensorManager>()
        ?: error("Failed to retrieve SensorManager")

    // Null is preserved to indicate unavailable - In more complex applications a flow that emits Result<Float> along with `filter()` usage for example
    // is preferred, so that it can reference emit errors as well.
    override val value: Flow<Float?> = callbackFlow {
        val sensorCallback = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                // Not used in this example
            }

            override fun onSensorChanged(event: SensorEvent) {
                // It is not expected to fail
                require(event.sensor.type == Sensor.TYPE_LIGHT)

                trySend(event.values[0])
            }
        }

        sensorManager.registerListener(
            sensorCallback,
            sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
                ?: error("Failed to retrieve light sensor"),
            SensorManager.SENSOR_DELAY_UI,
        )

        awaitClose {
            // Make sure to collect using lifecycle-aware code such as `collectAsStateWithLifecycle` to avoid consuming battery life
            sensorManager.unregisterListener(sensorCallback)
        }
    }
}
