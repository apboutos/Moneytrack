package com.apboutos.moneytrack.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the category of the field of the Entry class.
 * This class is saved in the database for future features
 * like the user being able to create custom categories.
 */
@Entity(tableName = "category")
data class Category(

    @PrimaryKey(autoGenerate = false)
    var name : String) {
}