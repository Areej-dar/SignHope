package com.example.signhope

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.signhope.databinding.WorddetectionBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class WordDetectionActivity : AppCompatActivity() {

    private lateinit var binding: WorddetectionBinding
    private lateinit var selectedVideoUri: Uri

    private val client = OkHttpClient.Builder()
        .connectTimeout(180, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(180, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(180, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    private val videoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val uri = data.data
                    if (uri != null) {
                        selectedVideoUri = uri
                        showMessage("Video picked successfully")
                    }
                }
            } else {
                showMessage("Video selection failed")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WorddetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTakeVideo.setOnClickListener {
            val intent = Intent(this, VideoCaptureActivity::class.java)
            videoPickerLauncher.launch(intent)
        }

        binding.btnPickVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            videoPickerLauncher.launch(intent)
        }

        binding.btnDetectVideo.setOnClickListener {
            if (::selectedVideoUri.isInitialized) {
                binding.tvPrediction.text = "Result is loading..."
                binding.tvPrediction.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
                sendVideoToFastAPI(selectedVideoUri)
            } else {
                showMessage("Please pick or capture a video first")
            }
        }
    }

    private fun sendVideoToFastAPI(videoUri: Uri) {
        val videoFile = createTempFileFromUri(videoUri)
        val requestBody = videoFile.asRequestBody("video/mp4".toMediaTypeOrNull())

        val filePart = MultipartBody.Part.createFormData("file", videoFile.name, requestBody)

        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(filePart)
            .build()

        val request = Request.Builder()
            .url("http://10.4.73.128:8000/predict_video")
            .post(multipartBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    showMessage("Failed to send video: ${e.message}")
                }
                Log.e("MainActivity", "Failed to send video: ${e.message}", e)
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread { binding.progressBar.visibility = View.GONE }
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d("MainActivity", "Response body: $responseBody")

                    try {
                        val json = JSONObject(responseBody)
                        val prediction = json.getString("prediction")
                        runOnUiThread {
                            binding.tvPrediction.text = "Prediction: $prediction"
                            binding.tvPrediction.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            binding.tvPrediction.text = "Failed to parse response"
                            binding.tvPrediction.visibility = View.VISIBLE
                        }
                        Log.e("MainActivity", "Failed to parse response: ${e.message}", e)
                    }
                } else {
                    runOnUiThread {
                        binding.tvPrediction.text = "Failed to get response from server"
                        binding.tvPrediction.visibility = View.VISIBLE
                    }
                    Log.e("MainActivity", "Failed to get response from server: ${response.message}")
                }
            }
        })
    }

    private fun createTempFileFromUri(uri: Uri): File {
        val fileName = getFileName(uri) ?: "temp_file.mp4"
        val tempFile = File(cacheDir, fileName)
        val inputStream = contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(tempFile)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }

    private fun getFileName(uri: Uri): String? {
        var name: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1 && it.moveToFirst()) {
                name = it.getString(nameIndex)
            }
        }
        return name
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
