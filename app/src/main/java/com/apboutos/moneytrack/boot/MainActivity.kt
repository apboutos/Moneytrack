package com.apboutos.moneytrack.boot

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
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
}