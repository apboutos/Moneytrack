@file:Suppress("unused")

package com.apboutos.moneytrack.view

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import com.apboutos.moneytrack.model.database.entity.Entry
import com.apboutos.moneytrack.viewmodel.LedgerActivityViewModel



class LedgerActivity : Activity() {

    val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory(application).create(LedgerActivityViewModel::class.java) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.activity_ledger_recycler_view) }
    private val newEntryButton by lazy { findViewById<Button>(R.id.activity_ledger_newEntryButton)}
    val dateButton by lazy { findViewById<Button>(R.id.activity_ledger_dateButton) }
    private val sumBox by lazy { findViewById<TextView>(R.id.activity_ledger_sumBox) }
    val adapter by lazy { LedgerRecyclerAdapter(viewModel.loadEntries(dateButton.text.toString()))}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ledger_x)

        viewModel.insertMockDataToDatabase()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()

        dateButton.setOnClickListener { openCalendar() }
    }

    private fun openCalendar() {
        val calendarDialog = CalendarDialog(this, dateButton.text.toString())
        calendarDialog.setCancelable(true)
        calendarDialog.setCanceledOnTouchOutside(false)
        calendarDialog.show()
    }

}