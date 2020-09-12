package com.apboutos.moneytrack.model.database.converter

import android.content.Intent
import android.os.Parcel
import com.apboutos.moneytrack.utilities.converter.CurrencyConverter
import org.junit.Assert.*
import org.junit.Test

class DateTest{

    @Test
    fun parcelableTest(){

       val dateList = arrayListOf(Date("2000-01-01"),Date("2001-01-01"))
       val intent = Intent()
        intent.putParcelableArrayListExtra("entryList",dateList)
        val tmp = intent.getParcelableArrayExtra("entryList")
        assertEquals(dateList,tmp)
    }
}