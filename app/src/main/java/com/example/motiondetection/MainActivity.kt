package com.example.motiondetection

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private val handler = Handler(Looper.getMainLooper())

    // TODO: Înlocuiește cu IP-ul real al ESP-ului tău
    private val espUrl = "http://192.168.201.1/events"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)

        startSSEConnection()
    }

    private fun startSSEConnection() {
        Thread {
            try {
                val url = URL(espUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.setRequestProperty("Accept", "text/event-stream")
                connection.connectTimeout = 5000
                connection.readTimeout = 0 // conexiune deschisă permanent

                val reader = connection.inputStream.bufferedReader()

                while (true) {
                    val line = reader.readLine() ?: break
                    if (line.startsWith("data:")) {
                        val value = line.removePrefix("data:").trim()
                        Log.d("ccc", "Received: $value")

                        runOnUiThread {
                            statusTextView.text = when (value) {
                                "motion" -> "Motion detected!"
                                "nomotion" -> "No motion."
                                else -> "Unexpected: $value"
                            }
                        }
                    }
                }

                connection.disconnect()
            } catch (e: Exception) {
                runOnUiThread {
                    statusTextView.text = "Disconnected"
                }
                Log.e("ccc", "Error: ${e.message}")
            }
        }.start()
    }


    private fun getMotionStatusFromESP() {
        Thread {
            try {
                val url = URL(espUrl)
                Log.d("ccc", "getMotionStatusFromESP: " + url)
                val connection = url.openConnection() as HttpURLConnection
                Log.d("ccc", connection.toString())
                connection.connectTimeout = 3000
                connection.readTimeout = 3000

                val response = connection.inputStream.bufferedReader().readText().trim()
                Log.d("ccc", response)
                runOnUiThread {
                    when (response) {
                        "motion" -> statusTextView.text = "Motion detected!"
                        "nomotion" -> statusTextView.text = "No motion."
                        else -> statusTextView.text = "Unexpected response: $response"
                    }
                }

                connection.disconnect()
            } catch (e: Exception) {
                runOnUiThread {
                    statusTextView.text = "ESP unreachable"
                }
            }
        }.start()
    }
}
