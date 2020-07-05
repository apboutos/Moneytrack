package com.apboutos.moneytrack.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.apboutos.moneytrack.model.entity.Category

@Dao
interface CategoryDAO {

    @Insert
    fun insert(category: Category)

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)

}