package com.example.signhope

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException

class AlphabetDetectionActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    private var photoCaptured: Bitmap? = null
    private val apiClient = MyApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view to the layout file you want to use (e.g., activity_alphabet_detection.xml)
        setContentView(R.layout.alphabetdetection)

        if (checkCameraPermission()) {
            openCamera()
        } else {
            requestCameraPermission()
        }

        // Set up event listener for the "Detect Alphabets" button
        findViewById<Button>(R.id.btnDetectAlphabets).setOnClickListener {
            photoCaptured?.let { bitmap ->
                capturePhoto(bitmap)
            }
        }

        // Set up event listener for the new button to open the camera again for the detection process
        findViewById<Button>(R.id.btnNewAction).setOnClickListener {
            openCamera() // Call the openCamera() method to open the camera again
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            photoCaptured = photo
            findViewById<ImageView>(R.id.imageView).apply {
                setImageBitmap(photo)
                visibility = View.VISIBLE
            }
        }
    }

    private fun capturePhoto(capturedBitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        capturedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val prediction = apiClient.predictImage(byteArray)
                runOnUiThread {
                    // Display the prediction result
                    findViewById<TextView>(R.id.tvPrediction).apply {
                        text = prediction
                        visibility = View.VISIBLE
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@AlphabetDetectionActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    inner class MyApiClient {
        private val client = OkHttpClient()

        @Throws(IOException::class)
        fun predictImage(imageData: ByteArray): String {
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    "photo.jpg",
                    imageData.toRequestBody("image/jpeg".toMediaTypeOrNull())
                )
                .build()

            val request = Request.Builder()
                .url("http://10.4.73.128:8000/predict_image") // Replace with your FastAPI endpoint URL
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                return response.body!!.string()
            }
        }
    }
}