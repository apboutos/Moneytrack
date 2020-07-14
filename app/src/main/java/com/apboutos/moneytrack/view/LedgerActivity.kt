@file:Suppress("unused", "UNUSED_PARAMETER")

package com.apboutos.moneytrack.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.viewmodel.LedgerActivityViewModel
import com.google.android.material.appbar.MaterialToolbar


class LedgerActivity : AppCompatActivity() {

    private val self : LedgerActivity = this
    private val tag = "LedgerActivity"
    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory(application).create(LedgerActivityViewModel::class.java) }
    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.activity_ledger_toolbar) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.activity_ledger_recycler_view) }
    internal val adapter by lazy { LedgerRecyclerAdapter(viewModel.entryList)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ledger)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        registerTouchHelper()
        registerRecyclerViewListener()

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
                //openDialog(position)
                Log.d(tag,position.toString())
            }

        }
    }

    private fun openDialog(position : Int){
        val dialog = EditEntryDialog(this,viewModel.getEntry(position),position)
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

    fun onClickFloatingActionButton(view: View){
        Log.d(tag,"ActionButton")
        openDialog(1)
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