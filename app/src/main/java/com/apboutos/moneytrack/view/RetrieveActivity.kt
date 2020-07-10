@file:Suppress("unused")

package com.apboutos.moneytrack.view

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.viewmodel.RetrieveActivityViewModel

class RetrieveActivity : Activity() {

    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(RetrieveActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieve)

        val emailBox = findViewById<EditText>(R.id.activity_retrieve_email_box)

        findViewById<Button>(R.id.activity_retrieve_retrieve_button).setOnClickListener {
            viewModel.retrieveLostCredentials(emailBox.text.toString())
            Toast.makeText(this,getString(R.string.activity_retrieve_confirmation) + "${emailBox.text}",Toast.LENGTH_SHORT).show()
        }
    }
}