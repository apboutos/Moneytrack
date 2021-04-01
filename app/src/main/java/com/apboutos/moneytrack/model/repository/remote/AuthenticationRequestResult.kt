package com.apboutos.moneytrack.model.repository.remote

import com.google.gson.annotations.Expose

/**
 * The JSON body of an authentication request response.
 * {
 *      "error" : String
 * }
 */
data class AuthenticationRequestResult (@Expose var error : String){
}