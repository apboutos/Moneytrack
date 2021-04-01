package com.apboutos.moneytrack.model.repository.remote

import com.google.gson.annotations.Expose

/**
 * The JSON body of an authentication request.
 * {
 *      "username" : String ,
 *      "password" : String
 * }
 */
data class AuthenticationRequestData(@Expose var username : String,@Expose var password : String)
{
}