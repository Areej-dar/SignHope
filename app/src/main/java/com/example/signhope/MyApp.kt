package com.example.signhope

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MyApp : Application() {
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate() {
        super.onCreate()
        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun logout() {
        // Clear the login status
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("isLoggedIn")
        editor.apply()

        // Perform logout actions specific to your authentication provider
        FirebaseAuth.getInstance().signOut()
        googleSignInClient.signOut()
    }
}
