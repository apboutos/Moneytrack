package com.apboutos.moneytrack.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry")
data class Entry(

    @PrimaryKey(autoGenerate = false)
    var id : String,
    var username : String,
    var type : String,
    var description : String,
    var category : String,
    var amount : Double,
    var date : String,
    var lastUpdate : String,
    var isDeleted : Boolean)
{
    companion object{
        fun createEmptyEntry() : Entry{
            return Entry("","","","","",0.00,"","",false)
        }
    }
}
