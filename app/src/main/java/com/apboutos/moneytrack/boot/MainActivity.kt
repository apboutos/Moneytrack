package com.apboutos.moneytrack.boot

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.repository.local.DatabaseRepository
import com.apboutos.moneytrack.view.LedgerActivity
import com.apboutos.moneytrack.view.LoginActivity

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if(getSharedPreferences("autoLogin",Context.MODE_PRIVATE).getBoolean("autoLogin",false)){
            val intent = Intent(this, LedgerActivity::class.java)
            intent.putExtra("username",DatabaseRepository(application).selectCredential()?.username ?: "root")
            startActivity(intent)

        }
        else{
             val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        }
        finish()
    }


    //TODO Feature  : The settings dialog must be implemented. Example settings 1. Confirm before swipe delete. 2. Optional ledger legend. 3. Theme change
    //TODO Feature  : Server domain must move to https
    //TODO Bug***   : When the user does a search and the description box is left empty no entry is returned instead of returning all entries.
    //                To fix this when the description box is empty it must be passed as null instead of an empty string ""
    //TODO Bug*     : Login is taking too long. Must look into it.
    //TODO Bug*     : In the ReportDialog there is a small bug that must be investigated.
    //                When the date is 29 of February and the year changes to a non leap year the day defaults to 1 instead of 28.
    //TODO Refactor : Find a non deprecated way to implement the NetworkTester Class


}