package com.example.signhope

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GettingStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.getting_started_page)

        val button21 = findViewById<Button>(R.id.get_started_btn)
        button21.setOnClickListener {
            val intent = Intent(this, SignupSigninActivity::class.java)
            startActivity(intent)
        }
    }
}