@file:Suppress("unused")

package com.apboutos.moneytrack.model.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import com.apboutos.moneytrack.model.database.dao.*
import com.apboutos.moneytrack.model.database.entity.*

@Database(entities = [Entry::class, User::class, Category::class, Summary::class, Credential::class],version = 3)
@TypeConverters(Date::class, Datetime::class)
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
        private const val TAG = "MoneytrackDatabase"

        /**
         * Returns the current instance of the MoneytrackDatabase and if it is null creates a new instance in a thread safe manner.
         */
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        /**
         * Instantiates a concrete implementation of MoneytrackDatabase. This implementation
         * contains a migration strategy that is required to migrate to new future versions of
         * the database.
         */
        private fun buildDatabase(context: Context) : MoneytrackDatabase {
            return Room.databaseBuilder(context, MoneytrackDatabase::class.java, "Moneytrack_Database.db")
                .addMigrations(MIGRATION_2_3).build()
        }

        /**
         * Migration Strategy from version 2 to version 3.
         * A migration strategy is required so the data are not lost when moving to a new database schema.
         */
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

    }


}