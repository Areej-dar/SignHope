package com.example.signhope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ChatMessage(val message: String, val isUser: Boolean)

class ChatAdapter(private val chatMessages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatMessage = chatMessages[position]
        holder.bind(chatMessage)
    }

    override fun getItemCount(): Int = chatMessages.size

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.tvMessage)

        fun bind(chatMessage: ChatMessage) {
            messageTextView.text = chatMessage.message
            messageTextView.setBackgroundResource(
                if (chatMessage.isUser) R.color.userMessageBackground else R.color.botMessageBackground
            )
            messageTextView.textAlignment =
                if (chatMessage.isUser) View.TEXT_ALIGNMENT_VIEW_END else View.TEXT_ALIGNMENT_VIEW_START
        }
    }
}
