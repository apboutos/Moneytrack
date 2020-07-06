package com.apboutos.moneytrack.model.database.converter

import androidx.room.TypeConverter
import java.util.*

class Converter {
    //TODO Implement class
    @TypeConverter
    fun fromTimestamp(value: Long): String {
        return "K"
    }

    @TypeConverter
    fun dateToTimestamp(date: String): Long {



        return 0;
    }

}