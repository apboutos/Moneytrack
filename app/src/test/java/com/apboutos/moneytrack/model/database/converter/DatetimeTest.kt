package com.apboutos.moneytrack.model.database.converter

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DatetimeTest{

    @Test
    fun testIsBefore(){
        assertTrue(Datetime("2020-09-04 21:00:00").isBefore(Datetime("2020-09-05 21:00:00")))
    }

    @Test
    fun currentDatetime(){

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")


        assertEquals(Datetime.currentDatetime(),current.format(formatter))
        println(current.format(formatter))
        println(Datetime.currentDatetime())
    }
}