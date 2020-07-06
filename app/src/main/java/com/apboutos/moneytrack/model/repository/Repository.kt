package com.apboutos.moneytrack.model.repository

import android.app.Application
import android.os.AsyncTask
import com.apboutos.moneytrack.model.database.dao.EntryDAO
import com.apboutos.moneytrack.model.database.database.MoneytrackDatabase
import com.apboutos.moneytrack.model.database.entity.Entry
import com.apboutos.moneytrack.model.database.entity.Summary

class Repository(application: Application) {

    private val database    = MoneytrackDatabase.invoke(application)
    private val entryDAO    = database.EntryDAO()
    private val userDAO     = database.UserDAO()
    private val categoryDAO = database.CategoryDAO()


    fun insert(entry : Entry){
        InsertEntryAsyncTask(entryDAO).execute(entry)
    }

    fun update(entry : Entry){
        UpdateEntryAsyncTask(entryDAO).execute(entry)
    }

    fun delete(entry : Entry){
        DeleteEntryAsyncTask(entryDAO).execute(entry)
    }

    fun selectAllEntriesOfDate(date : String, username : String) : List<Entry>{
        return DateEntriesAsyncTask(entryDAO,date,username).execute(null).get()
    }

    fun selectAllEntriesOfSummary(summary: Summary) : List<Entry>{
        return SummaryEntriesAsyncTask(entryDAO,summary).execute(null).get()
    }

    class InsertEntryAsyncTask(val dao : EntryDAO) : AsyncTask<Entry,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Entry) :Boolean {
            dao.insert(parameters[0])
            return true;
        }
    }
    class UpdateEntryAsyncTask(val dao : EntryDAO) : AsyncTask<Entry,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Entry) :Boolean {
            dao.update(parameters[0])
            return true;
        }
    }
    class DeleteEntryAsyncTask(val dao : EntryDAO) : AsyncTask<Entry,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Entry) :Boolean {
            dao.delete(parameters[0])
            return true;
        }
    }
    class DateEntriesAsyncTask(val dao : EntryDAO,val date : String,val username : String) : AsyncTask<Void,Void,List<Entry>>(){
        override fun doInBackground(vararg parameters : Void?): List<Entry>? {
            return dao.selectAllEntriesOfDate(date,username)
        }
    }
    class SummaryEntriesAsyncTask(val dao : EntryDAO,val summary: Summary) : AsyncTask<Void,Void,List<Entry>>(){
        override fun doInBackground(vararg parameters : Void?): List<Entry>? {
            return dao.selectAllEntriesOfSummary(summary.username,summary.category,summary.type,summary.fromDate,summary.untilDate)
        }
    }

}