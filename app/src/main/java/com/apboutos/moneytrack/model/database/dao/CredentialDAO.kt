package com.apboutos.moneytrack.model.database.dao

import androidx.room.*
import com.apboutos.moneytrack.model.database.entity.Credential

@Dao
interface CredentialDAO {

    /**
     * Inserts a new Credential.
     */
    @Insert
    fun insert(credential : Credential)

    /**
     * Deletes all existing credentials.
     */
    @Query("DELETE FROM credential")
    fun deleteAll()

    /**
     * Returns the most recent Credential.
     */
    @Query("SELECT * FROM credential LIMIT 1")
    fun select() : Credential
}