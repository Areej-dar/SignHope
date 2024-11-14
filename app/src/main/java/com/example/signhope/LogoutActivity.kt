package com.example.signhope

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LogoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logoutpage)

        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            AuthUtil.logout(this)
        }
    }
}