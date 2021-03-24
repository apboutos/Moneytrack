package com.apboutos.moneytrack.model.database.dao

import androidx.room.*
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import com.apboutos.moneytrack.model.database.entity.Entry


@Dao
interface EntryDAO {

    @Insert
    fun insert(entry : Entry)

    @Update
    fun update(entry : Entry)

    @Delete
    fun delete(entry : Entry)

    @Query("UPDATE entry SET isDeleted = 1 WHERE id = :id")
    fun markAsDeleted(id : String)

    @Query("SELECT * FROM entry WHERE id = :id")
    fun selectEntry(id : String) : Entry

    @Query("SELECT * FROM entry WHERE username = :username AND lastUpdate > :lastPushDatetime")
    fun selectModifiedEntries(username: String, lastPushDatetime : Datetime) : List<Entry>

    @Query("SELECT date FROM entry WHERE username = :username")
    fun selectAllEntryDatesOfUser(username: String) : List<Date>

    @Query("SELECT * FROM entry WHERE date = :date AND username = :username")
    fun selectAllEntriesOfDate(date : Date, username : String) : List<Entry>

    @Query("SELECT * FROM entry WHERE date = :date AND username = :username AND isDeleted = 0")
    fun selectAllNonDeletedEntriesOfDate(date : Date, username : String) : List<Entry>

    @Query("SELECT * FROM entry WHERE username = :username AND date >= :from AND date <= :until AND (:type IS NULL OR type = :type) AND (:category IS NULL OR category = :category) AND (:description IS NULL OR description = :description) AND isDeleted = 0")
    fun selectAllEntriesOfSummary(username: String, from: Date,until: Date, type : String?, category : String?, description: String?) : List<Entry>

    @Query("SELECT SUM(amount) FROM entry WHERE username = :username AND isDeleted = 0")
    fun selectEntrySumOfLifetime(username: String) : Double

    @Query("SELECT SUM(amount) FROM entry WHERE username = :username AND date >= :from AND date <= :until AND isDeleted = 0")
    fun selectEntrySumOfDateRange(username: String, from: Date, until: Date) : Double
}