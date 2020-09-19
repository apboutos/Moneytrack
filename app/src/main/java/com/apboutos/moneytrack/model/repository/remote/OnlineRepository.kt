@file:Suppress("unused", "PrivatePropertyName")

package com.apboutos.moneytrack.model.repository.remote

import android.app.Application
import android.content.Intent
import android.util.Log
import com.apboutos.moneytrack.model.database.entity.Entry
import com.apboutos.moneytrack.model.database.entity.User
import com.apboutos.moneytrack.viewmodel.receiver.LedgerReceiver
import com.apboutos.moneytrack.viewmodel.receiver.LoginReceiver
import com.apboutos.moneytrack.viewmodel.receiver.RegisterReceiver
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class OnlineRepository(var application: Application) {

    private val TAG = "OnlineRepository"
    private val retro : Retrofit = Retrofit.Builder().baseUrl("http://exophrenik.com/moneytrack/")
                                                     .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().excludeFieldsWithoutExposeAnnotation().create()))
                                                     .build()
    private val api : RemoteServerAPI = retro.create(RemoteServerAPI::class.java)

    private lateinit var loginResult: AuthenticationRequestResult


    fun authenticateUser(username : String , password : String) {

                api.authenticateUser(AuthenticationRequestData(username, password)).enqueue(object : Callback<AuthenticationRequestResult> {
                    override fun onFailure(call: Call<AuthenticationRequestResult>, t: Throwable) {
                        Log.e(TAG,t.message,t)
                        val intent = Intent()
                        intent.putExtra("error","SERVER_UNREACHABLE")
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        intent.action = LoginReceiver.SERVER_LOGIN_RESPONSE
                        application.sendBroadcast(intent)
                    }

                    override fun onResponse(call: Call<AuthenticationRequestResult>, response: Response<AuthenticationRequestResult>) {

                        Log.d(TAG,"Response code: " + response.code())
                        Log.d(TAG,"Response body: " + response.body())
                        val intent = Intent()
                        if(response.body() == null){
                            intent.putExtra("error","SERVER_UNREACHABLE")
                        }
                        else{
                            intent.putExtra("error",response.body()!!.error)
                        }
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        intent.action = LoginReceiver.SERVER_LOGIN_RESPONSE
                        application.sendBroadcast(intent)
                    }
                })
    }

    fun pushData(entryList : List<Entry>){
        for (i in entryList){
            Log.d(TAG,"List Item : ${i.description} ${i.date} ${i.lastUpdate} ${i.isDeleted}")
        }
        api.pushData(PushDataRequestBody(entryList)).enqueue(object : Callback<PushDataRequestResult> {
            override fun onFailure(call: Call<PushDataRequestResult>, t: Throwable) {
                Log.e(TAG, t.message, t)
                val intent = Intent()
                intent.putExtra("error", "SERVER_UNREACHABLE")
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.action = LedgerReceiver.SERVER_PUSH_DATA_RESPONSE
                application.sendBroadcast(intent)
            }

            override fun onResponse(call: Call<PushDataRequestResult>, response: Response<PushDataRequestResult>
            ) {
                Log.d(TAG, "PushData Response code: " + response.code())
                Log.d(TAG, "PushData Response body: " + response.body())
                val intent = Intent()
                if(response.body() == null){
                    intent.putExtra("error","SERVER_UNREACHABLE")
                }
                else{
                    intent.putExtra("error",response.body()!!.error)
                }
                Log.d(TAG,"${response.body()}")
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.action = LedgerReceiver.SERVER_PUSH_DATA_RESPONSE
                application.sendBroadcast(intent)
            }
        })
    }

    fun pullData(username: String,lastPullRequestDatetime : String){
        api.pullData(PullDataRequestBody(username,lastPullRequestDatetime)).enqueue(object : Callback<List<Entry>> {
            override fun onFailure(call: Call<List<Entry>>, t: Throwable) {
                Log.e(TAG, t.message, t)
                val intent = Intent()
                intent.putExtra("error", "SERVER_UNREACHABLE")
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.action = LedgerReceiver.SERVER_PULL_DATA_RESPONSE
                application.sendBroadcast(intent)
            }

            override fun onResponse(call: Call<List<Entry>>, response: Response<List<Entry>>) {
                Log.d(TAG, "PullData Response code: " + response.code())
                Log.d(TAG, "PullData Response body: " + response.body())
                Log.d(TAG,"PullData Request data: $username $lastPullRequestDatetime")

                val tmpArrayList : ArrayList<Entry> = ArrayList()
                tmpArrayList.addAll(response.body() ?: listOf())

                for (i in tmpArrayList){
                    Log.d(TAG,"${i.description}  ${i.date}  ${i.lastUpdate} ${i.isDeleted}")
                }

                val intent = Intent()
                intent.putParcelableArrayListExtra("entryList",tmpArrayList)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.action = LedgerReceiver.SERVER_PULL_DATA_RESPONSE
                application.sendBroadcast(intent)

            }
        })
    }

    fun registerUser( user : User) {
        if(!NetworkTester.internetConnectionIsAvailable(application)){
            val intent = Intent()
            intent.putExtra("error","SERVER_UNREACHABLE")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.action = RegisterReceiver.SERVER_REGISTER_RESPONSE
            application.sendBroadcast(intent)
            return
        }

        api.registerUser(user).enqueue(object : Callback<RegistrationResult>{
            override fun onFailure(call: Call<RegistrationResult>, t: Throwable) {
                Log.e(TAG,t.message,t)
                val intent = Intent()
                intent.putExtra("error","SERVER_UNREACHABLE")
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.action = RegisterReceiver.SERVER_REGISTER_RESPONSE
                application.sendBroadcast(intent)
            }

            override fun onResponse(call: Call<RegistrationResult>, response: Response<RegistrationResult>
            ) {
                Log.d(TAG,"Response code: " + response.code())
                Log.d(TAG,"Response body: " + response.body())
                val intent = Intent()
                if(response.body() == null){
                    intent.putExtra("error","SERVER_UNREACHABLE")
                }
                else{
                    intent.putExtra("error",response.body()!!.error)
                }
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.action = RegisterReceiver.SERVER_REGISTER_RESPONSE
                application.sendBroadcast(intent)
            }
        })
    }

    fun retrieveLostCredentials(email : String){

        api.retrieveCredentials(RetrieveRequestBody(email)).enqueue(object : Callback<RetrieveRequestResult>{
            override fun onFailure(call: Call<RetrieveRequestResult>, t: Throwable) {
                Log.e(TAG,t.message,t)
            }

            override fun onResponse(call: Call<RetrieveRequestResult>, response: Response<RetrieveRequestResult>) {
                Log.d(TAG,"Response code: " + response.code())
                Log.d(TAG,"Response body: " + response.body())
            }

        })
    }
}


