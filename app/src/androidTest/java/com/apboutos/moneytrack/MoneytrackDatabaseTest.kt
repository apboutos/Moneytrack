package com.apboutos.moneytrack

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.room.Room
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import com.apboutos.moneytrack.model.database.dao.*
import com.apboutos.moneytrack.model.database.database.MoneytrackDatabase
import com.apboutos.moneytrack.model.database.entity.Entry
import com.apboutos.moneytrack.model.database.entity.User
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class MoneytrackDatabaseTest{

    private lateinit var db: MoneytrackDatabase
    private lateinit var entryDao : EntryDAO
    private lateinit var userDao  : UserDAO
    private lateinit var categoryDAO: CategoryDAO
    private lateinit var summaryDAO: SummaryDAO
    private lateinit var credentialDAO: CredentialDAO

    @Before
    fun createDb() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MoneytrackDatabase::class.java).build()
        userDao = db.UserDAO()
        entryDao = db.EntryDAO()
        categoryDAO = db.CategoryDAO()
        summaryDAO = db.SummaryDAO()
        credentialDAO = db.CredentialDAO()
    }

    @Test
    @Throws(Exception::class)
    fun insertUser() {
        val user = User("exophrenik","ma582468","exophrenik@gmail.com","2020-07-06","2020-07-6 23:43:23")
            userDao.insert(user)
        val byName : User = userDao.selectUserBy("exopdhrenik")
        Assert.assertEquals(null,byName)
    }

    @Test(expected = SQLiteConstraintException::class)
    @Throws(Exception::class)
    fun insertDuplicateUserAndRead() {
        val user = User("exophrenik","ma582468","exophrenik@gmail.com","2020-07-06","2020-07-6 23:43:23")
        userDao.insert(user)
        userDao.insert(user)
    }

    @Test
    @Throws(Exception::class)
    fun updateUser() {
        val user = User("exophrenik","ma582468","exophrenik@gmail.com","2020-07-06","2020-07-6 23:43:23")
        userDao.insert(user)
        val byName : User = userDao.selectUserBy("exophrenik")
        Assert.assertEquals(user,byName)
        val user2 = User("exophrenik","ma582470","exophrenik@gmail.com","2020-07-06","2020-07-6 23:43:23")
        userDao.update(user2)
        val byName2 = userDao.selectUserBy("exophrenik")
        Assert.assertNotEquals(byName,byName2)
    }

    @Test
    @Throws(Exception::class)
    fun deleteUser() {
        val user = User("exophrenik","ma582470","exophrenik@gmail.com","2020-07-06","2020-07-6 23:43:23")
        userDao.insert(user)
        val byName : User = userDao.selectUserBy("exophrenik")
        Assert.assertEquals(user,byName)
        userDao.delete(user)
        Assert.assertEquals(null,userDao.selectUserBy("exophrenik"))
    }

    @Test
    @Throws(Exception::class)
    fun selectAllEntriesOfDate(){

        val list1 : List<Entry> = listOf(
            Entry("ex2213","exophrenik","Expense","toEat","junk food",92.21, Date("2020-07-01"),Datetime("2020-06-01 23:41:22"),false),
            Entry("ex2214","exophrenik","Income","paycheck","paycheck",92.21,Date("2020-07-01"),Datetime("2020-06-01 23:41:22"),false),
            Entry("ex2215","exophrenik","Expense","toEat","junk food",92.21,Date("2020-06-02"),Datetime("2020-06-01 23:41:22"),false),
            Entry("ex2216","exophrenik","Expense","toEat","junk food",92.21,Date("2020-06-02"),Datetime("2020-06-01 23:41:22"),false),
            Entry("ex2217","exophrenik","Expense","toEat","junk food",92.21,Date("2020-06-02"), Datetime("2020-06-01 23:41:22"),false))
            for(i in list1){
                entryDao.insert(i)
            }
        val list2 = entryDao.selectAllEntriesOfDate(Date("2020-07-13"),"exophrenik")
        Assert.assertEquals(0,list2.size)
        /*
        Assert.assertNotEquals(list1,list2)
        val list3 = mutableListOf<Entry>()
        list3.addAll(list2)
        list3.addAll(entryDao.selectAllEntriesOfDate(Date("2020-06-02"),"exophrenik"))
        Assert.assertEquals(list1,list3)
        */
    }



    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

}