package com.apboutos.moneytrack.model.repository.remote

import com.google.gson.annotations.Expose

/**
 * The JSON body of a push data response.
 * {
 *      "error" : String
 * }
 */
data class PushDataRequestResult(@Expose var error : String)