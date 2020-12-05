package com.apboutos.moneytrack.model.database.converter

import android.util.Log
import com.google.gson.*
import java.lang.reflect.Type

class DatetimeDeserializer() : JsonDeserializer<Datetime> {

    val tag = "DatetimeDeserializer"

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Datetime {
        if(json == null) throw JsonParseException("Datetime in json is null")
        Log.d(tag,"json was deserialized as ${json.asJsonObject.get("datetime").asString}")

        return Datetime(json.asJsonObject.get("datetime").asString)
    }
}