package com.apboutos.moneytrack.model.repository.remote

import com.google.gson.annotations.Expose

/**
 * The JSON Body of a retrieve request.
 * {
 *      "email" : String
 * }
 */
data class RetrieveRequestBody(@Expose var email : String)
