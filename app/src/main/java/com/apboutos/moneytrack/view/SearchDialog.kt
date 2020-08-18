package com.apboutos.moneytrack.view

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import com.apboutos.moneytrack.R

class SearchDialog(private val parentActivity: LedgerActivity) : Dialog(parentActivity){

    private val tag = "SearchDialog"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_ledger_search_dialog)


    }
}