package com.apboutos.moneytrack.utilities.converter

import java.io.Serializable

object DateFormatConverter : Serializable {
    fun parseToDatabaseDateFromUserDate(year: Int, month: Int, day: Int): String {
        val tmp = StringBuilder()
        tmp.append(year)
        tmp.append("-")
        tmp.append(month)
        tmp.append("-")
        tmp.append(day)
        return tmp.toString()
    }

    fun parseToUserDateFromDatabaseDate(date: String?): String {
        val tmp = StringBuilder()
        val parts = date!!.split("-".toRegex()).toTypedArray()
        tmp.append(parts[2])
        tmp.append("-")
        tmp.append(parts[1])
        tmp.append("-")
        tmp.append(parts[0])
        return tmp.toString()
    }

    fun parseToDatabaseDateFromUserDate(date: String): String {
        val tmp = StringBuilder()
        val parts = date.split("-".toRegex()).toTypedArray()
        tmp.append(parts[2])
        tmp.append("-")
        tmp.append(parts[1])
        tmp.append("-")
        tmp.append(parts[0])
        return tmp.toString()
    }

    fun parseToDisplayableString(year: Int, month: Int, day: Int): String {
        val tmp = StringBuilder()
        tmp.append(day)
        tmp.append("-")
        tmp.append(month)
        tmp.append("-")
        tmp.append(year)
        return tmp.toString()
    }

    fun parseToIdString(year: Int, month: Int, day: Int): String {
        val tmp = StringBuilder()
        tmp.append(year)
        if (month < 10) tmp.append("0")
        tmp.append(month)
        if (day < 10) tmp.append("0")
        tmp.append(day)
        return tmp.toString()
    }
}