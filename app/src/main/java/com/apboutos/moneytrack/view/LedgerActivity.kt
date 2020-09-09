@file:Suppress("unused", "UNUSED_PARAMETER")

package com.apboutos.moneytrack.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.database.entity.Summary
import com.apboutos.moneytrack.utilities.converter.DateFormatConverter
import com.apboutos.moneytrack.viewmodel.LedgerActivityViewModel
import com.google.android.material.appbar.MaterialToolbar


class LedgerActivity : AppCompatActivity() {

    private val self : LedgerActivity = this
    private val tag = "LedgerActivity"
    internal val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory(application).create(LedgerActivityViewModel::class.java) }
    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.activity_ledger_toolbar) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.activity_ledger_recycler_view) }
    internal val adapter by lazy { LedgerRecyclerAdapter(viewModel.entryList,this)}
    internal val dateBox by lazy { findViewById<TextView>(R.id.activity_ledger_dateToolbar_dateBox) }
    private val previousDayButton by lazy {findViewById<Button>(R.id.activity_ledger_dateToolbar_previousButton)}
    private val nextDayButton by lazy {findViewById<Button>(R.id.activity_ledger_dateToolbar_nextButton)}
    private var searchResultsAreDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ledger)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        registerTouchHelper()
        registerRecyclerViewListener()
        dateBox.text = DateFormatConverter.parseToDisplayableDate(viewModel.currentDate,this)

        viewModel.currentUser = intent.getStringExtra("username") ?: "root"
        if(viewModel.currentDate == "root"){
            Toast.makeText(this,"Logged in as the test user root.",Toast.LENGTH_LONG).show()
        }
        viewModel.loadEntries()
        adapter.notifyDataSetChanged()
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

    private fun registerRecyclerViewListener(){
        adapter.listener = object : LedgerRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                openEditDialog(position)
                Log.d(tag,position.toString())
            }

        }
    }


    private fun openNewDialog(){
        val dialog = NewEntryDialog(this)
        dialog.setCanceledOnTouchOutside(false)
        dialog.create()
        dialog.show()
    }

    private fun openEditDialog(position : Int){
        val dialog = EditEntryDialog(this,viewModel.getEntry(position),position)
        dialog.setCanceledOnTouchOutside(false)
        dialog.create()
        dialog.show()
    }
    private fun registerTouchHelper(){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.removeEntry(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(recyclerView)
    }

    fun onClickPreviousButton(view: View){
        viewModel.goToPreviousDay()
        adapter.notifyDataSetChanged()
        dateBox.text = DateFormatConverter.parseToDisplayableDate(viewModel.currentDate,this)
    }

    fun onClickNextButton(view: View){
        viewModel.goToNextDay()
        adapter.notifyDataSetChanged()
        dateBox.text = DateFormatConverter.parseToDisplayableDate(viewModel.currentDate,this)
    }

    fun onClickFloatingActionButton(view: View){
        openNewDialog()
        Log.d(tag,"ActionButton")
    }

    private fun onClickLedgerMenuIcon() : Boolean{
        val report = ReportDialog(this)
        report.create()
        report.show()
        Log.d(tag,"Ledger")
        return true
    }

    private fun onClickCalendarMenuIcon() : Boolean{
        val calendar = CalendarDialog(this)
        calendar.setCanceledOnTouchOutside(false)
        calendar.create()
        calendar.show()
        Log.d(tag,"Calendar")
        return true
    }

    private fun onClickSearchMenuIcon() : Boolean{
        val search = SearchDialog(this)
        search.setCanceledOnTouchOutside(false)
        search.create()
        search.show()
        Log.d(tag,"Search")
        return true
    }

    private fun onClickSettingsMenuIcon() : Boolean{
        Log.d(tag,"Settings")
        return true
    }

    private fun onClickLogoutMenuIcon() : Boolean{
        val preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("autoLogin", false)
        editor.apply()
        finish()
        return true
    }

    fun loadSearchResults(summary: Summary){
        viewModel.loadEntriesOfSearch(summary)
        adapter.notifyDataSetChanged()
        dateBox.text = "${DateFormatConverter.parseToDisplayableFormat(summary.fromDate.toString(),this)} to ${DateFormatConverter.parseToDisplayableFormat(summary.untilDate.toString(),this)}"
        nextDayButton.visibility = View.INVISIBLE
        previousDayButton.visibility = View.INVISIBLE
        searchResultsAreDisplayed = true
    }

    override fun onBackPressed() {
        if (searchResultsAreDisplayed){
            searchResultsAreDisplayed = false
            previousDayButton.visibility = View.VISIBLE
            nextDayButton.visibility = View.VISIBLE
            viewModel.loadEntries()
            adapter.notifyDataSetChanged()
            dateBox.text = DateFormatConverter.parseToDisplayableDate(viewModel.currentDate,this)
        }
        else{
            super.onBackPressed()
        }
    }
}