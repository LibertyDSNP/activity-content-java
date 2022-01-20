package org.dsnp.activitycontent.gsonadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.dsnp.activitycontent.ActivityContent

/**
 * Converts additional fields in form of a Map<String, Any> into JSON.
*/
class AdditionalFieldAdapter : TypeAdapter<Map<String, Any>>() {
    override fun write(out: JsonWriter, value: Map<String, Any>?) {
        out.nullValue()
        val gson = ActivityContent.outputGson
        value?.forEach { (fieldName, fieldValue) ->
            out.name(fieldName)
            out.jsonValue(gson.toJson(fieldValue))
        }
    }

    override fun read(`in`: JsonReader?): Map<String, Any>? {
        return null
    }
}