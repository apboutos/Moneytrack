@file:Suppress("unused")

package com.apboutos.moneytrack.model.database.entity

import androidx.room.*
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime


@Entity(tableName = "entry")
@TypeConverters(Date::class,Datetime::class)
data class Entry(

    @PrimaryKey(autoGenerate = false)
    var id : String,
    var username : String,
    var type : String,
    var description : String,
    var category : String,
    var amount : Double,
    var date : Date,
    var lastUpdate : Datetime,
    var isDeleted : Boolean)
{
    companion object{
        fun createEmptyEntry() : Entry {
            return Entry("","","","","",0.00,Date(""),Datetime(""),false)
        }
    }

}
