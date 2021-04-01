@file:Suppress("unused")

package com.apboutos.moneytrack.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import com.apboutos.moneytrack.model.database.entity.Category
import com.apboutos.moneytrack.model.database.entity.Entry
import com.apboutos.moneytrack.model.database.entity.Summary
import com.apboutos.moneytrack.model.repository.local.DatabaseRepository
import com.apboutos.moneytrack.model.repository.remote.OnlineRepository
import com.apboutos.moneytrack.utilities.Time
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class LedgerActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val tag = "LedgerActivityViewModel"
    private val databaseRepository by lazy { DatabaseRepository(application) }
    private val onlineRepository by lazy { OnlineRepository(application) }

    lateinit var currentUser: String
    lateinit var lastPullRequestDatetime : String
    lateinit var lastPushRequestDatetime : String
    var currentDate: String = Time.getDate().date
    val entryList: ArrayList<Entry> by lazy { ArrayList() }

    init {
        setBaseCategoriesList()
    }

    /**
     * Advances the currentDate by a day.
     */
    fun goToNextDay() {
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(currentDate) ?: java.util.Date()
        calendar.add(Calendar.DATE,1)
        currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        loadEntries()
    }

    /**
     * Reduces the currentDate by a day.
     */
    fun goToPreviousDay() {
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(currentDate) ?: java.util.Date()
        calendar.add(Calendar.DATE,-1)
        currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        loadEntries()
    }

    /**
     * Stores a default list of categories to the repository.
     */
    private fun setBaseCategoriesList() {
        val list = arrayListOf(
            "bill",
            "consumable",
            "electronic",
            "entertainment",
            "food",
            "gift",
            "house item",
            "junk food",
            "loan",
            "medical",
            "miscellaneous",
            "paycheck",
            "transportation"
        )
        runBlocking {
            for (i in list) {
                databaseRepository.insert(Category(i))
            }
        }
    }

    /**
     * Returns an array of Strings of the years that contain entries.
     * The year of the currentDate is added regardless of whether it contains entries or not.
     */
    fun getYearsThatContainEntries(): Array<String> {

        lateinit var list : List<String>
        runBlocking {
            list = databaseRepository.selectAllEntryDatesOfUser(currentUser).stream()
                .map { e -> e.year }
                .distinct()
                .collect(Collectors.toList())
            list = list.plus(Date(currentDate).year)
            list.forEach { e -> Log.d(tag, "set year= $e") }
        }
        return list.toTypedArray()
    }

    /**
     * Returns a double value containing the sum of the amount of all the entries of the current User
     * that are not marked as deleted and fit within the given Date range.
     */
    fun getSumOfDateRange(from: Date, until: Date): Double {
        val sum : Double
        runBlocking {
            sum = databaseRepository.selectSumAmountOfDateRange(currentUser, from, until)
        }
        return sum
    }

    /**
     * Returns a double value containing the sum of the amount of all the entries of the current User
     * that are not marked as deleted.
     */
    fun getSumOfLifetime(): Double {
        val sum: Double
        runBlocking {
            sum = databaseRepository.selectSumAmountOfLifetime(currentUser)
        }
        return sum
    }

    /**
     * Adds a new entry to the repository and to the entryList.
     */
    fun createEntry(entry: Entry) {
        runBlocking {
            databaseRepository.insert(entry)
            entryList.add(entry)
            Log.d(tag, "Entry Insert success. id: " + entry.id + " username: " + entry.username + " date: " + entry.date)
        }
    }

    /**
     * Updates and existing entry.
     */
    fun updateEntry(position: Int, entry: Entry) {
        runBlocking {
            databaseRepository.update(entry)
            entryList[position] = entry
            Log.d(tag, "Entry update success. id: " + entry.id)
        }
    }

    /**
     * Retrieves the entry at the specified index from the entryList.
     */
    fun getEntry(position: Int): Entry {
        return entryList[position]
    }

    /**
     * Marks the Entry at the specified index of the entryList as deleted.
     */
    fun markEntryAsDeleted(position: Int){
        val entry = entryList[position]
        entry.isDeleted = true
        entry.lastUpdate = Time.getTimestamp()
        runBlocking {
            databaseRepository.update(entry)
            entryList.removeAt(position)
            Log.d(tag, "Entry marked as deleted success. id: " + entry.id)
        }
    }

    /**
     * Removes the Entry at the specified index from the repository and the entryList.
     */
    fun removeEntry(position: Int) {
        runBlocking {
            databaseRepository.delete(entryList[position])
            entryList.removeAt(position)
            Log.d(tag, "Entry Delete success. id: ")
        }
    }

    /**
     * Returns a list containing all the entries of the currentUser at the currentDate.
     */
    fun loadEntries(): ArrayList<Entry> {
        entryList.clear()
        runBlocking {
            entryList.addAll(databaseRepository.selectAllNonDeletedEntriesOfDate(currentDate, currentUser))
        }
        return entryList
    }

    /**
     * Returns a list containing all the entries that match the specified criteria of the Summary.
     */
    fun loadEntriesOfSearch(summary: Summary): ArrayList<Entry> {
        entryList.clear()
        runBlocking {
            entryList.addAll(databaseRepository.selectAllEntriesOfSummary(summary))
            databaseRepository.insert(summary)
        }
        return entryList
    }

    /**
     * Returns an array of String containing all the existing categories.
     */
    fun getCategories(): ArrayList<String> {
        val list : List<Category>
        runBlocking {
            list = databaseRepository.selectAllCategories()
        }
        val arrayList = ArrayList<String>()
        for (i in list) {
            arrayList.add(i.name)
        }
        return arrayList
    }

    /**
     * Queries the remote database for all the data of the current user that are more
     * recent than the datetime of the last pull request.
     */
    fun pullDataFromRemoteDatabase(){
        onlineRepository.pullData(currentUser,lastPullRequestDatetime)
    }

    /**
     * Updates the local repository with the entries received from the remove repository
     * contained in the entryList.
     */
    fun updateDatabaseWithReceivedRemoteEntries(entryList : ArrayList<Entry>){

        runBlocking {
            for (i in entryList){
                val tmp = databaseRepository.selectEntry(i.id)
                if(tmp != null){
                    Log.d(tag,"entry in database ${tmp.id} ${tmp.description} ${tmp.lastUpdate} ${tmp.isDeleted}")
                    if(tmp.lastUpdate.isBefore(i.lastUpdate) && tmp.id != ""){
                        Log.d(tag,"${tmp.description} ${tmp.lastUpdate} is before ${i.description} ${i.lastUpdate}")
                        if(i.isDeleted){
                            databaseRepository.delete(i)
                            Log.d(tag,"${i.description} ${i.lastUpdate} ${i.isDeleted} was deleted")
                        }
                        else{
                            databaseRepository.update(i)
                        }
                    }
                }
                else{
                    databaseRepository.insert(i)
                }
            }
            loadEntries()
        }

    }

    /**
     * Sends all the entries of the currentUser created or modified after the datetime
     * of the last push request to the remote database.
     */
    fun pushModifiedDataToRemoteDatabase(){
        viewModelScope.launch {
            val list = databaseRepository.selectModifiedEntries(currentUser, Datetime(lastPushRequestDatetime))
            Log.d(tag,"lastPushRequestDatetime = $lastPushRequestDatetime")
            for (i in list){
                Log.d(tag,"For pushing ${i.description} ${i.date} ${i.lastUpdate} ${i.isDeleted}")
            }
            onlineRepository.pushData(list)
        }
    }

}