package com.example.purechase.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.purechase.Helper.Authentification
import com.example.purechase.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var signupName: EditText
    private lateinit var signupEmail: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupButton: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            val statusBarHeight = insets.systemWindowInsetTop
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }

        signupName = findViewById(R.id.signupName)
        signupEmail = findViewById(R.id.signupEmail)
        signupPassword = findViewById(R.id.signupPassword)
        signupButton = findViewById(R.id.signupBtn)

        signupButton.setOnClickListener {
            database = FirebaseDatabase.getInstance()
            reference = database.getReference("users")

            val name = signupName.text.toString()
            val email = signupEmail.text.toString()
            val password = signupPassword.text.toString()


            val authentification = Authentification().apply {
                this.name = name
                this.email = email
                this.password = password
            }


            reference.child(name).setValue(authentification)

            Toast.makeText(this@SignupActivity, "You have signed up successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SignupActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}