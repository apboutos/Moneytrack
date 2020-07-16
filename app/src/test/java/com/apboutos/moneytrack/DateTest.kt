package com.apboutos.moneytrack

import com.apboutos.moneytrack.model.database.converter.Date
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class DateTest {

    @Before
    fun setUp() {
    }

    @Test
    fun constructor(){
        assertEquals("2020",Date("2020-12-12").year)
        assertEquals("12",Date("2020-12-12").month)
        assertEquals("12",Date("2020-12-12").day)
    }

    @Test
    fun testToString() {
    }

    @Test
    fun testEquals() {
    }

    @Test
    fun testHashCode() {
    }

    @Test
    fun nextDate() {
        assertEquals("2020-12-14",Date("2020-12-13").nextDate())
        assertEquals("2020-12-01",Date("2020-11-30").nextDate())
        assertEquals("2021-01-01",Date("2020-12-31").nextDate())
        assertEquals("2020-02-29",Date("2020-02-28").nextDate())
    }

    @Test
    fun previousDate() {
    }

    @Test
    fun timestampToDate() {
    }

    @Test
    fun dateToTimestamp() {
    }
}