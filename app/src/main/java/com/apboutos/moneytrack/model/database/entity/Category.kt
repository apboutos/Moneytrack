package com.apboutos.moneytrack.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(

    @PrimaryKey(autoGenerate = false)
    var name : String) {
}