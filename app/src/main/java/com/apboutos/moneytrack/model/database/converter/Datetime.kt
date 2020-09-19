@file:Suppress("unused","SimpleDateFormat")

package com.apboutos.moneytrack.model.database.converter

import android.os.Parcel
import android.os.Parcelable
import androidx.room.TypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Datetime() : Parcelable{

    @SerializedName("lastUpdate")
    @Expose
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

    fun isBefore(dateTime: Datetime) : Boolean {
        val tmp1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.datetime)
        val tmp2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime.datetime)
        if (tmp1.before(tmp2)) return true
        return false
    }

    @TypeConverter
    fun timestampToDatetime(value : Long): Datetime = Datetime(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date(value)))

    @TypeConverter
    fun datetimeToTimestamp(dateTime: Datetime) : Long = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime.datetime).time


    //Parcelable implementation.
    constructor(parcel: Parcel) : this() {
        datetime = parcel.readString() ?: "parcel error"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(datetime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Datetime> {
        override fun createFromParcel(parcel: Parcel): Datetime {
            return Datetime(parcel)
        }

        override fun newArray(size: Int): Array<Datetime?> {
            return arrayOfNulls(size)
        }

        fun currentDatetime() : String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return current.format(formatter)
        }

    }

}