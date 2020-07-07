@file:Suppress("PrivatePropertyName","unused")

package com.apboutos.moneytrack.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.viewmodel.LoginActivityViewModel


//TODO Implement a progress bar dialog that appears while the user us waiting for login authentication.
class LoginActivity : Activity() {

    private val TAG = "LoginActivity"
    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(LoginActivityViewModel::class.java) }
    private val usernameBox by lazy { findViewById<EditText>(R.id.activity_login_usernameBox) }
    private val passwordBox by lazy { findViewById<EditText>(R.id.activity_login_passwordBox) }
    private val rememberMeBox by lazy { findViewById<CheckBox>(R.id.activity_login_rememberMeBox) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val forgotText    = findViewById<TextView>(R.id.activity_login_forgotText)
        val signUpText    = findViewById<TextView>(R.id.activity_login_signUpText)
        val loginButton   = findViewById<Button>(R.id.activity_login_loginButton)

        fillUsernameAndPasswordBoxes()

        loginButton.setOnClickListener {

            if(!credentialEnteredIsValid()) return@setOnClickListener
            when (viewModel.requestAuthentication(usernameBox.text.toString(),passwordBox.text.toString())){

                LoginActivityViewModel.LoginError.NO_ERROR -> { startActivity(Intent(this,LedgerActivity::class.java).putExtra("username",usernameBox.text.toString()))
                                                                if(rememberMeBox.isChecked) viewModel.saveUserCredential(usernameBox.text.toString(),passwordBox.text.toString())
                                                                else viewModel.deleteStoredCredential()
                                                                finish() }

                LoginActivityViewModel.LoginError.WRONG_USERNAME -> { Toast.makeText(applicationContext, getString(R.string.activity_login_username_error), Toast.LENGTH_LONG).show()
                                                                      rememberMeBox.visibility = View.VISIBLE }

                LoginActivityViewModel.LoginError.WRONG_PASSWORD -> { Toast.makeText(applicationContext, getString(R.string.activity_login_password_error), Toast.LENGTH_LONG).show()
                                                                      rememberMeBox.visibility = View.VISIBLE }
            }
        }

        forgotText.setOnClickListener {
            startActivity(Intent(this,RetrieveActivity::class.java))
            finish()
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
    }

    private fun credentialEnteredIsValid(): Boolean {
        if (usernameBox.text.toString().isEmpty()) {
            usernameBox.error = "Enter a username"
            return false
        } else {
            usernameBox.error = null
        }
        if (passwordBox.text.toString().isEmpty()) {
            passwordBox.error = "Enter a password"
            return false
        } else {
            passwordBox.error = null
        }
        return true
    }

    private fun fillUsernameAndPasswordBoxes(){
        val credential = viewModel.retrieveStoredCredential()
        if(credential != null){
            usernameBox.setText(credential.username)
            passwordBox.setText(credential.password)
            rememberMeBox.isChecked = true
        }
    }


}