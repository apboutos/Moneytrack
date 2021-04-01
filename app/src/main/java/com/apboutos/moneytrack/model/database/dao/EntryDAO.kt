package com.apboutos.moneytrack.model.database.dao

import androidx.room.*
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import com.apboutos.moneytrack.model.database.entity.Entry


@Dao
interface EntryDAO {

    /**
     * Inserts a new Entry.
     */
    @Insert
    fun insert(entry : Entry)

    /**
     * Updates an existing Entry.
     */
    @Update
    fun update(entry : Entry)

    /**
     * Deletes an existing Entry.
     */
    @Delete
    fun delete(entry : Entry)

    /**
     * Marks the Entry with the matching id as deleted.
     */
    @Query("UPDATE entry SET isDeleted = 1 WHERE id = :id")
    fun markAsDeleted(id : String)

    /**
     * Returns an Entry with the matching id. Returns null if no match was found.
     */
    @Query("SELECT * FROM entry WHERE id = :id")
    fun selectEntry(id : String) : Entry?

    /**
     * Returns a List containing all the entries of the specified user that have been updated after the given Datetime.
     */
    @Query("SELECT * FROM entry WHERE username = :username AND lastUpdate > :lastPushDatetime")
    fun selectModifiedEntries(username: String, lastPushDatetime : Datetime) : List<Entry>

    /**
     * Returns a List containing all the entries of the specified user.
     */
    @Query("SELECT date FROM entry WHERE username = :username")
    fun selectAllEntryDatesOfUser(username: String) : List<Date>

    /**
     * Returns a List containing all the entries of the specified user on a given Date.
     */
    @Query("SELECT * FROM entry WHERE date = :date AND username = :username")
    fun selectAllEntriesOfDate(date : Date, username : String) : List<Entry>

    /**
     * Returns a List containing all the entries of the specified user on a given Date that are not marked as deleted.
     */
    @Query("SELECT * FROM entry WHERE date = :date AND username = :username AND isDeleted = 0")
    fun selectAllNonDeletedEntriesOfDate(date : Date, username : String) : List<Entry>

    /**
     * Returns a List containing all the entries of the specified user that match the given criteria and are not marked as deleted.
     */
    @Query("SELECT * FROM entry WHERE username = :username AND date >= :from AND date <= :until AND (:type IS NULL OR type = :type) AND (:category IS NULL OR category = :category) AND (:description IS NULL OR description = :description) AND isDeleted = 0")
    fun selectAllEntriesOfSummary(username: String, from: Date,until: Date, type : String?, category : String?, description: String?) : List<Entry>

    /**
     * Returns a double value representing the sum of the "amount" column of all the entries of the specified user that are not marked as deleted.
     */
    @Query("SELECT SUM(amount) FROM entry WHERE username = :username AND isDeleted = 0")
    fun selectEntrySumOfLifetime(username: String) : Double

    /**
     * Returns a double value representing the sum of the "amount" column of all the entries of the specified user that are not marked as deleted and fit within the given Date range.
     */
    @Query("SELECT SUM(amount) FROM entry WHERE username = :username AND date >= :from AND date <= :until AND isDeleted = 0")
    fun selectEntrySumOfDateRange(username: String, from: Date, until: Date) : Double
}