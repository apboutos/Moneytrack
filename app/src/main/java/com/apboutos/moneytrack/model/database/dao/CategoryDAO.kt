package com.apboutos.moneytrack.model.database.dao

import androidx.room.*
import com.apboutos.moneytrack.model.database.entity.Category

@Dao
interface CategoryDAO {

    /**
     * Inserts a new Category.
     */
    @Insert
    fun insert(category: Category)

    /**
     * Updates an existing Category.
     */
    @Update
    fun update(category: Category)

    /**
     *Deletes an existing Category.
     */
    @Delete
    fun delete(category: Category)

    /**
     * Returns a list containing all the existing categories.
     */
    @Query("SELECT * FROM category")
    fun selectAllCategories() : List<Category>

}