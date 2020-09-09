@file:Suppress("unused")

package com.apboutos.moneytrack.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.entity.Category
import com.apboutos.moneytrack.model.database.entity.Entry
import com.apboutos.moneytrack.model.database.entity.Summary
import com.apboutos.moneytrack.model.repository.local.DatabaseRepository
import com.apboutos.moneytrack.model.repository.remote.OnlineRepository
import com.apboutos.moneytrack.utilities.Time
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LedgerActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val tag = "LedgerActivityViewModel"
    private val databaseRepository by lazy { DatabaseRepository(application) }
    private val onlineRepository by lazy { OnlineRepository(application) }

    lateinit var currentUser : String
    var currentDate : String = Time.getDate().date
    val entryList : ArrayList<Entry> by lazy{ ArrayList<Entry>() }

    init {
        setBaseCategoriesList()
    }

    fun goToNextDay(){
        val tmp = LocalDate.parse(currentDate).plusDays(1)
        currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(tmp)
        loadEntries()
    }

    fun goToPreviousDay(){
        val tmp = LocalDate.parse(currentDate).minusDays(1)
        currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(tmp)
        loadEntries()
    }

    private fun setBaseCategoriesList(){
        val list = arrayListOf("bill","consumable","electronic","entertainment","food","gift","house item","junk food","loan"
            ,"medical","miscellaneous","paycheck","transportation")
        for (i in list){
            databaseRepository.insert(Category(i))
        }
    }

    fun getYearsThatContainEntries() : Array<String>{
        val list = databaseRepository.selectAllEntryDatesOfUser(currentUser)
        var set = setOf<String>()
        for(i in list){
            set = set.plus(i.year)
        }
        set = set.plus(Date(currentDate).year)
        for(i in set){
          Log.d(tag,"set year= $i")
        }
        return set.toTypedArray()
    }

    fun getSumOfDateRange(from: Date,until: Date) : Double{
        return databaseRepository.selectSumAmountOfDateRange(currentUser,from,until)
    }

    fun getSumOfLifetime() : Double{
        return databaseRepository.selectSumAmountOfLifetime(currentUser)
    }

    fun createEntry(entry : Entry){
        if(databaseRepository.insert(entry)){
            entryList.add(entry)
            Log.d(tag,"Entry Insert success. id: " + entry.id + " username: " + entry.username + " date: " + entry.date)
        }
        else{
            Log.d(tag,"Entry Insert failed. id: " + entry.id)
        }
    }

    fun updateEntry(position: Int, entry: Entry){
        Log.d(tag,"called")
        if(databaseRepository.update(entry)){
            entryList[position] = entry
            Log.d(tag,"Entry update success. id: " + entry.id)
        }else{
            Log.d(tag,"Entry update failed. id: " + entry.id)
        }
    }

    fun getEntry(position: Int) : Entry{
        return entryList[position]
    }

    fun removeEntry(position : Int){
        if(databaseRepository.delete(entryList[position])){
                entryList.removeAt(position)
                Log.d(tag,"Entry Delete success. id: ")
        }else{
            Log.d(tag,"Entry Delete failed. id: " + entryList[position].id + " username: " + entryList[position].username + " date: " + entryList[position].date)
        }

    }

    fun loadEntries () : ArrayList<Entry>{
        entryList.clear()
        entryList.addAll(databaseRepository.selectAllEntriesOfDate(currentDate,currentUser))
        Log.d(tag,"entryList.size: " + entryList.size)
        return entryList
    }

    fun loadEntriesOfSearch(summary: Summary) : ArrayList<Entry>{
        entryList.clear()
        entryList.addAll(databaseRepository.selectAllEntriesOfSummary(summary))
        Log.d(tag,"entryList.size: " + entryList.size)
        databaseRepository.insert(summary)
        return entryList
    }

    fun getCategories() : ArrayList<String>{
        val list = databaseRepository.selectAllCategories()
        val arrayList = ArrayList<String>()
        for(i in list){
            arrayList.add(i.name)
        }
        return arrayList
    }
}