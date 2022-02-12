package com.example.mapd726_group3_newsbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signUp = findViewById<Button>(R.id.SignUpBtn)
        val signInTxt = findViewById<TextView>(R.id.SignInBtnTxt)

        signUp.setOnClickListener {
            val intent = Intent(this@SignUp, Explore::class.java)
            startActivity(intent)
        }

        signInTxt.setOnClickListener {
            val intent = Intent(this@SignUp, SignIn::class.java)
            startActivity(intent)
        }
    }
}