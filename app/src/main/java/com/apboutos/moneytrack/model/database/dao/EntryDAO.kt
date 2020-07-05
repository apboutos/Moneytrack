package com.apboutos.moneytrack.model.dao

import androidx.room.*
import com.apboutos.moneytrack.model.entity.Entry

@Dao
interface EntryDAO {

    @Insert
    fun insert(entry : Entry)

    @Update
    fun update(entry : Entry)

    @Delete
    fun delete(entry : Entry)

    @Query("DELETE FROM entry")
    fun deleteAllEntries()

    @Query("SELECT * FROM entry WHERE date = :date AND username = :username")
    fun selectAllEntriesWhere(date : String , username : String) : List<Entry>

    @Query("SELECT * FROM entry WHERE username = :username AND category = :category AND type = :type AND date >= :fromDate AND date <= :untilDate")
    fun selectAllEntriesWhere(username: String, category: String, type : String , fromDate : String, untilDate : String) : List<Entry>
}