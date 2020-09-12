package com.apboutos.moneytrack.model.database.converter

import org.junit.Assert.*
import org.junit.Test

class DatetimeTest{

    @Test
    fun testIsBefore(){
        assertTrue(Datetime("2020-09-04 21:00:00").isBefore(Datetime("2020-09-05 21:00:00")))
    }
}