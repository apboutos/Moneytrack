package com.apboutos.moneytrack.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "summary")
data class Summary(
        val username : String,
        val category: String,
        val type : String,
        val fromDate : String,
        val untilDate : String)
{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}