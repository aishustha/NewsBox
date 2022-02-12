package com.example.mapd726_group3_newsbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signIn = findViewById<Button>(R.id.SignInBtn)
        val signUpTxt = findViewById<TextView>(R.id.SignUpBtnTxt)

        signIn.setOnClickListener {
            val intent = Intent(this@SignIn, Explore::class.java)
            startActivity(intent)
        }

        signUpTxt.setOnClickListener {
            val intent = Intent(this@SignIn, SignUp::class.java)
            startActivity(intent)
        }
    }
}