package com.example.signhope

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        // Find CardViews by their IDs
        val cardView1: CardView = findViewById(R.id.CardView1)
        val cardView2: CardView = findViewById(R.id.CardView2)
        val cardView3: CardView = findViewById(R.id.CardView3)
        val cardView4: CardView = findViewById(R.id.CardView4)
        val cardView5: CardView = findViewById(R.id.CardView5)
        val cardView6: CardView = findViewById(R.id.CardView6)
        val cardView: CardView = findViewById(R.id.CardView)

        val cardViews = listOf(cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView)

        for (cardView in cardViews) {
            cardView.setOnClickListener {
                when (it.id) {
                    R.id.CardView1 -> {
                        // Toggle animation first, then navigate to LogoutActivity
                        animateCardView(it)
                        Handler().postDelayed({
                            val intent = Intent(this, DetectionActivity::class.java)
                            startActivity(intent)
                        }, 300) // Delay to match animation duration
                    }
                    R.id.CardView2 -> {
                        // Toggle animation first, then navigate to LogoutActivity
                        animateCardView(it)
                        Handler().postDelayed({
                            val intent = Intent(this, PictionaryActivity::class.java)
                            startActivity(intent)
                        }, 300) // Delay to match animation duration
                    }
                    R.id.CardView3 -> {
                        // Toggle animation first, then navigate to LogoutActivity
                        animateCardView(it)
                        Handler().postDelayed({
                            val intent = Intent(this, PlayVideos::class.java)
                            startActivity(intent)
                        }, 300) // Delay to match animation duration
                    }
                    R.id.CardView4 -> {
                        // Toggle animation first, then navigate to LogoutActivity
                        animateCardView(it)
                        Handler().postDelayed({
                            val intent = Intent(this, ChatBotActivity::class.java)
                            startActivity(intent)
                        }, 300) // Delay to match animation duration
                    }
                    R.id.CardView5 -> {
                        // Toggle animation first, then navigate to LogoutActivity
                        animateCardView(it)
                        Handler().postDelayed({
                            val intent = Intent(this, TexttoSpeech::class.java)
                            startActivity(intent)
                        }, 300) // Delay to match animation duration
                    }
                    R.id.CardView6 -> {
                        // Toggle animation first, then navigate to LogoutActivity
                        animateCardView(it)
                        Handler().postDelayed({
                            val intent = Intent(this, LogoutActivity::class.java)
                            startActivity(intent)
                        }, 300) // Delay to match animation duration
                    }
                    R.id.CardView -> {
                        // Toggle animation first, then navigate to LogoutActivity
                        animateCardView(it)
                        Handler().postDelayed({
                            val intent = Intent(this, AboutUsActivity::class.java)
                            startActivity(intent)
                        }, 300) // Delay to match animation duration
                    }
                    else -> {
                        animateCardView(it)
                        when (it.id) {
                            R.id.CardView -> Toast.makeText(this, "CardView clicked", Toast.LENGTH_SHORT).show()
                            R.id.CardView1 -> Toast.makeText(this, "CardView1 clicked", Toast.LENGTH_SHORT).show()
                            R.id.CardView2 -> Toast.makeText(this, "CardView2 clicked", Toast.LENGTH_SHORT).show()
                            R.id.CardView3 -> Toast.makeText(this, "CardView3 clicked", Toast.LENGTH_SHORT).show()
                            R.id.CardView4 -> Toast.makeText(this, "CardView4 clicked", Toast.LENGTH_SHORT).show()
                            R.id.CardView5 -> Toast.makeText(this, "CardView5 clicked", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            cardView.setOnHoverListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_HOVER_ENTER -> animateCardView(v)
                    MotionEvent.ACTION_HOVER_EXIT -> resetCardView(v)
                }
                false
            }
        }
    }

    private fun animateCardView(view: View) {
        // Scale animation for the card view
        val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1.1f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.1f)
        )
        scaleUp.duration = 150
        scaleUp.start()

        scaleUp.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                // Reset scale to normal after animation ends
                val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                    view,
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)
                )
                scaleDown.duration = 150
                scaleDown.start()
            }
        })
    }

    private fun resetCardView(view: View) {
        // Reset the scale to its original size
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)
        )
        scaleDown.duration = 150
        scaleDown.start()
    }
}
