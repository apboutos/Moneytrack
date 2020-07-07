package com.apboutos.moneytrack.model.repository

import android.app.Application
import android.os.AsyncTask
import com.apboutos.moneytrack.model.database.dao.*
import com.apboutos.moneytrack.model.database.database.MoneytrackDatabase
import com.apboutos.moneytrack.model.database.entity.*

class DatabaseRepository(application: Application) {

    private val database      = MoneytrackDatabase.invoke(application)
    private val entryDAO      = database.EntryDAO()
    private val userDAO       = database.UserDAO()
    private val categoryDAO   = database.CategoryDAO()
    private val summaryDAO    = database.SummaryDAO()
    private val credentialDAO = database.CredentialDAO()

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

    fun insert(user : User){
        InsertUserAsyncTask(userDAO).execute(user)
    }

    fun update(user : User){
        UpdateUserAsyncTask(userDAO).execute(user)
    }

    fun delete(user : User){
        DeleteUserAsyncTask(userDAO).execute(user)
    }

    fun selectUserBy(username : String) : User {
        return SelectUserAsyncTask(userDAO,username).execute(null).get()
    }

    fun insert(category: Category){
        InsertCategoryAsyncTask(categoryDAO).execute(category)
    }

    fun update(category: Category){
        UpdateCategoryAsyncTask(categoryDAO).execute(category)
    }

    fun delete(category: Category){
        DeleteCategoryAsyncTask(categoryDAO).execute(category)
    }

    fun insert(summary: Summary){
        InsertSummaryAsyncTask(summaryDAO).execute(summary)
    }

    fun deleteAllSummaries(){
        DeleteAllSummaryAsyncTask(summaryDAO).execute(null)
    }

    fun selectAllSummaries() : List<Summary>{
        return SelectAllSummaryAsyncTask(summaryDAO).execute(null).get()
    }

    fun insertCredential(credential: Credential){
        InsertCredentialAsyncTask(credentialDAO).execute(credential)
    }

    fun deleteCredential(){
        DeleteCredentialAsyncTask(credentialDAO).execute(null)
    }

    fun selectCredential() : Credential?{
        return SelectCredentialAsyncTask(credentialDAO).execute(null).get()
    }

    private class InsertEntryAsyncTask(val dao : EntryDAO) : AsyncTask<Entry,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Entry) :Boolean {
            dao.insert(parameters[0])
            return true
        }
    }
    private class UpdateEntryAsyncTask(val dao : EntryDAO) : AsyncTask<Entry,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Entry) :Boolean {
            dao.update(parameters[0])
            return true
        }
    }
    private class DeleteEntryAsyncTask(val dao : EntryDAO) : AsyncTask<Entry,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Entry) :Boolean {
            dao.delete(parameters[0])
            return true
        }
    }
    private class DateEntriesAsyncTask(val dao : EntryDAO,val date : String,val username : String) : AsyncTask<Void,Void,List<Entry>>(){
        override fun doInBackground(vararg parameters : Void?): List<Entry>? {
            return dao.selectAllEntriesOfDate(date,username)
        }
    }
    private class SummaryEntriesAsyncTask(val dao : EntryDAO,val summary: Summary) : AsyncTask<Void,Void,List<Entry>>(){
        override fun doInBackground(vararg parameters : Void?): List<Entry>? {
            return dao.selectAllEntriesOfSummary(summary.username,summary.category,summary.type,summary.fromDate,summary.untilDate)
        }
    }

    private class InsertUserAsyncTask(val dao : UserDAO) : AsyncTask<User,Void,Boolean>(){
        override fun doInBackground(vararg parameters : User) :Boolean {
            dao.insert(parameters[0])
            return true
        }
    }

    private class UpdateUserAsyncTask(val dao : UserDAO) : AsyncTask<User,Void,Boolean>(){
        override fun doInBackground(vararg parameters : User) :Boolean {
            dao.update(parameters[0])
            return true
        }
    }

    private class DeleteUserAsyncTask(val dao : UserDAO) : AsyncTask<User,Void,Boolean>(){
        override fun doInBackground(vararg parameters : User) :Boolean {
            dao.delete(parameters[0])
            return true
        }
    }

    private class SelectUserAsyncTask(val dao : UserDAO, val username: String) : AsyncTask<Void,Void,User>(){
        override fun doInBackground(vararg parameters : Void) : User {
            return dao.selectUserBy(username)
        }
    }

    private class InsertCategoryAsyncTask(val dao : CategoryDAO) : AsyncTask<Category,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Category) :Boolean {
            dao.insert(parameters[0])
            return true
        }
    }
    private class UpdateCategoryAsyncTask(val dao : CategoryDAO) : AsyncTask<Category,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Category) :Boolean {
            dao.update(parameters[0])
            return true
        }
    }
    private class DeleteCategoryAsyncTask(val dao : CategoryDAO) : AsyncTask<Category,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Category) :Boolean {
            dao.delete(parameters[0])
            return true
        }
    }

    private class InsertSummaryAsyncTask(val dao : SummaryDAO) : AsyncTask<Summary,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Summary) :Boolean {
            dao.insert(parameters[0])
            return true
        }
    }

    private class DeleteAllSummaryAsyncTask(val dao : SummaryDAO) : AsyncTask<Void,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Void) :Boolean {
            dao.deleteAllSummaries()
            return true
        }
    }

    private class SelectAllSummaryAsyncTask(val dao : SummaryDAO) : AsyncTask<Void,Void,List<Summary>>(){
        override fun doInBackground(vararg parameters : Void) : List<Summary> {
            return dao.selectAllSummaries()
        }
    }

    private class InsertCredentialAsyncTask(val dao : CredentialDAO) : AsyncTask<Credential,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Credential) :Boolean {
            dao.insert(parameters[0])
            return true
        }
    }

    private class DeleteCredentialAsyncTask(val dao : CredentialDAO) : AsyncTask<Void,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Void) :Boolean {
            dao.delete()
            return true
        }
    }

    private class SelectCredentialAsyncTask(val dao : CredentialDAO) : AsyncTask<Void,Void,Credential?>(){
        override fun doInBackground(vararg parameters : Void) : Credential? {
            return dao.select()
        }
    }
}