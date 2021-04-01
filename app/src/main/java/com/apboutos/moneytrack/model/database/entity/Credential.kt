package com.apboutos.moneytrack.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a stored credential. The credential contain a username and password of a user.
 * It is stored in the SQLi database so the user can login without an internet connection.
 */
@Entity(tableName = "credential")
data class Credential(@PrimaryKey(autoGenerate = false) val username : String, val password : String) {
}