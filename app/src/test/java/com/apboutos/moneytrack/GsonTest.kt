package com.apboutos.moneytrack

import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.converter.Datetime
import com.apboutos.moneytrack.model.database.entity.Entry
import com.google.gson.GsonBuilder
import org.junit.Test

class GsonTest {

    @Test
    fun testGSONConverter(){


        val builder = GsonBuilder().setLenient().excludeFieldsWithoutExposeAnnotation().create()

        val list = arrayListOf<Entry>()
        list.add(Entry("TestId","exophrenik","Income","Test","paycheck",12.23,
            Date("2020-09-13"), Datetime("2020-09-13 00:00:00"),false))
        list.add(Entry("TestId","exophrenik","Income","Test","paycheck",12.23,
            Date("2020-09-13"), Datetime("2020-09-13 00:00:00"),false))
        builder.toJson(list)
        print(builder.toJson(list))
    }
}