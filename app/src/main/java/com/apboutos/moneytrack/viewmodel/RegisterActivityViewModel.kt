@file:Suppress("unused")

package com.apboutos.moneytrack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.database.entity.User
import com.apboutos.moneytrack.model.repository.local.DatabaseRepository
import com.apboutos.moneytrack.model.repository.remote.OnlineRepository
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseRepository = DatabaseRepository(application)
    private val onlineRepository = OnlineRepository(application)

    fun registerNewUser(username : String, password : String, email : String) {

        val user = User(username, password,email, SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()), SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date()))
        onlineRepository.registerUser(user)

    }

    fun addUserToDatabase(username: String,password: String,email: String){
        databaseRepository.insert(User(username,password,email,SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()),SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())))
    }
}