package com.example.sensorcheckingapp

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView

class SensorDetailActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorDetailLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_detail)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorDetailLayout = findViewById(R.id.sensorDetailLayout)

        val sensorType = intent.getIntExtra("sensorType", -1)
        val sensorName = intent.getStringExtra("sensorName")

        displaySensorDetails(sensorType, sensorName)
    }

    private fun displaySensorDetails(sensorType: Int, sensorName: String?) {
        val sensor = sensorManager.getDefaultSensor(sensorType)
        val details = StringBuilder()

        if (sensor != null) {
            details.append("$sensorName Details:\n")
            details.append("Name: ${sensor.name}\n")
            details.append("Vendor: ${sensor.vendor}\n")
            details.append("Version: ${sensor.version}\n")
            details.append("Maximum Range: ${sensor.maximumRange}\n")
            details.append("Resolution: ${sensor.resolution}\n")
            details.append("Power: ${sensor.power}\n")
        } else {
            details.append("$sensorName is not available on this device.")
        }

        val detailTextView = TextView(this).apply {
            text = details.toString()
            textSize = 16f
            setPadding(0, 8, 0, 8)
        }
        sensorDetailLayout.addView(detailTextView)
    }
}
