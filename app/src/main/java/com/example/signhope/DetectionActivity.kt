package com.example.signhope

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class DetectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detection)

        // Find the CardView elements and TextView elements in the layout
        val cardWalking: CardView = findViewById(R.id.cardWalking)
        val textViewWalking: TextView = findViewById(R.id.textViewWalking)
        val cardBoating: CardView = findViewById(R.id.cardBoating)
        val textViewBoating: TextView = findViewById(R.id.textViewBoating)

        // Set up event listener for cardWalking (Translate Alphabets)
        cardWalking.setOnClickListener {
            // Launch AlphabetDetectionActivity
            val intent = Intent(this, AlphabetDetectionActivity::class.java)
            startActivity(intent)
        }

        // Set up event listener for cardBoating (Translate Words)
        cardBoating.setOnClickListener {
            val intent = Intent(this, WordDetectionActivity::class.java)
            startActivity(intent)
        }

        // Set up hover listeners for the CardViews
        setUpHoverListeners(cardWalking, textViewWalking)
        setUpHoverListeners(cardBoating, textViewBoating)
    }

    private fun setUpHoverListeners(cardView: CardView, textView: TextView) {
        cardView.setOnHoverListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_HOVER_ENTER -> {
                    // Change the text color when the hover starts
                    textView.setTextColor(Color.parseColor("#B1D4E0"))
                    true
                }
                MotionEvent.ACTION_HOVER_EXIT -> {
                    // Revert the text color when the hover ends
                    textView.setTextColor(Color.parseColor("#000000"))
                    true
                }
                else -> false
            }
        }
    }
}