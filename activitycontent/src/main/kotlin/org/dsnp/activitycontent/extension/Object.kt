package org.dsnp.activitycontent.extension

import com.google.gson.JsonElement
import org.dsnp.activitycontent.model.ConcreteAdditionalFieldAccessor

/**
 * Checks if [jsonElement] is a [com.google.gson.JsonObject] (using [com.google.gson.JsonElement.isJsonObject]). If it is, it adds that json object in [ConcreteAdditionalFieldAccessor.jsonObject].
 *
 *
 * @param T The type has to be a subclass of [ConcreteAdditionalFieldAccessor].
 * @param jsonElement That will be checked and added to [ConcreteAdditionalFieldAccessor.jsonObject].
 * @return The object itself of method chaining.
 */
internal fun <T : ConcreteAdditionalFieldAccessor> T.with(jsonElement: JsonElement): T {
    if (jsonElement.isJsonObject) {
        (this as ConcreteAdditionalFieldAccessor).jsonObject = jsonElement.asJsonObject
    }
    return this
}