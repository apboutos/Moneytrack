package com.apboutos.moneytrack.model.repository.remote

import com.google.gson.annotations.Expose

/**
 * The JSON body of a pull data request.
 * {
 *      "username" : String ,
 *      "lastPullRequestDatetime : String
 * }
 */
data class PullDataRequestBody (@Expose var username : String, @Expose var lastPullRequestDatetime : String)