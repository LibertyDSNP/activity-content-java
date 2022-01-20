package org.dsnp.activitycontent.gsonadapter

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.dsnp.activitycontent.extension.with
import org.dsnp.activitycontent.model.ActivityContentLocation
import org.dsnp.activitycontent.model.ConcreteActivityContentLocation
import java.lang.reflect.Type

/**
 * Converts JSON into an [ActivityContentLocation].
 * IMPORTANT: If you want to use additional fields, you need to enable support for that by passing in true for the parameter [supportAdditionalFields].
 * An additional field is a JSON field that is not part of the default DSNP Activity Content definition (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview)).
 * By default, additional field support is turned OFF in order to reduce memory usage.
 *
 *
 * @property supportAdditionalFields True if you want to support additional fields, false, otherwise.
 * @property gsonFactory A wrapper for a [Gson] instance that will be used to parse JSON.
 */
open class LocationDeserializer(
    private val supportAdditionalFields: Boolean,
    private val gsonFactory: () -> Gson,
) : JsonDeserializer<ActivityContentLocation> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ActivityContentLocation {
        return gsonFactory()
            .fromJson(json, ConcreteActivityContentLocation::class.java)
            .apply {
                if (supportAdditionalFields) {
                    with(json)
                }
            }
    }
}