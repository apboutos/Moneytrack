package com.apboutos.moneytrack


import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import org.junit.Assert.*
import org.junit.Test

class ConverterTest{

    @Test
    fun testTimeStampToDate(){

        val c = Date()
        var date = "1989-12-13"
        assertEquals(date , c.timestampToDate(c.dateToTimestamp(Date(date))).date)
        date = "1989-02-03"
        assertEquals(date , c.timestampToDate(c.dateToTimestamp(Date(date))).date)
    }

    @Test
    fun testTimeStampToDateTime(){

        val c = Datetime()
        val date = "1989-12-13 13:21:44"
        assertEquals(date , c.timestampToDatetime(c.datetimeToTimestamp(Datetime(date))).datetime)
    }



}