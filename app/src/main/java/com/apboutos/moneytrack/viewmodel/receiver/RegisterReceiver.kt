package com.apboutos.moneytrack.viewmodel.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.apboutos.moneytrack.utilities.error.RegisterError
import com.apboutos.moneytrack.view.RegisterActivity

class RegisterReceiver(private val parentActivity: RegisterActivity) : BroadcastReceiver() {

    private val tag = "RegisterReceiver"
    override fun onReceive(context: Context, intent: Intent) {
        Log.e(tag,"I'm receiving.")
        when (intent.getStringExtra("error")) {
            "SERVER_UNREACHABLE" -> parentActivity.handleResponse(RegisterError.SERVER_UNREACHABLE)
            "WRONG_USERNAME" -> parentActivity.handleResponse(RegisterError.USERNAME_TAKEN)
            "WRONG_PASSWORD" -> parentActivity.handleResponse(RegisterError.EMAIL_TAKEN)
            "NO_INTERNET" -> parentActivity.handleResponse(RegisterError.NO_INTERNET)
            "NO_ERROR" -> parentActivity.handleResponse(RegisterError.NO_ERROR)
        }
    }

    companion object {
        const val SERVER_REGISTER_RESPONSE = "server"
    }

}