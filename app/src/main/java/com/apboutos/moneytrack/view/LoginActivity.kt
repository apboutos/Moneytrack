@file:Suppress("PrivatePropertyName","unused")

package com.apboutos.moneytrack.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.utilities.error.LoginError
import com.apboutos.moneytrack.viewmodel.LoginActivityViewModel
import com.apboutos.moneytrack.viewmodel.receiver.LoginReceiver
import java.util.prefs.Preferences


//TODO Implement a progress bar dialog that appears while the user us waiting for login authentication.
class LoginActivity : Activity() {

    private val TAG = "LoginActivity"
    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(LoginActivityViewModel::class.java) }
    private val usernameBox by lazy { findViewById<EditText>(R.id.activity_login_usernameBox) }
    private val passwordBox by lazy { findViewById<EditText>(R.id.activity_login_passwordBox) }
    private val rememberMeBox by lazy { findViewById<CheckBox>(R.id.activity_login_rememberMeBox) }
    private val forgotText by lazy{ findViewById<TextView>(R.id.activity_login_forgotText) }
    private val receiver by lazy { LoginReceiver(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val signUpText    = findViewById<TextView>(R.id.activity_login_signUpText)
        val loginButton   = findViewById<Button>(R.id.activity_login_loginButton)

        fillUsernameAndPasswordBoxes()

        loginButton.setOnClickListener {

            if(!credentialEnteredIsValid()) return@setOnClickListener
            handleResponse(viewModel.requestAuthentication(usernameBox.text.toString(),passwordBox.text.toString()))
        }

        forgotText.setOnClickListener {
            startActivity(Intent(this,RetrieveActivity::class.java))
            finish()
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
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

    fun handleResponse(error: LoginError){
        when(error){
            LoginError.NO_ERROR -> { startActivity(Intent(this,LedgerActivity::class.java).putExtra("username",usernameBox.text.toString()))
                if(rememberMeBox.isChecked) viewModel.saveUserCredential(usernameBox.text.toString(),passwordBox.text.toString())
                else viewModel.deleteStoredCredential()
                applicationContext.getSharedPreferences("session", Context.MODE_PRIVATE).edit().putString("username",usernameBox.text.toString()).apply()
                finish() }

            LoginError.WRONG_USERNAME -> { Toast.makeText(applicationContext, getString(R.string.activity_login_username_error), Toast.LENGTH_LONG).show()
                forgotText.visibility = View.VISIBLE }

            LoginError.WRONG_PASSWORD -> { Toast.makeText(applicationContext, getString(R.string.activity_login_password_error), Toast.LENGTH_LONG).show()
                forgotText.visibility = View.VISIBLE }

            LoginError.NO_INTERNET -> { Toast.makeText(applicationContext, getString(R.string.activity_login_no_internet_error), Toast.LENGTH_LONG).show()
                forgotText.visibility = View.VISIBLE }

            LoginError.SERVER_UNREACHABLE-> { Toast.makeText(applicationContext, getString(R.string.activity_login_server_unreachable_error), Toast.LENGTH_LONG).show() }

            LoginError.ATTEMPTING_ONLINE_LOGIN -> { Log.d(TAG,"User ${usernameBox.text} was not found in database. Attempting online login.")}
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addCategory(Intent.CATEGORY_DEFAULT)
        filter.addAction(LoginReceiver.SERVER_LOGIN_RESPONSE)
        registerReceiver(receiver,filter)
    }

}