package com.apboutos.moneytrack

import org.junit.Test

import org.junit.Assert.*
import java.text.DateFormat
import java.util.*
import java.sql.Date
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)


        val date1 : java.util.Date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2000-1-9 20:45:30")
        val date2 : java.util.Date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2000-1-9 20:45:31")
        val date = Date(date1.time)
        val format = SimpleDateFormat("yyyy-MM-dd")
        println(format.format(date))

        assertTrue(date1.time < date2.time)
        println(date1.time)
        println(date2.time)
    }
}