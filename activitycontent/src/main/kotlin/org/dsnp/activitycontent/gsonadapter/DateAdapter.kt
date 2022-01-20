package org.dsnp.activitycontent.gsonadapter

import com.google.gson.*
import java.lang.reflect.Type
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Converts JSON into an [Date] and vice versa.
*/
open class DateAdapter : JsonDeserializer<Date>, JsonSerializer<Date> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Date {
        return Date.from(Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(json.asString)))
    }

    override fun serialize(src: Date, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.toInstant().toString())
    }
}