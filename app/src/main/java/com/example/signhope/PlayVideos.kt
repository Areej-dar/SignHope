package com.example.signhope

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PlayVideos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_tutorials)

        val scrollView = findViewById<ScrollView>(R.id.scrollView)

        findViewById<TextView>(R.id.greetingsText).setOnClickListener {
            // Scroll to the "Greetings" text in the white section
            val greetingsTextView = findViewById<TextView>(R.id.greetingsText1)
            scrollView.smoothScrollTo(0, greetingsTextView.top)
        }

        // Click listener for "Food" text
        findViewById<TextView>(R.id.foodText).setOnClickListener {
            // Scroll to the "Food" text in the white section
            val foodTextView = findViewById<TextView>(R.id.foodText1)
            scrollView.smoothScrollTo(0, foodTextView.top)
        }

        findViewById<TextView>(R.id.emotionsText).setOnClickListener {
            // Scroll to the "Food" text in the white section
            val foodTextView = findViewById<TextView>(R.id.emotionsText1)
            scrollView.smoothScrollTo(0, foodTextView.top)
        }

        findViewById<TextView>(R.id.timeText).setOnClickListener {
            // Scroll to the "Food" text in the white section
            val foodTextView = findViewById<TextView>(R.id.timeText1)
            scrollView.smoothScrollTo(0, foodTextView.top)
        }

        findViewById<TextView>(R.id.monthsText).setOnClickListener {
            // Scroll to the "Food" text in the white section
            val foodTextView = findViewById<TextView>(R.id.monthText1)
            scrollView.smoothScrollTo(0, foodTextView.top)
        }

        findViewById<TextView>(R.id.familyText).setOnClickListener {
            // Scroll to the "Food" text in the white section
            val foodTextView = findViewById<TextView>(R.id.familyText1)
            scrollView.smoothScrollTo(0, foodTextView.top)
        }

        val button1 = findViewById<ImageButton>(R.id.gesture_1)
        button1.setOnClickListener {
            val intent = Intent(this, HelloASL::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<ImageButton>(R.id.gesture_2)
        button2.setOnClickListener {
            val intent = Intent(this, GoodMorningASL::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<ImageButton>(R.id.gesture_3)
        button3.setOnClickListener {
            val intent = Intent(this, HowAreYouFeelingTodayASL::class.java)
            startActivity(intent)
        }

        val button4 = findViewById<ImageButton>(R.id.gesture_4)
        button4.setOnClickListener {
            val intent = Intent(this, ItsNiceToMeetYouASL::class.java)
            startActivity(intent)
        }

        val button5 = findViewById<ImageButton>(R.id.gesture_5)
        button5.setOnClickListener {
            val intent = Intent(this, WhatsYourName::class.java)
            startActivity(intent)
        }

        val button6 = findViewById<ImageButton>(R.id.gesture_6)
        button6.setOnClickListener {
            val intent = Intent(this, BreakfastFoodsASL::class.java)
            startActivity(intent)
        }

        val button7 = findViewById<ImageButton>(R.id.gesture_7)
        button7.setOnClickListener {
            val intent = Intent(this, EatFood::class.java)
            startActivity(intent)
        }

        val button8 = findViewById<ImageButton>(R.id.gesture_8)
        button8.setOnClickListener {
            val intent = Intent(this, YourFavoriteFoodWhatASL::class.java)
            startActivity(intent)
        }

        val button9 = findViewById<ImageButton>(R.id.gesture_9)
        button9.setOnClickListener {
            val intent = Intent(this, IAmHungryASL::class.java)
            startActivity(intent)
        }

        val button10 = findViewById<ImageButton>(R.id.gesture_10)
        button10.setOnClickListener {
            val intent = Intent(this, IWantToDrinkWaterASL::class.java)
            startActivity(intent)
        }

        val button34 = findViewById<ImageButton>(R.id.gesture_11)
        button34.setOnClickListener {
            val intent = Intent(this, EmotionsASL::class.java)
            startActivity(intent)
        }

        val button35 = findViewById<ImageButton>(R.id.gesture_12)
        button35.setOnClickListener {
            val intent = Intent(this, TimeASL::class.java)
            startActivity(intent)
        }

        val button36 = findViewById<ImageButton>(R.id.gesture_13)
        button36.setOnClickListener {
            val intent = Intent(this, MonthsASL::class.java)
            startActivity(intent)
        }

        val button37 = findViewById<ImageButton>(R.id.gesture_14)
        button37.setOnClickListener {
            val intent = Intent(this, FamilySigns::class.java)
            startActivity(intent)
        }
    }
}