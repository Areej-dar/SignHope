package com.example.signhope

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var password: EditText
    private lateinit var showPassword: TextView

    companion object {
        const val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        if (checkLoginStatus()) {
            // If user is logged in, proceed to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Initialize FaceBook SDK
        FacebookSdk.sdkInitialize(applicationContext)

        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException) {

                }
            },
        )

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        password = findViewById(R.id.enter_password)
        showPassword = findViewById(R.id.textShowPassword)

        showPassword.setOnClickListener {
            onShowPasswordClicked(password)
        }

        val button13 = findViewById<TextView>(R.id.goto_signup)
        button13.setOnClickListener {
            val intent = Intent(this, SignupFormActivity::class.java)
            startActivity(intent)
        }
        val button14 = findViewById<Button>(R.id.login_button)
        button14.setOnClickListener {
            performLogin()
        }
        val button15 = findViewById<ImageView>(R.id.signin_google)
        button15.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun onShowPasswordClicked(password: EditText) {
        val start = password.selectionStart
        val end = password.selectionEnd
        if (password.transformationMethod == PasswordTransformationMethod.getInstance()) {
            password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            password.setSelection(start, end)
        } else {
            password.transformationMethod = PasswordTransformationMethod.getInstance()
            password.setSelection(start, end)
        }
    }

    private fun performLogin() {
        val email: EditText = findViewById(R.id.enter_email)

        //null check on input
        if(email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveLoginStatus()
                    // Create Intent for the VerifiedActivity
                    val intent = Intent(this, HomeActivity::class.java)

                    // Show Toast Message
                    Toast.makeText(baseContext, " success", Toast.LENGTH_SHORT,).show()

                    // Start the VerifiedActivity
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT,)
                        .show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error occured ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        // Handle Facebook SDK result
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun handleFacebookAccessToken(token: AccessToken) {

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please sign-in to continue!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun saveLoginStatus() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }

    // Checking login status
    private fun checkLoginStatus(): Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}
