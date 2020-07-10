package com.apboutos.moneytrack.model.repository.remote


import com.apboutos.moneytrack.model.database.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RemoteServerAPI {

    @Headers("Content-Type: application/json")
    @POST("authenticationRequest.php")
    fun authenticateUser(@Body authentication: AuthenticationRequestData) : Call<AuthenticationRequestResult>

    @Headers("Content-Type: application/json")
    @POST("register.php")
    fun registerUser(@Body user : User) : Call<RegistrationResult>
}