package com.apboutos.moneytrack.model.repository.remote

import com.apboutos.moneytrack.model.database.entity.Entry
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * The JSON body of a push data request.
 * {
 *      "entryList" : JSONArray(Entry)
 * }
 */
data class PushDataRequestBody(@SerializedName("entryList")@Expose var list : List<Entry>) {
}