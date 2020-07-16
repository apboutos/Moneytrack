@file:Suppress("unused","SimpleDateFormat")

package com.apboutos.moneytrack.model.database.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat

class Date() {

    lateinit var date : String
    lateinit var year : String
    lateinit var month: String
    lateinit var day  : String

    constructor(date : String) : this(){
        this.date = date
        year = date.substring(0..3)
        month = date.substring(5..6)
        day = date.substring(8..9)
    }

    override fun toString(): String {
        return date
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Date) return false
        if (date == other.date) return true
        return false
    }

    override fun hashCode(): Int {
        return date.hashCode()
    }

    @TypeConverter
    fun timestampToDate(value: Long): Date = Date(SimpleDateFormat("yyyy-MM-dd").format(java.util.Date(value)))

    @TypeConverter
    fun dateToTimestamp(date : Date): Long = SimpleDateFormat("yyyy-MM-dd").parse(date.date).time;

}