package com.apboutos.moneytrack.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials")
data class Credentials(@PrimaryKey(autoGenerate = false) val username : String, val password : String) {
}