package com.apboutos.moneytrack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apboutos.moneytrack.model.repository.remote.OnlineRepository

class RetrieveActivityViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Sends a request to the server to email the user his credentials.
     */
    fun retrieveLostCredentials(email: String){
        OnlineRepository(getApplication()).retrieveLostCredentials(email)
    }
}