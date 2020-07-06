package com.apboutos.moneytrack.model.database.dao

import androidx.room.*
import com.apboutos.moneytrack.model.database.entity.Credential

@Dao
interface CredentialDAO {

    @Insert
    fun insert(credential : Credential)

    @Query("DELETE FROM credential")
    fun delete()

    @Query("SELECT * FROM credential LIMIT 1")
    fun select() : Credential
}