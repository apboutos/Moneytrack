package com.apboutos.moneytrack.model.database.dao

import androidx.room.*
import com.apboutos.moneytrack.model.database.entity.User


@Dao
interface UserDAO {

    /**
     * Inserts a new User.
     */
    @Insert
    fun insert(user : User)

    /**
     * Updates an existing User.
     */
    @Update
    fun update(user : User)

    /**
     * Deletes an existing User.
     */
    @Delete
    fun delete(user : User)

    /**
     * Returns a User with the matching username otherwise returns null.
     */
    @Query("SELECT * FROM user WHERE username = :username")
    fun selectUserBy(username : String) : User?
}