package com.apboutos.moneytrack.view

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class CalendarDialogTest{

    private val startingCurrentDate = "2020-07-15"

    @Test
    fun setInitialDisplayDateTest(){

        val date = startingCurrentDate
        val parts = date.split("-".toRegex()).toTypedArray()
        val day = parts[2].toInt()
        var month = parts[1].toInt()
        val year = parts[0].toInt()
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day
        val milliTime = calendar.timeInMillis

        assertEquals(2020,calendar[Calendar.YEAR])
        assertEquals(7,calendar[Calendar.MONTH])
        assertEquals(15,calendar[Calendar.DAY_OF_MONTH])

    }
}