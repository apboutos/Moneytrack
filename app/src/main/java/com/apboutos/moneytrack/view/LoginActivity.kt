package com.apboutos.moneytrack.view

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.viewmodel.LoginActivityViewModel


class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewModel     = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(LoginActivityViewModel::class.java)
        val usernameBox   = findViewById<EditText>(R.id.activity_login_usernameBox)
        val passwordBox   = findViewById<EditText>(R.id.activity_login_passwordBox)
        val rememberMeBox = findViewById<CheckBox>(R.id.activity_login_rememberMeBox)
        val forgotText    = findViewById<TextView>(R.id.activity_login_forgotText)
        val signUpText    = findViewById<TextView>(R.id.activity_login_signUpText)
        val loginButton   = findViewById<Button>(R.id.activity_login_loginButton)

        loginButton.setOnClickListener {

        }

        forgotText.setOnClickListener {

        }

        signUpText.setOnClickListener {

        }
    }
}