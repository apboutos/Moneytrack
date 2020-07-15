package com.apboutos.moneytrack.model.database.dao

import androidx.room.*
import com.apboutos.moneytrack.model.database.entity.Category

@Dao
interface CategoryDAO {

    @Insert
    fun insert(category: Category)

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("SELECT * FROM category")
    fun selectAllCategories() : List<Category>

}