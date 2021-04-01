@file:Suppress("unused")

package com.apboutos.moneytrack.model.database.entity

import android.os.Parcelable
import androidx.room.*
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import com.apboutos.moneytrack.utilities.Time
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Represents an entry in the ledger.
 */
@Entity(tableName = "entry")
@TypeConverters(Date::class,Datetime::class)
@Parcelize
data class Entry(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    @Expose
    var id : String,
    @SerializedName("username")
    @Expose
    var username : String,
    @SerializedName("type")
    @Expose
    var type : String,
    @SerializedName("description")
    @Expose
    var description : String,
    @SerializedName("category")
    @Expose
    var category : String,
    @SerializedName("amount")
    @Expose
    var amount : Double,
    @SerializedName("date")
    @Expose
    var date : Date,
    @SerializedName("lastUpdate")
    @Expose
    var lastUpdate : Datetime,
    @SerializedName("isDeleted")
    @Expose
    var isDeleted : Boolean) : Parcelable
{
    companion object {

        /**
         * Creates an Entry object initialized with default values.
         */
        fun createEmptyEntry() : Entry {
            return Entry("","","","","",0.00,Date("2020-12-12"),Datetime("2020-12-12 12:12:12"),false)
        }

        /**
         * Creates a new ID. The ID is the concatenation of the user and the current timestamp.
         */
        fun createId(username: String) : String {
            return username + Time.getIdTimestamp()
        }
    }

}
