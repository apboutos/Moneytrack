@file:Suppress("unused")

package com.apboutos.moneytrack.model.repository

import android.app.Application
import com.apboutos.moneytrack.model.database.entity.User
import com.apboutos.moneytrack.utilities.error.LoginError
import com.apboutos.moneytrack.utilities.error.RegisterError
import java.util.*

class OnlineRepository(application: Application) {

    fun authenticateUser(username : String , password : String) : LoginError {
        return LoginError.NO_ERROR
    }

    fun registerUser( user : User) : RegisterError {
        return RegisterError.NO_ERROR
    }

}

