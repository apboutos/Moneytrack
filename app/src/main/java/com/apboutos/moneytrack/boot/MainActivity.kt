package com.apboutos.moneytrack.boot

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.database.database.MoneytrackDatabase
import com.apboutos.moneytrack.model.entity.Entry

class MainActivity : Activity() {

    private val text by lazy {findViewById<TextView>(R.id.helloTextBox)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val e = Entry.createEmptyEntry()

        //val ee = MoneytrackDatabase.invoke()

        text.text = "Yolo"
    }
}