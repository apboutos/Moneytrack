package com.apboutos.moneytrack.model.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apboutos.moneytrack.model.database.converter.Converter
import com.apboutos.moneytrack.model.database.dao.*
import com.apboutos.moneytrack.model.database.entity.*


@Database(entities = [Entry::class, User::class, Category::class, Summary::class, Credential::class],version = 1)
@TypeConverters(Converter::class)
abstract class MoneytrackDatabase : RoomDatabase() {

    abstract fun EntryDAO() : EntryDAO
    abstract fun UserDAO() : UserDAO
    abstract fun CategoryDAO() : CategoryDAO
    abstract fun SummaryDAO() : SummaryDAO
    abstract fun CredentialDAO() : CredentialDAO

    companion object{

        @Volatile
        private var instance : MoneytrackDatabase? = null
        private var LOCK = Any()


        //TODO This code must be further investigated. Was copy pasted.
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) : MoneytrackDatabase {
            return Room.databaseBuilder(context, MoneytrackDatabase::class.java, "Moneytrack_Database.db").build()
        }
    }
}