package com.apboutos.moneytrack.model.repository.remote

import com.google.gson.annotations.Expose

/**
 * The JSON body of a registration request response.
 * {
 *      "error" : String
 * }
 */
data class RegistrationResult(@Expose var error : String) {
}