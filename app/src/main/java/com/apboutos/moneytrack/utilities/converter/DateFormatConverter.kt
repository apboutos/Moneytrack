package com.apboutos.moneytrack.utilities.converter

import android.content.Context
import android.icu.util.GregorianCalendar
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.database.converter.Date
import java.io.Serializable

object DateFormatConverter : Serializable {

    /**
     * Returns a String containing a date in database format yyyy-MM-dd.
     */
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

    /**
     * Returns a String containing the day number without the starting zero. 07 -> 7.
     */
    fun cropStartingZeroFrom(day : String) : String{
        if(day.startsWith("0")) return day.drop(1)
        return day
    }

    /**
     * Adds a starting zero to the day if it needs one. 7 -> 07.
     */
    fun normalizeDay(day : String) : String{
        if (day.length == 1){
            return "0$day"
        }
        return day
    }

    /**
     * Returns a String containing the internationalized name of the specified month.
     */
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

    /**
     * Returns a String containing the number of the specified month. February -> 02
     */
    fun getMonthNumber(month : String, context: Context) : String{
        return when(month){
            context.resources.getString(R.string.month1) -> "01"
            context.resources.getString(R.string.month2) -> "02"
            context.resources.getString(R.string.month3) -> "03"
            context.resources.getString(R.string.month4) -> "04"
            context.resources.getString(R.string.month5) -> "05"
            context.resources.getString(R.string.month6) -> "06"
            context.resources.getString(R.string.month7) -> "07"
            context.resources.getString(R.string.month8) -> "08"
            context.resources.getString(R.string.month9) -> "09"
            context.resources.getString(R.string.month10) -> "10"
            context.resources.getString(R.string.month11) -> "11"
            else -> "12"
        }
    }


    /**
     * Returns an array of Strings containing all the internationalized names of months.
     */
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

    /**
     * Returns a String containing the number of days the specified month has.
     * @param month The month in MM format.
     */
    fun getLastDayOfMonth(month: String) : String{
        return when(month){
            "01","03","05","06","07","08","10","12" -> "31"
            "02" -> "28"
            else -> "30"
        }
    }

    /**
     * Returns a String containing the number of days the specified month has.
     * Respects leap years.
     * @param month The month in MM format.
     * @param year The year in yyyy format.
     */
    fun getLastDayOfMonth(month: String, year: String) : String{
        return when(month){
            "01","03","05","06","07","08","10","12" -> "31"
            "02" -> if(isLeapYear(year)) "29" else "29"
            else -> "30"
        }
    }

    /**
     * Returns an array of Strings containing all the days of a specified month in d format.
     * @param month The month in MM format.
     * @param year the year in yyyy format.
     */
    fun getDaysOfMonth(month : String, year: String) : Array<String>{
        return when(month){
            "01","03","05","06","07","08","10","12" -> arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31")
            "02"                                    -> if(isLeapYear(year)) arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29")
                                                       else arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28")
            else                                    -> arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30")
        }
    }

    /**
     * Returns a String containing a displayable format of the specified date.
     * 2020-01-03 -> 3-January-2020.
     * @param date The date in yyyy-MM-dd format.
     * @param context The application's context.
     * @return a date in d-MonthName-yyyy format.
     */
    fun parseToDisplayableDate(date : String, context: Context) : String{
        val tmp = Date(date)
        val sb = StringBuilder()
        sb.append(cropStartingZeroFrom(tmp.day))
        sb.append(" ")
        sb.append(getMonthName(tmp.month,context))
        sb.append(" ")
        sb.append(tmp.year)
        return sb.toString()
    }

    /**
     * Returns a String containing a displayable format of the specified date.
     * 2020-01-03 -> 3-1-2020.
     * @param date The date in yyyy-MM-dd format.
     * @return a date in d-M-yyyy format.
     */
    fun parseToDisplayableFormat(date: String) : String{
        val tmp = Date(date)
        val sb = StringBuilder()
        sb.append(cropStartingZeroFrom(tmp.day))
        sb.append("-")
        sb.append(cropStartingZeroFrom(tmp.month))
        sb.append("-")
        sb.append(tmp.year)
        return sb.toString()
    }

    /**
     * Checks whether the specified year is a leap year or not.
     */
    private fun isLeapYear(year : String) : Boolean{
        return GregorianCalendar().isLeapYear(Integer.parseInt(year))
    }
}