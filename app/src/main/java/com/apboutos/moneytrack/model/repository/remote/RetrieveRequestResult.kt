package com.apboutos.moneytrack.model.repository.remote

import com.google.gson.annotations.Expose

/**
 * The JSON Body of a retrieve request response.
 * {
 *      "result" : String
 * }
 */
data class RetrieveRequestResult (@Expose var result : String)