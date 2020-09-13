package com.apboutos.moneytrack.model.repository.remote


import com.apboutos.moneytrack.model.database.entity.Entry
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

    @Headers("Content-Type: application/json")
    @POST("retrieve.php")
    fun retrieveCredentials(@Body retrieveRequestBody: RetrieveRequestBody) : Call<RetrieveRequestResult>

    @Headers("Content-Type: application/json")
    @POST("pullData.php")
    fun pullData(@Body pullDataRequestBody : PullDataRequestBody) : Call<List<Entry>>

    @Headers("Content-Type: application/json")
    @POST("pushData.php")
    fun pushData(@Body pushDataRequestBody: List<Entry>) : Call<PushDataRequestResult>
}