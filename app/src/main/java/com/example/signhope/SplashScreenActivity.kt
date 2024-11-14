package com.example.signhope

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 1500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            // This method will be executed once the timer is over
            // Start your app getting started activity
            val i = Intent(this, GettingStartedActivity::class.java)
            startActivity(i)

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}