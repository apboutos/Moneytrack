@file:Suppress("unused", "LiftReturnOrAssignment")

package com.apboutos.moneytrack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.database.entity.Credential
import com.apboutos.moneytrack.model.repository.local.DatabaseRepository
import com.apboutos.moneytrack.model.repository.remote.NetworkTester
import com.apboutos.moneytrack.model.repository.remote.OnlineRepository
import com.apboutos.moneytrack.utilities.error.LoginError

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

        private val databaseRepository = DatabaseRepository(application)
        private val onlineRepository = OnlineRepository(application)

        fun retrieveStoredCredential() : Credential? = databaseRepository.selectCredential()

        fun requestAuthentication( username : String, password : String) : LoginError {

            val user = databaseRepository.selectUserBy(username)
            // user == null means that the user was not found in the local database
            // and so an online login attempt must be made.
            if(user == null){
                if(!NetworkTester.internetConnectionIsAvailable(getApplication())) return LoginError.NO_INTERNET
                onlineRepository.authenticateUser(username,password)
                return LoginError.ATTEMPTING_ONLINE_LOGIN
            }else{
                return when {
                    user.username != username -> {LoginError.WRONG_USERNAME}
                    user.password != password -> {LoginError.WRONG_PASSWORD}
                    else -> {LoginError.NO_ERROR}
                }
            }
        }

        fun saveUserCredential( username: String, password: String){
                databaseRepository.deleteCredential()
                databaseRepository.insertCredential(Credential(username,password))
        }

        fun deleteStoredCredential(){
                databaseRepository.deleteCredential()
        }
}
