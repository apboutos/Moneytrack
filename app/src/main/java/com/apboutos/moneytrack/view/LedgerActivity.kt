@file:Suppress("unused")

package com.apboutos.moneytrack.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.viewmodel.LedgerActivityViewModel
import com.google.android.material.appbar.MaterialToolbar


class LedgerActivity : AppCompatActivity() {

    private val tag = "LedgerActivity"
    val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory(application).create(LedgerActivityViewModel::class.java) }
    val toolbar by lazy { findViewById<MaterialToolbar>(R.id.activity_ledger_toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ledger)


        val adapter = LedgerRecyclerAdapter(viewModel.createMockData("2020-10-12","2020-10-12 23:23:23"))
        val recyclerView = findViewById<RecyclerView>(R.id.activity_ledger_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()


        toolbar.setOnMenuItemClickListener{menuItem ->
            when(menuItem.itemId){
                R.id.toolbar_menu_ledger   -> onClickLedgerMenuIcon()
                R.id.toolbar_menu_calendar -> onClickCalendarMenuIcon()
                R.id.toolbar_menu_search   -> onClickSearchMenuIcon()
                R.id.toolbar_menu_settings -> onClickSettingsMenuIcon()
                R.id.toolbar_menu_logout   -> onClickLogoutMenuIcon()
                else                       -> false
            }
        }
    }

    fun onClickFloatingActionButton(view: View){
        Log.d(tag,"ActionButton")
    }

    private fun onClickLedgerMenuIcon() : Boolean{
        Log.d(tag,"Ledger")
        return true
    }

    private fun onClickCalendarMenuIcon() : Boolean{
        Log.d(tag,"Calendar")
        return true
    }

    private fun onClickSearchMenuIcon() : Boolean{
        Log.d(tag,"Search")
        return true
    }

    private fun onClickSettingsMenuIcon() : Boolean{
        Log.d(tag,"Settings")
        return true
    }

    private fun onClickLogoutMenuIcon() : Boolean{
        Log.d(tag,"Logout")
        return true
    }
}