package com.apboutos.moneytrack.model.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apboutos.moneytrack.model.dao.CategoryDAO
import com.apboutos.moneytrack.model.dao.UserDAO
import com.apboutos.moneytrack.model.database.dao.EntryDAO
import com.apboutos.moneytrack.model.database.dao.SummaryDAO
import com.apboutos.moneytrack.model.entity.Category
import com.apboutos.moneytrack.model.entity.Entry
import com.apboutos.moneytrack.model.entity.User

@Database(entities = [Entry::class, User::class, Category::class],version = 1)
abstract class MoneytrackDatabase : RoomDatabase() {

    abstract fun EntryDAO() : EntryDAO
    abstract fun UserDAO() : UserDAO
    abstract fun CategoryDAO() : CategoryDAO
    abstract fun SummaryDAO() : SummaryDAO

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