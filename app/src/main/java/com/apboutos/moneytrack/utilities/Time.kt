@file:Suppress("unused")

package com.apboutos.moneytrack.utilities

import android.icu.text.SimpleDateFormat
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import java.util.*

object Time {

    /**
     * Returns a Datetime containing the current timestamp.
     */
    fun getTimestamp() : Datetime{
        return Datetime(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(java.util.Date()))
    }

    /**
     * Returns a Date containing the current date.
     */
    fun getDate() : Date{
        return Date(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(java.util.Date()))
    }

    /**
     * Returns a String containing the current timestamp in yyyyMMddHHmmss format to be used as part of an Entry id.
     */
    fun getIdTimestamp() : String {
        return SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(java.util.Date())
    }
}