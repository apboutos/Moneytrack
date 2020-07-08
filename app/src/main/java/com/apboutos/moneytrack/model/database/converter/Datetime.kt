@file:Suppress("unused","SimpleDateFormat")

package com.apboutos.moneytrack.model.database.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat

class Datetime(){

    lateinit var datetime : String
    constructor(datetime : String) : this(){
        this.datetime = datetime
    }

    override fun toString(): String {
        return datetime
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Datetime) return false
        if (datetime == other.datetime) return true
        return false
    }

    override fun hashCode(): Int {
        return datetime.hashCode()
    }

    @TypeConverter
    fun timestampToDatetime(value : Long): Datetime = Datetime(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date(value)))

    @TypeConverter
    fun datetimeToTimestamp(dateTime: Datetime) : Long = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime.datetime).time



}