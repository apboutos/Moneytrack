package com.apboutos.moneytrack.model.database.dao

import androidx.room.*
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.entity.Entry


@Dao
interface EntryDAO {

    @Insert
    fun insert(entry : Entry)

    @Update
    fun update(entry : Entry)

    @Delete
    fun delete(entry : Entry)

    @Query("SELECT * FROM entry WHERE date = :date AND username = :username")
    fun selectAllEntriesOfDate(date : Date, username : String) : List<Entry>

    @Query("SELECT * FROM entry WHERE username = :username AND category = :category AND type = :type AND date >= :fromDate AND date <= :untilDate")
    fun selectAllEntriesOfSummary(username: String, category: String, type : String, fromDate : Date, untilDate : Date) : List<Entry>
}