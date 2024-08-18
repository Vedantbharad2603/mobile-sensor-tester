package com.example.sensorcheckingapp

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the SensorManager and LinearLayout for sensor list
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorListLayout = findViewById(R.id.sensorListLayout)

        // Set up Auto Check button
        val autoCheckButton: Button = findViewById(R.id.autoCheckButton)
        autoCheckButton.setOnClickListener {
            autoCheckSensors()
        }

        // Display the buttons for each sensor
        displaySensorButtons()
    }

    private fun displaySensorButtons() {
        val sensors = listOf(
            Sensor.TYPE_ACCELEROMETER to "Accelerometer",
            Sensor.TYPE_GYROSCOPE to "Gyroscope",
            Sensor.TYPE_LIGHT to "Ambient Light",
            Sensor.TYPE_MAGNETIC_FIELD to "Magnetometer",
            Sensor.TYPE_PROXIMITY to "Proximity",
            Sensor.TYPE_PRESSURE to "Barometer",
            Sensor.TYPE_AMBIENT_TEMPERATURE to "Temperature",
            Sensor.TYPE_GRAVITY to "Gravity",
            Sensor.TYPE_LINEAR_ACCELERATION to "Linear Acceleration",
            Sensor.TYPE_ROTATION_VECTOR to "Rotation Vector",
            Sensor.TYPE_STEP_COUNTER to "Step Counter",
            Sensor.TYPE_HEART_RATE to "Heart Rate",
            Sensor.TYPE_RELATIVE_HUMIDITY to "Humidity"
        )

        sensors.forEach { (sensorType, sensorName) ->
            val button = Button(this).apply {
                text = sensorName
                setOnClickListener {
                    val intent = Intent(this@MainActivity, SensorDetailActivity::class.java).apply {
                        putExtra("sensorType", sensorType)
                        putExtra("sensorName", sensorName)
                    }
                    startActivity(intent)
                }
            }
            sensorListLayout.addView(button)
        }
    }

    private fun autoCheckSensors() {
        val sensors = listOf(
            Sensor.TYPE_ACCELEROMETER to "Accelerometer",
            Sensor.TYPE_GYROSCOPE to "Gyroscope",
            Sensor.TYPE_LIGHT to "Ambient Light",
            Sensor.TYPE_MAGNETIC_FIELD to "Magnetometer",
            Sensor.TYPE_PROXIMITY to "Proximity",
            Sensor.TYPE_PRESSURE to "Barometer",
            Sensor.TYPE_AMBIENT_TEMPERATURE to "Temperature",
            Sensor.TYPE_GRAVITY to "Gravity",
            Sensor.TYPE_LINEAR_ACCELERATION to "Linear Acceleration",
            Sensor.TYPE_ROTATION_VECTOR to "Rotation Vector",
            Sensor.TYPE_STEP_COUNTER to "Step Counter",
            Sensor.TYPE_HEART_RATE to "Heart Rate",
            Sensor.TYPE_RELATIVE_HUMIDITY to "Humidity"
        )

        val report = StringBuilder()
        sensors.forEach { (sensorType, sensorName) ->
            val sensor = sensorManager.getDefaultSensor(sensorType)
            val status = if (sensor != null) "Available" else "Not Available"
            report.append("$sensorName: $status\n")
        }

        val reportTextView = TextView(this).apply {
            text = report.toString()
            textSize = 16f
            setPadding(16, 16, 16, 16)
        }
        sensorListLayout.removeAllViews()
        sensorListLayout.addView(reportTextView)
    }
}
