package com.apboutos.moneytrack.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apboutos.moneytrack.model.database.entity.Summary

@Dao
interface SummaryDAO {

    @Insert
    fun insert(summary : Summary)

    @Query("DELETE FROM summary")
    fun deleteAllSummaries()
}