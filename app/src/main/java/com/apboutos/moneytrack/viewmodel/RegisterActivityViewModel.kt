package com.apboutos.moneytrack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.database.entity.User


class RegisterActivityViewModel(application: Application) : AndroidViewModel(application) {




    fun registerNewUser(user: User) : RegisterError {

        return RegisterError.NO_ERROR
    }





    enum class RegisterError { NO_ERROR, USERNAME_TAKEN_ERROR, EMAIL_TAKEN_ERROR, NO_INTERNET_ERROR, SERVER_UNREACHABLE_ERROR }
}