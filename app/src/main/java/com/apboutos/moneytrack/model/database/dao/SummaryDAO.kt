package com.apboutos.moneytrack.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apboutos.moneytrack.model.database.entity.Summary

@Dao
interface SummaryDAO {

    /**
     * Inserts a new Summary.
     */
    @Insert
    fun insert(summary : Summary)

    /**
     * Deletes all summaries.
     */
    @Query("DELETE FROM summary")
    fun deleteAllSummaries()

    /**
     * Returns a list containing all existing summaries.
     */
    @Query("SELECT * FROM summary")
    fun selectAllSummaries() : List<Summary>
}