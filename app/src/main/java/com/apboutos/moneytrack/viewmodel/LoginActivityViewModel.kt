@file:Suppress("unused", "LiftReturnOrAssignment")

package com.apboutos.moneytrack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.apboutos.moneytrack.model.database.entity.Credential
import com.apboutos.moneytrack.model.database.entity.User
import com.apboutos.moneytrack.model.repository.local.DatabaseRepository
import com.apboutos.moneytrack.model.repository.remote.NetworkTester
import com.apboutos.moneytrack.model.repository.remote.OnlineRepository
import com.apboutos.moneytrack.utilities.error.LoginError
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseRepository = DatabaseRepository(application)
    private val onlineRepository = OnlineRepository(application)

    /**
     * Retrieves the stored Credential from the local database.
     * Returns null if no Credential is found.
     */
    fun retrieveStoredCredential(): Credential? {
        var credential: Credential? = null
        runBlocking {
            credential = databaseRepository.selectCredential()
        }
        return credential
    }

    /**
     *  Authenticates the specified username and password against the local repository.
     *  If the authentication fails the remote repository is used. If that fails as well
     *  and authentication error is produced.
     */
    fun requestAuthentication(username: String, password: String): LoginError {

        var user : User? = null
        runBlocking { user = databaseRepository.selectUserBy(username) }
        // user == null means that the user was not found in the local database
        // and so an online login attempt must be made.
        if (user == null) {
            if (!NetworkTester.internetConnectionIsAvailable(getApplication())) return LoginError.NO_INTERNET
            onlineRepository.authenticateUser(username, password)
            return LoginError.ATTEMPTING_ONLINE_LOGIN
        }
        else {
            return when {
                user!!.username != username -> {
                    LoginError.WRONG_USERNAME
                }
                user!!.password != password -> {
                    LoginError.WRONG_PASSWORD
                }
                else -> {
                    LoginError.NO_ERROR
                }
            }
        }
    }

    /**
     * Saves the user's Credential to the local repository.
     */
    fun saveUserCredential( username: String, password: String){
        viewModelScope.launch {
            databaseRepository.deleteCredential()
            databaseRepository.insertCredential(Credential(username,password))
        }
    }

    /**
     * Deletes the stored Credential from the local repository.
     */
    fun deleteStoredCredential(){
        viewModelScope.launch {
            databaseRepository.deleteCredential()
        }
    }

}
