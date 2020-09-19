@file:Suppress("unused","SimpleDateFormat")

package com.apboutos.moneytrack.model.database.converter

import android.os.Parcel
import android.os.Parcelable
import androidx.room.TypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat

class Date() : Parcelable{

    @SerializedName("date")
    @Expose
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

    //Parcelable implementation.
    constructor(parcel: Parcel) : this() {
        date = parcel.readString() ?: "parcel error"
        year = date.substring(0..3)
        month = date.substring(5..6)
        day = date.substring(8..9)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Date> {
        override fun createFromParcel(parcel: Parcel): Date {
            return Date(parcel)
        }

        override fun newArray(size: Int): Array<Date?> {
            return arrayOfNulls(size)
        }
    }

}