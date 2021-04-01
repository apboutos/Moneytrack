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
    fun selectNonExistingEntry(){
        Assert.assertNull(entryDao.selectEntry("yolo"))
    }

    @Test
    fun selectModifiedEntries(){
        Assert.assertNotNull(entryDao.selectModifiedEntries("FUBAR", Datetime("2021-01-01 00:00:00")))
        Assert.assertEquals(entryDao.selectModifiedEntries("FUBAR", Datetime("2021-01-01 00:00:00")).size, 0)
    }

    @Test
    fun selectUserBy(){
        Assert.assertNull(userDao.selectUserBy("FUBAR"))
    }

    @Test
    fun selectAllEntryDatesOfUser(){

        entryDao.insert(Entry("ex2213","exophrenik","Expense","toEat","junk food",92.21, Date("2020-07-01"),Datetime("2020-06-01 23:41:22"),false))
        entryDao.insert(Entry("ex2214","exophrenik","Income","paycheck","paycheck",92.21,Date("2021-07-01"),Datetime("2020-06-01 23:41:22"),false))
        entryDao.insert(Entry("ex2215","exophrenik","Expense","toEat","junk food",92.21,Date("2022-07-01"),Datetime("2020-06-01 23:41:22"),false))
        entryDao.insert(Entry("ex2216","exophrenik","Expense","toEat","junk food",92.21,Date("2021-07-01"),Datetime("2020-06-01 23:41:22"),false))
        entryDao.insert(Entry("ex2217","exophrenik","Expense","toEat","junk food",92.21,Date("2020-07-01"), Datetime("2020-06-01 23:41:22"),false))

        val list = entryDao.selectAllEntryDatesOfUser("exophrenik")
        Assert.assertEquals(listOf(Date("2020-07-01"),Date("2021-07-01"),Date("2022-07-01"),Date("2021-07-01"),Date("2020-07-01")),list)

        var set = setOf<String>()
        for (i in list){
            //set.addAll(listOf(i.year))
            set = set.plus(i.year)
        }
        Assert.assertEquals(mutableSetOf("2020","2021","2022"),set)
        Assert.assertEquals(arrayOf("2020","2021","2022"),set.toTypedArray())
    }

    @Test
    fun insertUser() {
        val user = User("exophrenik","ma582468","exophrenik@gmail.com","2020-07-06","2020-07-6 23:43:23")
            userDao.insert(user)
        Assert.assertNotNull(userDao.selectUserBy("exophrenik"))
        Assert.assertNull(userDao.selectUserBy("FUBAR"))
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
        val user2 = User("exophrenik","ma582470","exophrenik@gmail.com","2020-07-06","2020-07-6 23:43:23")
        userDao.update(user2)
        val byName2 = userDao.selectUserBy("exophrenik")
        Assert.assertNotEquals(user,byName2)
    }

    @Test
    fun deleteUser() {
        val user = User("exophrenik","ma582470","exophrenik@gmail.com","2020-07-06","2020-07-6 23:43:23")
        userDao.insert(user)
        userDao.delete(user)
        Assert.assertNull(userDao.selectUserBy("exophrenik"))
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

    private fun insertMockDataToDatabase(){
        val list = createMockData("2020-07-13","2020-07-13 13:33:42")
        for(i in list){
           // databaseRepository.insert(i)
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