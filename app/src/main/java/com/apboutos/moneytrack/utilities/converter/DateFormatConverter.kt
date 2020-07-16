package com.apboutos.moneytrack.utilities.converter

import android.content.Context
import com.apboutos.moneytrack.R
import java.io.Serializable

object DateFormatConverter : Serializable {
    fun parseToDatabaseDateFromUserDate(year: Int, month: Int, day: Int): String {
        val tmp = StringBuilder()
        tmp.append(year)
        tmp.append("-")
        if(month < 10) tmp.append("0")
        tmp.append(month)
        tmp.append("-")
        if(day < 10) tmp.append("0")
        tmp.append(day)
        return tmp.toString()
    }

    fun cropStartingZeroFrom(day : String) : String{
        if(day.startsWith("0")) return day.drop(1)
        return day
    }

    fun normalizeDay(day : String) : String{
        if (day.length == 1){
            return "0$day"
        }
        return day
    }

    fun getMonthName(month : String, context: Context) : String{
        return when (month){
            "01" -> context.resources.getString(R.string.month1)
            "02" -> context.resources.getString(R.string.month2)
            "03" -> context.resources.getString(R.string.month3)
            "04" -> context.resources.getString(R.string.month4)
            "05" -> context.resources.getString(R.string.month5)
            "06" -> context.resources.getString(R.string.month6)
            "07" -> context.resources.getString(R.string.month7)
            "08" -> context.resources.getString(R.string.month8)
            "09" -> context.resources.getString(R.string.month9)
            "10" -> context.resources.getString(R.string.month10)
            "11" -> context.resources.getString(R.string.month11)
            else -> context.resources.getString(R.string.month12)
        }
    }

    fun getMonthNumber(month : String, context: Context) : String{
        return when(month){
            context.resources.getString(R.string.month1) -> "01"
            context.resources.getString(R.string.month1) -> "02"
            context.resources.getString(R.string.month1) -> "03"
            context.resources.getString(R.string.month1) -> "04"
            context.resources.getString(R.string.month1) -> "05"
            context.resources.getString(R.string.month1) -> "06"
            context.resources.getString(R.string.month1) -> "07"
            context.resources.getString(R.string.month1) -> "08"
            context.resources.getString(R.string.month1) -> "09"
            context.resources.getString(R.string.month1) -> "10"
            context.resources.getString(R.string.month1) -> "11"
            else -> "12"
        }
    }


    fun getListOfMonths(context: Context) : Array<String>{
        return arrayOf(context.resources.getString(R.string.month1),
            context.resources.getString(R.string.month2),
            context.resources.getString(R.string.month3),
            context.resources.getString(R.string.month4),
            context.resources.getString(R.string.month5),
            context.resources.getString(R.string.month6),
            context.resources.getString(R.string.month7),
            context.resources.getString(R.string.month8),
            context.resources.getString(R.string.month9),
            context.resources.getString(R.string.month10),
            context.resources.getString(R.string.month11),
            context.resources.getString(R.string.month12))
    }

    fun getLastDayOfMonth(month: String) : String{
        return when(month){
            "01","03","05","06","07","08","10","12" -> "31"
            "02" -> "28"
            else -> "30"
        }
    }

    fun getDaysOfMonth(month : String) : Array<String>{
        return when(month){
            "01","03","05","06","07","08","10","12" -> arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31")
            "02"                                    -> arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28")
            else                                    -> arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30")
        }
    }
}