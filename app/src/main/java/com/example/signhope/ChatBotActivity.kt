package com.example.signhope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.runBlocking

class ChatBotActivity : AppCompatActivity() {
    private lateinit var chatAdapter: ChatAdapter
    private val chatMessages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatbot)

        val eTPrompt = findViewById<EditText>(R.id.eTPrompt)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val rvChat = findViewById<RecyclerView>(R.id.rvChat)

        chatAdapter = ChatAdapter(chatMessages)
        rvChat.layoutManager = LinearLayoutManager(this)
        rvChat.adapter = chatAdapter

        btnSubmit.setOnClickListener {
            val prompt = eTPrompt.text.toString().trim()
            if (prompt.isNotEmpty()) {
                chatMessages.add(ChatMessage(prompt, true))
                chatAdapter.notifyItemInserted(chatMessages.size - 1)
                rvChat.scrollToPosition(chatMessages.size - 1)

                val generativeModel = GenerativeModel(
                    modelName = "gemini-pro",
                    apiKey = "AIzaSyBWY3Ld5BEr3V_g0Sf1BtfnaW_fPP_U_do"
                )
                runBlocking {
                    try {
                        val response = generativeModel.generateContent(prompt)
                        chatMessages.add(ChatMessage(response.text ?: "No response", false))
                        chatAdapter.notifyItemInserted(chatMessages.size - 1)
                        rvChat.scrollToPosition(chatMessages.size - 1)
                    } catch (e: Exception) {
                        chatMessages.add(ChatMessage("Error: ${e.message}", false))
                        chatAdapter.notifyItemInserted(chatMessages.size - 1)
                        rvChat.scrollToPosition(chatMessages.size - 1)
                    }
                }
                eTPrompt.text.clear()
            }
        }
    }
}
