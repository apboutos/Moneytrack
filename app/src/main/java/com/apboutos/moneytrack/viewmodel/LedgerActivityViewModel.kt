@file:Suppress("unused")

package com.apboutos.moneytrack.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import com.apboutos.moneytrack.model.database.entity.Entry
import com.apboutos.moneytrack.model.repository.local.DatabaseRepository
import com.apboutos.moneytrack.model.repository.remote.OnlineRepository

class LedgerActivityViewModel(application: Application) : AndroidViewModel(application) {

    val tag = "LedgerActivityViewModel"
    val databaseRepository by lazy { DatabaseRepository(application) }
    val onlineRepository by lazy { OnlineRepository(application) }

    lateinit var currentUser : String
    var currentDate : String = "2020-07-14"

    val entryList : ArrayList<Entry> by lazy{ ArrayList<Entry>() }


    fun chanceDate(date: String){
        currentDate = date
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
        Log.d("tag","entryList.size: " + entryList.size)
        return entryList
    }

    private fun insertMockDataToDatabase(){
        val list = createMockData("2020-07-13","2020-07-13 13:33:42")
        for(i in list){
            databaseRepository.insert(i)
        }
    }

    private fun createMockData(date : String , dateTime : String) : ArrayList<Entry>{
        val entryList = ArrayList<Entry>()

        entryList.add(Entry("1","exophrenik","Income","paycheck","paycheck",456.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("2","exophrenik","Expense","chicken","food",18.50, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("3","exophrenik","Expense","broccoli","food",2.10, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("4","exophrenik","Expense","tomatoes","food",3.30, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("5","exophrenik","Expense","tuna","food",7.40, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("6","exophrenik","Expense","salmon","food",13.50, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("7","exophrenik","Expense","eggs","food",3.00, Date(date),
            Datetime(dateTime),false))
        /*entryList.add(Entry("8","exophrenik","Expense","electricity","bill",173.33, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("9","exophrenik","Expense","water","bill",73.33, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("10","exophrenik","Expense","tooth extraction","medical",50.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("11","exophrenik","Expense","drinks","entertainment",8.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("12","exophrenik","Expense","toEat","junk food",5.60, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("13","exophrenik","Expense","netCafe","entertainment",10.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("14","exophrenik","Expense","axe","miscellaneous",5.75, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("15","exophrenik","Expense","beer","junk food",6.90, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("16","exophrenik","Expense","chips","junk food",1.40, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("17","exophrenik","Expense","pizza","junk food",8.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("18","exophrenik","Expense","crunch","junk food",2.60, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("19","exophrenik","Expense","caprice","junk food",3.10, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("20","exophrenik","Expense","cheetos","junk food",0.98, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("21","exophrenik","Income","paycheck","paycheck",456.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("22","exophrenik","Expense","chicken","food",18.50, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("23","exophrenik","Expense","broccoli","food",2.10, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("24","exophrenik","Expense","tomatoes","food",3.30, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("25","exophrenik","Expense","tuna","food",7.40, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("26","exophrenik","Expense","salmon","food",13.50, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("27","exophrenik","Expense","eggs","food",3.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("28","exophrenik","Expense","electricity","bill",173.33, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("29","exophrenik","Expense","water","bill",73.33, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("30","exophrenik","Expense","tooth extraction","medical",50.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("31","exophrenik","Expense","drinks","entertainment",8.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("32","exophrenik","Expense","toEat","junk food",5.60, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("33","exophrenik","Expense","netCafe","entertainment",10.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("34","exophrenik","Expense","axe","miscellaneous",5.75, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("35","exophrenik","Expense","beer","junk food",6.90, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("36","exophrenik","Expense","chips","junk food",1.40, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("37","exophrenik","Expense","pizza","junk food",8.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("38","exophrenik","Expense","crunch","junk food",2.60, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("39","exophrenik","Expense","caprice","junk food",3.10, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("40","exophrenik","Expense","cheetos","junk food",0.98, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("41","exophrenik","Income","paycheck","paycheck",456.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("42","exophrenik","Expense","chicken","food",18.50, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("43","exophrenik","Expense","broccoli","food",2.10, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("44","exophrenik","Expense","tomatoes","food",3.30, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("45","exophrenik","Expense","tuna","food",7.40, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("46","exophrenik","Expense","salmon","food",13.50, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("47","exophrenik","Expense","eggs","food",3.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("48","exophrenik","Expense","electricity","bill",173.33, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("49","exophrenik","Expense","water","bill",73.33, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("50","exophrenik","Expense","tooth extraction","medical",50.00, Date(
            date
        ), Datetime(dateTime),false))
        entryList.add(Entry("51","exophrenik","Expense","drinks","entertainment",8.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("52","exophrenik","Expense","toEat","junk food",5.60, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("53","exophrenik","Expense","netCafe","entertainment",10.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("54","exophrenik","Expense","axe","miscellaneous",5.75, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("55","exophrenik","Expense","beer","junk food",6.90, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("56","exophrenik","Expense","chips","junk food",1.40, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("57","exophrenik","Expense","pizza","junk food",8.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("58","exophrenik","Expense","crunch","junk food",2.60, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("59","exophrenik","Expense","caprice","junk food",3.10, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("60","exophrenik","Expense","cheetos","junk food",0.98, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("61","exophrenik","Income","paycheck","paycheck",456.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("62","exophrenik","Expense","chicken","food",18.50, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("63","exophrenik","Expense","broccoli","food",2.10, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("64","exophrenik","Expense","tomatoes","food",3.30, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("65","exophrenik","Expense","tuna","food",7.40, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("66","exophrenik","Expense","salmon","food",13.50, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("67","exophrenik","Expense","eggs","food",3.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("68","exophrenik","Expense","electricity","bill",173.33, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("69","exophrenik","Expense","water","bill",73.33, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("70","exophrenik","Expense","tooth extraction","medical",50.00, Date(
            date
        ), Datetime(dateTime),false))
        entryList.add(Entry("71","exophrenik","Expense","drinks","entertainment",8.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("72","exophrenik","Expense","toEat","junk food",5.60, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("73","exophrenik","Expense","netCafe","entertainment",10.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("74","exophrenik","Expense","axe","miscellaneous",5.75, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("75","exophrenik","Expense","beer","junk food",6.90, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("76","exophrenik","Expense","chips","junk food",1.40, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("77","exophrenik","Expense","pizza","junk food",8.00, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("78","exophrenik","Expense","crunch","junk food",2.60, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("79","exophrenik","Expense","caprice","junk food",3.10, Date(date),
            Datetime(dateTime),false))
        entryList.add(Entry("80","exophrenik","Expense","cheetos","junk food",0.98, Date(date),
            Datetime(dateTime),false))*/
        return entryList
    }

}