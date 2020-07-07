@file:Suppress("unused")

package com.apboutos.moneytrack.view

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.database.entity.User
import com.apboutos.moneytrack.viewmodel.RegisterActivityViewModel

class RegisterActivity : Activity() {

    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(RegisterActivityViewModel::class.java) }
    private val usernameBox by lazy { findViewById<EditText>(R.id.activity_register_username_box) }
    private val passwordBox by lazy { findViewById<EditText>(R.id.activity_register_password_box) }
    private val passwordRetypeBox by lazy { findViewById<EditText>(R.id.activity_register_password_re_box) }
    private val emailBox by lazy { findViewById<EditText>(R.id.activity_register_email_box) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val signUpButton  = findViewById<Button>(R.id.activity_register_signUp_button)
        signUpButton.setOnClickListener {

            if(!dataEnteredAreValid()) return@setOnClickListener

            when (viewModel.registerNewUser(User(usernameBox.text.toString(),passwordBox.text.toString(),emailBox.text.toString(),"",""))){

                RegisterActivityViewModel.RegisterError.NO_ERROR -> { Toast.makeText(applicationContext, "Registration completed.", Toast.LENGTH_SHORT).show(); finish() }

                RegisterActivityViewModel.RegisterError.USERNAME_TAKEN -> { usernameBox.error = getString(R.string.activity_register_user_error) }

                RegisterActivityViewModel.RegisterError.EMAIL_TAKEN -> { emailBox.error = getString(R.string.activity_register_email_error) }

                RegisterActivityViewModel.RegisterError.NO_INTERNET -> { Toast.makeText(applicationContext, getString(R.string.activity_register_no_internet_error), Toast.LENGTH_LONG).show()}

                RegisterActivityViewModel.RegisterError.SERVER_UNREACHABLE -> { Toast.makeText(applicationContext, getString(R.string.activity_register_server_unreachable_error), Toast.LENGTH_LONG).show() }
            }
        }
    }

    private fun dataEnteredAreValid(): Boolean {

        if (usernameBox.text.toString().isEmpty()) {usernameBox.error = "Enter a username."; return false}
        else usernameBox.error = null

        if (passwordBox.text.toString().isEmpty()) { passwordBox.error = "Enter a password."; return false}
        else passwordBox.error = null

        if (passwordRetypeBox.text.toString().isEmpty()) { passwordRetypeBox.error = "Retype the password you entered above."; return false }
        else passwordRetypeBox.error = null

        if (passwordBox.text.toString().length < 8) { passwordBox.error = "Password must be at least 8 characters long."; return false }
        else passwordRetypeBox.error = null

        if (passwordRetypeBox.text.toString().length < 8) { passwordBox.error = "Password must be at least 8 characters long."; return false }
        else passwordRetypeBox.error = null

        if (passwordBox.text.toString() != passwordRetypeBox!!.text.toString()) { passwordRetypeBox.error = "Passwords do not match."; return false }
        else passwordRetypeBox.error = null

        if (emailBox.text.toString().isEmpty()) { emailBox.error = "Enter an e-mail address."; return false }

        return true
    }
}