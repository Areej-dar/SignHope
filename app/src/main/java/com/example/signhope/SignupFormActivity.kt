package com.example.signhope

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupFormActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_form)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val button16 = findViewById<ImageView>(R.id.button_Back1)
        button16.setOnClickListener {
            val intent = Intent(this, SignupPageActivity::class.java)
            startActivity(intent)
        }

        val button17: Button = findViewById(R.id.continue_btn)
        button17.setOnClickListener {
            //lets get email and password from user
            performSignUp()
        }

        val showPasswordTextView: TextView = findViewById(R.id.register_show_password_)
        showPasswordTextView.setOnClickListener {
            onShowPasswordClicked()
        }

        val showConfirmPasswordTextView: TextView = findViewById(R.id.textShowPassword_)
        showConfirmPasswordTextView.setOnClickListener {
            onShowConfirmPasswordClicked()
        }
    }

    private fun performSignUp() {
        val email = findViewById<EditText>(R.id.enter_email1)
        val password = findViewById<EditText>(R.id.enter_password1)

        if(email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, lets move to next activity
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        baseContext,
                        "Success.",
                        Toast.LENGTH_SHORT,
                    ).show()


                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun onShowPasswordClicked() {
        val password: EditText = findViewById(R.id.enter_password1)
        if (password.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
            password.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            password.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
    }

    fun onShowConfirmPasswordClicked() {
        val confirmPassword: EditText = findViewById(R.id.register_confirm_password_)
        if (confirmPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
            confirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            confirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
    }
}