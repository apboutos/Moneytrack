@file:Suppress("unused")

package com.apboutos.moneytrack.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.viewmodel.LedgerActivityViewModel


class LedgerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ledger)


        val viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(LedgerActivityViewModel::class.java)
        val adapter = LedgerRecyclerAdapter(viewModel.createMockData("2020-10-12","2020-10-12 23:23:23"))
        val recyclerView = findViewById<RecyclerView>(R.id.activity_ledger_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()

    }


    fun onClickFloatingActionButton(view: View){

    }
}