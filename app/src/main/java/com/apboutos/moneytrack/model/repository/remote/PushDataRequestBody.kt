package com.apboutos.moneytrack.model.repository.remote

import com.apboutos.moneytrack.model.database.entity.Entry
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PushDataRequestBody(@SerializedName("entryList")@Expose var list : List<Entry>) {
}