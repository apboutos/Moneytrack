package com.apboutos.moneytrack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.database.entity.Credential
import com.apboutos.moneytrack.model.repository.DatabaseRepository

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

        private val repository = DatabaseRepository(application)










        fun retrieveStoredCredential() : Credential?{

                return Credential("exophrenik","ma582468") //TODO Mock data
        }

        fun requestAuthentication( username : String, password : String) : LoginError {

                return LoginError.NO_ERROR
        }

        fun saveUserCredential( username: String, password: String){

        }

        fun deleteStoredCredential(){

        }

        enum class LoginError { NO_ERROR,WRONG_PASSWORD,WRONG_USERNAME }
}