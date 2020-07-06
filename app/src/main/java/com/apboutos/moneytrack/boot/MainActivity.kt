package com.apboutos.moneytrack.boot

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.repository.DatabaseRepository

class MainActivity : Activity() {

    private val text by lazy {findViewById<TextView>(R.id.helloTextBox)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = DatabaseRepository(application)
    }
}