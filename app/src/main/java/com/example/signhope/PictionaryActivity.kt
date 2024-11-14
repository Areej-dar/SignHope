package com.example.signhope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView

class PictionaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pictionary)

        val scrollView = findViewById<ScrollView>(R.id.scrollView1)

        findViewById<TextView>(R.id.alphabetsText).setOnClickListener {

            val alphabetsTextView = findViewById<TextView>(R.id.alphabetsText1)
            scrollView.smoothScrollTo(0, alphabetsTextView.top)
        }

        findViewById<TextView>(R.id.numbersText).setOnClickListener {

            val alphabetsTextView = findViewById<TextView>(R.id.numbersText1)
            scrollView.smoothScrollTo(0, alphabetsTextView.top)
        }

        findViewById<TextView>(R.id.timeText).setOnClickListener {

            val alphabetsTextView = findViewById<TextView>(R.id.timeText1)
            scrollView.smoothScrollTo(0, alphabetsTextView.top)
        }

        findViewById<TextView>(R.id.basicText).setOnClickListener {

            val alphabetsTextView = findViewById<TextView>(R.id.basicText1)
            scrollView.smoothScrollTo(0, alphabetsTextView.top)
        }

        findViewById<TextView>(R.id.dailyText).setOnClickListener {

            val alphabetsTextView = findViewById<TextView>(R.id.dailyText1)
            scrollView.smoothScrollTo(0, alphabetsTextView.top)
        }

        findViewById<TextView>(R.id.commonText).setOnClickListener {

            val alphabetsTextView = findViewById<TextView>(R.id.commonText1)
            scrollView.smoothScrollTo(0, alphabetsTextView.top)
        }

        findViewById<TextView>(R.id.questionText).setOnClickListener {

            val alphabetsTextView = findViewById<TextView>(R.id.questionText1)
            scrollView.smoothScrollTo(0, alphabetsTextView.top)
        }

        val button11 = findViewById<ImageButton>(R.id.pictionary_1)
        button11.setOnClickListener {
            val intent = Intent(this, AlphabetsPictionary::class.java)
            startActivity(intent)
        }

        val button12 = findViewById<ImageButton>(R.id.pictionary_2)
        button12.setOnClickListener {
            val intent = Intent(this, NumbersPictionary::class.java)
            startActivity(intent)
        }

        val button38 = findViewById<ImageButton>(R.id.pictionary_3)
        button38.setOnClickListener {
            val intent = Intent(this, TimePictionary::class.java)
            startActivity(intent)
        }

        val button39 = findViewById<ImageButton>(R.id.pictionary_4)
        button39.setOnClickListener {
            val intent = Intent(this, BasicSignsPictionary::class.java)
            startActivity(intent)
        }

        val button40 = findViewById<ImageButton>(R.id.pictionary_5)
        button40.setOnClickListener {
            val intent = Intent(this, DailyPictionary::class.java)
            startActivity(intent)
        }

        val button41 = findViewById<ImageButton>(R.id.pictionary_6)
        button41.setOnClickListener {
            val intent = Intent(this, CommonSigns::class.java)
            startActivity(intent)
        }

        val button42 = findViewById<ImageButton>(R.id.pictionary_7)
        button42.setOnClickListener {
            val intent = Intent(this, QuestionPictionary::class.java)
            startActivity(intent)
        }
    }
}