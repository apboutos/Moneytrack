@file:Suppress("PrivatePropertyName","unused")

package com.apboutos.moneytrack.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.database.entity.Credential
import com.apboutos.moneytrack.utilities.error.LoginError
import com.apboutos.moneytrack.viewmodel.LoginActivityViewModel
import com.apboutos.moneytrack.viewmodel.receiver.LoginReceiver



class LoginActivity : Activity() {

    private val TAG = "LoginActivity"
    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(LoginActivityViewModel::class.java) }
    private val usernameBox by lazy { findViewById<EditText>(R.id.activity_login_usernameBox) }
    private val passwordBox by lazy { findViewById<EditText>(R.id.activity_login_passwordBox) }
    private val rememberMeBox by lazy { findViewById<CheckBox>(R.id.activity_login_rememberMeBox) }
    private val forgotText by lazy{ findViewById<TextView>(R.id.activity_login_forgotText) }
    private val loginProgressBar by lazy {findViewById<ProgressBar>(R.id.activity_login_progressBar)}
    private val receiver by lazy { LoginReceiver(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val signUpText    = findViewById<TextView>(R.id.activity_login_signUpText)
        val loginButton   = findViewById<Button>(R.id.activity_login_loginButton)

        fillUsernameAndPasswordBoxes(viewModel.retrieveStoredCredential())

        loginButton.setOnClickListener {

            if(!credentialEnteredIsValid()) return@setOnClickListener
            loginProgressBar.visibility = View.VISIBLE
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


    /**
     * Checks whether the credentials provided by the user are in a valid format.
     */
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

    /**
     * Fills the initial values of the username and password if they are saved in the database.
     */
    private fun fillUsernameAndPasswordBoxes(credential: Credential?){
        if(credential != null){
            usernameBox.setText(credential.username)
            passwordBox.setText(credential.password)
            rememberMeBox.isChecked = true
        }
    }

    /**
     * The LoginReceiver calls this method to handle the server's response to a login request.
     */
    fun handleResponse(error: LoginError){
        when(error){
            LoginError.NO_ERROR -> { startActivity(Intent(this,LedgerActivity::class.java).putExtra("username",usernameBox.text.toString()))
                if(rememberMeBox.isChecked) {
                    viewModel.saveUserCredential(usernameBox.text.toString(),passwordBox.text.toString())
                    getSharedPreferences("autoLogin",Context.MODE_PRIVATE).edit().putBoolean("autoLogin",true).apply()
                }
                else {
                    viewModel.deleteStoredCredential()
                }
                loginProgressBar.visibility = View.INVISIBLE
                finish() }

            LoginError.WRONG_USERNAME -> { Toast.makeText(applicationContext, getString(R.string.activity_login_username_error), Toast.LENGTH_LONG).show()
                forgotText.visibility = View.VISIBLE
                loginProgressBar.visibility = View.INVISIBLE}

            LoginError.WRONG_PASSWORD -> { Toast.makeText(applicationContext, getString(R.string.activity_login_password_error), Toast.LENGTH_LONG).show()
                forgotText.visibility = View.VISIBLE
                loginProgressBar.visibility = View.INVISIBLE}

            LoginError.NO_INTERNET -> { Toast.makeText(applicationContext, getString(R.string.activity_login_no_internet_error), Toast.LENGTH_LONG).show()
                forgotText.visibility = View.VISIBLE
                loginProgressBar.visibility = View.INVISIBLE}

            LoginError.SERVER_UNREACHABLE-> { Toast.makeText(applicationContext, getString(R.string.activity_login_server_unreachable_error), Toast.LENGTH_LONG).show()
                loginProgressBar.visibility = View.INVISIBLE}

            LoginError.ATTEMPTING_ONLINE_LOGIN -> { Log.d(TAG,"User ${usernameBox.text} was not found in database. Attempting online login.")}
        }

    }

    /**
     * Unregisters the broadcast receiver to avoid leaks.
     */
    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    /**
     * Registers the broadcast receiver.
     */
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addCategory(Intent.CATEGORY_DEFAULT)
        filter.addAction(LoginReceiver.SERVER_LOGIN_RESPONSE)
        registerReceiver(receiver,filter)
    }

}