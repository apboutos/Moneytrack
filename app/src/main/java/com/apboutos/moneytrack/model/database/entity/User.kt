package com.apboutos.moneytrack.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(

    @PrimaryKey(autoGenerate = false)
    var username         : String,
    var password         : String,
    var email            : String,
    var registrationDate : String,
    var lastLogin        : String)
{

}