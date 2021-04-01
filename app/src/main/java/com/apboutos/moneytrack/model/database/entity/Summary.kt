@file:Suppress("unused")

package com.apboutos.moneytrack.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.apboutos.moneytrack.model.database.converter.Date

/**
 * Represents a search query. Summaries are stored in the database
 * so the user can reuse them.
 */
@Entity(tableName = "summary")
@TypeConverters(Date::class)
data class Summary(
    val username : String,
    val category: String,
    val type : String,
    val description : String,
    val fromDate : Date,
    val untilDate : Date)
{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}

//TODO Summary description , type and category can be null.