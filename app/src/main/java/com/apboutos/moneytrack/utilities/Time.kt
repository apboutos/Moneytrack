@file:Suppress("unused")

package com.apboutos.moneytrack.utilities

import android.icu.text.SimpleDateFormat
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import java.util.*

object Time {

    fun getTimestamp() : Datetime{
        return Datetime(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(java.util.Date()))
    }

    fun getDate() : Date{
        return Date(SimpleDateFormat("yyyy-MM-dd", Locale.US).format(java.util.Date()))
    }

}