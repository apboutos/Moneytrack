package com.apboutos.moneytrack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.database.entity.User
import com.apboutos.moneytrack.model.repository.DatabaseRepository
import com.apboutos.moneytrack.model.repository.OnlineRepository


class RegisterActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseRepository = DatabaseRepository(application)
    private val onlineRepository = OnlineRepository()


    fun registerNewUser(user: User) : RegisterError {

        return RegisterError.NO_ERROR
    }





    enum class RegisterError { NO_ERROR, USERNAME_TAKEN, EMAIL_TAKEN, NO_INTERNET, SERVER_UNREACHABLE }
}