package com.apboutos.moneytrack.model.database.dao

import androidx.room.*
import com.apboutos.moneytrack.model.database.entity.User


@Dao
interface UserDAO {

    @Insert
    fun insert(user : User)

    @Update
    fun update(user : User)

    @Delete
    fun delete(user : User)

    @Query("SELECT * FROM user WHERE username = :username")
    fun selectUserBy(username : String) : User
}