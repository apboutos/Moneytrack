@file:Suppress("unused")

package com.apboutos.moneytrack.model.repository.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


object NetworkTester {
    fun internetConnectionIsAvailable(context: Context): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo: Array<NetworkInfo>
        if (cm != null) {
            netInfo = cm.allNetworkInfo
            for (ni in netInfo) {
                if (ni.typeName.equals("WIFI", ignoreCase = true)) if (ni.isConnected) haveConnectedWifi = true
                if (ni.typeName.equals("MOBILE", ignoreCase = true)) if (ni.isConnected) haveConnectedMobile = true
            }
        }
        return haveConnectedWifi || haveConnectedMobile
    }
}