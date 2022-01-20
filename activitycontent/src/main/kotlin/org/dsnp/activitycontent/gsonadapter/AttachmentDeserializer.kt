package org.dsnp.activitycontent.gsonadapter

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.extension.with
import org.dsnp.activitycontent.model.*
import java.lang.reflect.Type

private const val TYPE = "type"

/**
 * Converts JSON into an [ActivityContentAttachment].
 * The output will be one of [ConcreteActivityContentAudio], [ConcreteActivityContentImage], [ConcreteActivityContentVideo], or [ActivityContentLink].
 * IMPORTANT: If you want to use additional fields, you need to enable support for that by passing in true for the parameter [supportAdditionalFields].
 * An additional field is a JSON field that is not part of the default DSNP Activity Content definition (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview)).
 * By default, additional field support is turned OFF in order to reduce memory usage.
 *
 *
 * @property supportAdditionalFields True if you want to support additional fields, false, otherwise.
 * @property gsonFactory A wrapper for a [Gson] instance that will be used to parse JSON.
 */
open class AttachmentDeserializer(
    private val supportAdditionalFields: Boolean,
    private val gsonFactory: () -> Gson,
) : JsonDeserializer<ActivityContentAttachment> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ActivityContentAttachment {
        val jsonObject = json.asJsonObject
        val klass = when (val type = jsonObject.get(TYPE).asString!!) {
            TYPE_AUDIO -> ConcreteActivityContentAudio::class.java
            TYPE_IMAGE -> ConcreteActivityContentImage::class.java
            TYPE_VIDEO -> ConcreteActivityContentVideo::class.java
            TYPE_LINK -> ActivityContentLink::class.java
            else -> throw ValidationException("Invalid type for attachment: $type")
        }
        return gsonFactory().fromJson(json, klass).apply {
            if (supportAdditionalFields) {
                (this as ConcreteAdditionalFieldAccessor).with(json)
            }
        }
    }
}