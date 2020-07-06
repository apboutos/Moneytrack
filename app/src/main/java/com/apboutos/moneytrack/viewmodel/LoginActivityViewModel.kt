package com.apboutos.moneytrack.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.repository.DatabaseRepository

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

        private val repository = DatabaseRepository(application)










}