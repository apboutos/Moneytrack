package com.apboutos.moneytrack.model.repository

import com.apboutos.moneytrack.model.database.entity.User

class OnlineRepository {



    fun selectUserBy( username : String) : User?{
        return User("exophrenik","ma58268","exophrenik@gmail.com","2020-07-28","2020-07-28 12:52:21")
    }
}