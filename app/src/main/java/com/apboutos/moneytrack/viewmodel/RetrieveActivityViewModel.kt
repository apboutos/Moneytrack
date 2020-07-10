package com.apboutos.moneytrack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.repository.remote.OnlineRepository

class RetrieveActivityViewModel(application: Application) : AndroidViewModel(application) {

    fun retrieveLostCredentials(email: String){
        OnlineRepository(getApplication()).retrieveLostCredentials(email)
    }
}