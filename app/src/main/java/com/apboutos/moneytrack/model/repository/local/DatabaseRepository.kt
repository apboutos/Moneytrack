@file:Suppress("unused", "PrivatePropertyName")

package com.apboutos.moneytrack.model.repository.local

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.dao.*
import com.apboutos.moneytrack.model.database.database.MoneytrackDatabase
import com.apboutos.moneytrack.model.database.entity.*
import java.lang.Exception

class DatabaseRepository(application: Application) {

    private val database      = MoneytrackDatabase.invoke(application)
    private val entryDAO      = database.EntryDAO()
    private val userDAO       = database.UserDAO()
    private val categoryDAO   = database.CategoryDAO()
    private val summaryDAO    = database.SummaryDAO()
    private val credentialDAO = database.CredentialDAO()

    fun insert(entry : Entry) : Boolean{
        return InsertEntryAsyncTask(
            entryDAO
        ).execute(entry).get()
    }

    fun update(entry : Entry) : Boolean{
        return UpdateEntryAsyncTask(
            entryDAO
        ).execute(entry).get()
    }

    fun delete(entry : Entry) : Boolean{
        return DeleteEntryAsyncTask(
            entryDAO
        ).execute(entry).get()
    }

    fun selectAllEntryDateOfUser(username: String) : List<Date>{
        return AllEntryDatesOfUserAsyncTask(entryDAO,username).execute(null).get()
    }

    fun selectSumAmountOfLifetime(username: String) : Double{
        return SumOfLifetimeAsyncTask(entryDAO,username).execute(null).get()
    }

    fun selectSumAmountOfDateRange(username: String, from: Date, until: Date) : Double{
        return SumOfDateRangeAsyncTask(entryDAO,username,from,until).execute(null).get()
    }

    fun selectAllEntriesOfDate(date : String, username : String) : List<Entry>{
        return DateEntriesAsyncTask(
            entryDAO,
            Date(date),
            username
        ).execute(null).get()
    }

    fun selectAllEntriesOfSummary(summary: Summary) : List<Entry>{
        return SummaryEntriesAsyncTask(
            entryDAO,
            summary
        ).execute(null).get()
    }

    fun insert(user : User){
        InsertUserAsyncTask(
            userDAO
        ).execute(user)
    }

    fun update(user : User){
        UpdateUserAsyncTask(
            userDAO
        ).execute(user)
    }

    fun delete(user : User){
        DeleteUserAsyncTask(
            userDAO
        ).execute(user)
    }

    fun selectUserBy(username : String) : User? {
        return SelectUserAsyncTask(
            userDAO,
            username
        ).execute(null).get()
    }

    fun insert(category: Category){
        InsertCategoryAsyncTask(
            categoryDAO
        ).execute(category)
    }

    fun update(category: Category){
        UpdateCategoryAsyncTask(
            categoryDAO
        ).execute(category)
    }

    fun delete(category: Category){
        DeleteCategoryAsyncTask(
            categoryDAO
        ).execute(category)
    }

    fun selectAllCategories() : List<Category>{
        return SelectAllCategoriesAsyncTask(categoryDAO).execute().get()
    }

    fun insert(summary: Summary){
        InsertSummaryAsyncTask(
            summaryDAO
        ).execute(summary)
    }

    fun deleteAllSummaries(){
        DeleteAllSummaryAsyncTask(
            summaryDAO
        ).execute(null)
    }

    fun selectAllSummaries() : List<Summary>{
        return SelectAllSummaryAsyncTask(
            summaryDAO
        ).execute(null).get()
    }

    fun insertCredential(credential: Credential){
        InsertCredentialAsyncTask(
            credentialDAO
        ).execute(credential)
    }

    fun deleteCredential(){
        DeleteCredentialAsyncTask(
            credentialDAO
        ).execute(null)
    }

    fun selectCredential() : Credential?{
        return SelectCredentialAsyncTask(
            credentialDAO
        ).execute(null).get()
    }

    private class AllEntryDatesOfUserAsyncTask(val dao: EntryDAO, val username: String) : AsyncTask<Void,Void,List<Date>>(){
        override fun doInBackground(vararg p0: Void?): List<Date> {
            return try{
                dao.selectAllEntryDatesOfUser(username)
            }catch (e : Exception){
                Log.e("DatabaseRepository",e.message ?: "")
                listOf()
            }
        }
    }

    private class SumOfDateRangeAsyncTask(val dao : EntryDAO,val username: String, val from: Date, val until: Date) : AsyncTask<Void,Void,Double>(){
        override fun doInBackground(vararg p0: Void): Double {
            return try{
                dao.selectEntrySumOfDateRange(username,from,until)
            }catch (e : Exception){
                Log.e("DatabaseRepository",e.message ?: "")
                0.00
            }
        }

    }

    private class SumOfLifetimeAsyncTask(val dao : EntryDAO, val username: String) : AsyncTask<Void,Void,Double>(){
        override fun doInBackground(vararg parameters : Void) : Double {
            return try{
                dao.selectEntrySumOfLifetime(username)
            } catch (e : Exception){
                Log.e("DatabaseRepository",e.message ?: "")
                0.00
            }
        }
    }

    private class InsertEntryAsyncTask(val dao : EntryDAO) :AsyncTask<Entry,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Entry) :Boolean {
            try{ dao.insert(parameters[0]) }
            catch (e : Exception){
                Log.e("DatabaseRepository",e.message ?: "")
                return false
            }
            return true
        }
    }
    private class UpdateEntryAsyncTask(val dao : EntryDAO) : AsyncTask<Entry,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Entry) :Boolean {
            try{ dao.update(parameters[0]) }
            catch (e : Exception){
                Log.e("DatabaseRepository",e.message ?: "")
                return false
            }
            return true
        }
    }
    private class DeleteEntryAsyncTask(val dao : EntryDAO) : AsyncTask<Entry,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Entry) : Boolean {
            try{ dao.delete(parameters[0]) }
            catch (e : Exception){
                Log.e("DatabaseRepository",e.message ?: "")
                return false
            }
            return true
        }
    }
    private class DateEntriesAsyncTask(val dao : EntryDAO, val date : Date, val username : String) : AsyncTask<Void,Void,List<Entry>>(){
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
            try{ dao.insert(parameters[0]) }
            catch (e : Exception){
                Log.e("DatabaseRepository",e.message ?: "")
                return false
            }
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

    private class SelectUserAsyncTask(val dao : UserDAO, val username: String) : AsyncTask<Void,Void,User?>(){
        override fun doInBackground(vararg parameters : Void) : User? {
            return dao.selectUserBy(username)
        }
    }

    private class InsertCategoryAsyncTask(val dao : CategoryDAO) : AsyncTask<Category,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Category) :Boolean {
            try{ dao.insert(parameters[0]) }
            catch (e : Exception){
                Log.e("DatabaseRepository",e.message ?: "")
                return false
            }
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

    private class SelectAllCategoriesAsyncTask(val dao : CategoryDAO) : AsyncTask<Void,Void,List<Category>>(){
        override fun doInBackground(vararg p0: Void?): List<Category> {
            return dao.selectAllCategories()
        }

    }

    private class InsertSummaryAsyncTask(val dao : SummaryDAO) : AsyncTask<Summary,Void,Boolean>(){
        override fun doInBackground(vararg parameters : Summary) :Boolean {
            try{ dao.insert(parameters[0]) }
            catch (e : Exception){
                Log.e("DatabaseRepository",e.message ?: "")
                return false
            }
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
            try{ dao.insert(parameters[0]) }
            catch (e : Exception){
                Log.e("DatabaseRepository",e.message ?: "")
                return false
            }
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