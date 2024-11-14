package com.example.signhope

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SignupSigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_signin_page)

        val button22 = findViewById<Button>(R.id.signupButton)
        button22.setOnClickListener {
            val intent = Intent(this, SignupPageActivity::class.java)
            startActivity(intent)
        }

        val button23 = findViewById<Button>(R.id.signinButton)
        button23.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val button24 = findViewById<ImageView>(R.id.forward_arrow)
        button24.setOnClickListener {
            val intent = Intent(this, GuestHomeActivity::class.java)
            startActivity(intent)
        }
    }
}