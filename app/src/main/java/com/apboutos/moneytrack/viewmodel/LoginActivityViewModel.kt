@file:Suppress("unused")

package com.apboutos.moneytrack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.database.entity.Credential
import com.apboutos.moneytrack.model.repository.local.DatabaseRepository
import com.apboutos.moneytrack.model.repository.remote.OnlineRepository
import com.apboutos.moneytrack.utilities.error.LoginError

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

        private val databaseRepository = DatabaseRepository(application)
        private val onlineRepository = OnlineRepository(application)

        fun retrieveStoredCredential() : Credential? = databaseRepository.selectCredential()

        fun requestAuthentication( username : String, password : String) : LoginError {
                val credential : Credential? = databaseRepository.selectCredential()
                when {

                    credential == null -> { return onlineRepository.authenticateUser(username,password) }

                    credential.username != username -> { return LoginError.WRONG_USERNAME }

                    credential.password != password -> { return LoginError.WRONG_PASSWORD }

                    else -> { return LoginError.NO_ERROR }
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
