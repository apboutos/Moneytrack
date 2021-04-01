@file:Suppress("unused", "PrivatePropertyName")

package com.apboutos.moneytrack.model.repository.local

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import com.apboutos.moneytrack.model.database.database.MoneytrackDatabase
import com.apboutos.moneytrack.model.database.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseRepository(application: Application) {

    private val tag = "DatabaseRepository"
    private val database      = MoneytrackDatabase.invoke(application)
    private val entryDAO      = database.EntryDAO()
    private val userDAO       = database.UserDAO()
    private val categoryDAO   = database.CategoryDAO()
    private val summaryDAO    = database.SummaryDAO()
    private val credentialDAO = database.CredentialDAO()

    /**
     * Inserts the specified Entry to the repository.
     */
    suspend fun insert(entry : Entry){
        withContext(Dispatchers.IO){
            try {
                entryDAO.insert(entry)
            }
            catch (e : SQLiteConstraintException){
                Log.d(tag,"Entry ${entry.id} already in database")
            }
            catch (e : SQLiteException){
                Log.d(tag,e.message ?: "SQLiteException")
            }
        }
    }

    /**
     * Updates the specified Entry.
     */
    suspend fun update(entry : Entry){
        withContext(Dispatchers.IO){
            entryDAO.update(entry)
        }
    }

    /**
     * Deletes the specified Entry from the repository.
     */
    suspend fun delete(entry: Entry){
        withContext(Dispatchers.IO){
            entryDAO.delete(entry)
        }
    }

    /**
     * Returns an Entry matching the specified id, otherwise returns null.
     */
    suspend fun selectEntry(id : String) : Entry?{
        val entry : Entry?
        withContext(Dispatchers.IO){
            entry = entryDAO.selectEntry(id)
        }
        return entry
    }

    /**
     * Returns a List containing all the entries of the specified user that have been updated after the given Datetime.
     */
    suspend fun selectModifiedEntries(username: String, lastPushDatetime : Datetime) : List<Entry> {
        val list  : List<Entry>
        withContext(Dispatchers.IO){
            list = entryDAO.selectModifiedEntries(username,lastPushDatetime)
        }
        return list
    }

    /**
     * Returns a List containing all the entries of the specified user.
     */
    suspend fun selectAllEntryDatesOfUser(username: String) : List<Date>{
        val list : List<Date>
        withContext(Dispatchers.IO){
            list = entryDAO.selectAllEntryDatesOfUser(username)
        }
        return list
    }

    /**
     * Returns a double value representing the sum of the "amount" column of all the entries of the specified user that are not marked as deleted.
     */
    suspend fun selectSumAmountOfLifetime(username: String) : Double{
        val sum : Double
        withContext(Dispatchers.IO){
            sum = entryDAO.selectEntrySumOfLifetime(username)
        }
        return sum
    }

    /**
     * Returns a double value representing the sum of the "amount" column of all the entries of the specified user that are not marked as deleted and fit within the given Date range.
     */
    suspend fun selectSumAmountOfDateRange(username: String, from: Date, until: Date) : Double{
        val sum : Double
        withContext(Dispatchers.IO){
            sum = entryDAO.selectEntrySumOfDateRange(username,from,until)
        }
        return sum
    }

    /**
     * Returns a List containing all the entries of the specified user on a given Date.
     */
    suspend fun selectAllEntriesOfDate(date : String, username : String) : List<Entry>{
        val list : List<Entry>
        withContext(Dispatchers.IO){
            list = entryDAO.selectAllEntriesOfDate(Date(date),username)
        }
        return list
    }

    /**
     * Returns a List containing all the entries of the specified user that match the given criteria and are not marked as deleted.
     */
    suspend fun selectAllNonDeletedEntriesOfDate(date : String, username : String) : List<Entry>{
        val list : List<Entry>
        withContext(Dispatchers.IO){
            list = entryDAO.selectAllNonDeletedEntriesOfDate(Date(date),username)
        }
        return list
    }

    /**
     * Returns a List containing all the entries of the specified user that match the given criteria and are not marked as deleted.
     */
    suspend fun selectAllEntriesOfSummary(summary: Summary) : List<Entry>{
        val list : List<Entry>
        withContext(Dispatchers.IO){
            list = entryDAO.selectAllEntriesOfSummary(summary.username,summary.fromDate,summary.untilDate,summary.type,summary.category,summary.description)
        }
       return list
    }

    /**
     * Insert the specified User to the Repository.
     */
    suspend fun insert(user : User){
        withContext(Dispatchers.IO){
            try {
                userDAO.insert(user)
            }
            catch (e : SQLiteConstraintException){
                Log.d(tag,"User $user already in database")
            }
            catch (e : SQLiteException){
                Log.d(tag,e.message ?: "SQLiteException")
            }
        }
    }

    /**
     * Update the specified User.
     */
    suspend fun update(user : User){
        withContext(Dispatchers.IO){
            userDAO.update(user)
        }
    }

    /**
     * Delete the specified User from the repository.
     */
    suspend fun delete(user : User){
        withContext(Dispatchers.IO){
            userDAO.delete(user)
        }
    }

    /**
     * Returns the User matching the specified username, otherwise returns null.
     */
    suspend fun selectUserBy(username : String) : User? {
        val user : User?
        withContext(Dispatchers.IO){
            user = userDAO.selectUserBy(username)
        }
        return user
    }

    /**
     * Insert the specified Category to the repository.
     */
    suspend fun insert(category: Category){
        withContext(Dispatchers.IO){
            try{
                categoryDAO.insert(category)
            }
            catch (e : SQLiteConstraintException){
                Log.d(tag,"Category ${category.name} already in database")
            }
            catch (e : SQLiteException){
                Log.d(tag,e.message ?: "SQLiteException")
            }
        }
    }

    /**
     * Update the specified Category.
     */
    suspend fun update(category: Category){
        withContext(Dispatchers.IO){
            categoryDAO.update(category)
        }
    }

    /**
     * Delete the specified Category from the repository.
     */
    suspend fun delete(category: Category){
        withContext(Dispatchers.IO){
            categoryDAO.delete(category)
        }
    }

    /**
     * Returns a List containing all the existing categories.
     */
    suspend fun selectAllCategories() : List<Category>{
        val list : List<Category>
        withContext(Dispatchers.IO){
            list = categoryDAO.selectAllCategories()
        }
        return list
    }

    /**
     * Insert the specified Summary to the repository.
     */
    suspend fun insert(summary: Summary){
        withContext(Dispatchers.IO){
            try{
                summaryDAO.insert(summary)
            }
            catch (e : SQLiteConstraintException){
                Log.d(tag,"Summary $summary already in database")
            }
            catch (e : SQLiteException){
                Log.d(tag,e.message ?: "SQLiteException")
            }
        }
    }

    /**
     * Delete all existing summaries in the repository.
     */
    suspend fun deleteAllSummaries(){
        withContext(Dispatchers.IO){
            summaryDAO.deleteAllSummaries()
        }
    }

    /**
     * Returns a List containing all existing summaries.
     */
    suspend fun selectAllSummaries() : List<Summary>{
        val list: List<Summary>
        withContext(Dispatchers.IO){
            list = summaryDAO.selectAllSummaries()
        }
        return list
    }

    /**
     * Inserts the specified Credential to the repository.
     */
    suspend fun insertCredential(credential: Credential){
        withContext(Dispatchers.IO){
            try{
                credentialDAO.insert(credential)
            }
            catch (e : SQLiteConstraintException){
                Log.d(tag,"Credential $credential already in database")
            }
            catch (e : SQLiteException){
                Log.d(tag,e.message ?: "SQLiteException")
            }
        }
    }

    /**
     * Deletes all existing credentials from the repository.
     */
    suspend fun deleteCredential(){
        withContext(Dispatchers.IO){
            credentialDAO.deleteAll()
        }
    }

    /**
     * Returns the most recent Credential in the repository. Returns null if no credentials exist.
     */
    suspend fun selectCredential() : Credential?{
        val credential : Credential?
        withContext(Dispatchers.IO){
            credential = credentialDAO.select()
        }
        return credential
    }
}