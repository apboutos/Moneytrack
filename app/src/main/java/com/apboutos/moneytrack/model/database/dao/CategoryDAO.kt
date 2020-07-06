package com.apboutos.moneytrack.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.apboutos.moneytrack.model.database.entity.Category

@Dao
interface CategoryDAO {

    @Insert
    fun insert(category: Category)

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)

}