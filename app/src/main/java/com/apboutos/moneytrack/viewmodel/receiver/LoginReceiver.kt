package com.apboutos.moneytrack.viewmodel.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.apboutos.moneytrack.utilities.error.LoginError
import com.apboutos.moneytrack.view.LoginActivity

class LoginReceiver(private val parentActivity: LoginActivity) : BroadcastReceiver() {

    private val tag = "LoginReceiver"
    override fun onReceive(context: Context, intent: Intent) {
        Log.e(tag,"I'm receiving.")
        when (intent.getStringExtra("error")) {
            "SERVER_UNREACHABLE" -> parentActivity.handleResponse(LoginError.SERVER_UNREACHABLE)
            "WRONG_USERNAME" -> parentActivity.handleResponse(LoginError.WRONG_USERNAME)
            "WRONG_PASSWORD" -> parentActivity.handleResponse(LoginError.WRONG_PASSWORD)
            "NO_ERROR" -> parentActivity.handleResponse(LoginError.NO_ERROR)
        }
    }

    companion object {
        const val SERVER_LOGIN_RESPONSE = "server"
    }

}