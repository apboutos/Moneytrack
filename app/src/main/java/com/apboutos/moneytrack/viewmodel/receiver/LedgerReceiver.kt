package com.apboutos.moneytrack.viewmodel.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.apboutos.moneytrack.model.database.entity.Entry
import com.apboutos.moneytrack.view.LedgerActivity


class LedgerReceiver(private val parentActivity: LedgerActivity) : BroadcastReceiver() {

    private val tag = "LedgerReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {

        val list = intent?.getParcelableArrayListExtra<Entry>("entryList")
        parentActivity.viewModel.updateDatabaseWithReceivedRemoteEntries(list)

    }

    companion object {
        const val SERVER_PULL_DATA_RESPONSE = "server"
        const val SERVER_PUSH_DATA_RESPONSE = "server"
    }
}