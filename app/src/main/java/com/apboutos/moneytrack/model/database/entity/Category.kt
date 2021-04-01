package com.apboutos.moneytrack.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the category field of the Entry class.
 * This class is saved in the database so the user can create his own categories.
 */
@Entity(tableName = "category")
data class Category(

    @PrimaryKey(autoGenerate = false)
    var name : String) {
}