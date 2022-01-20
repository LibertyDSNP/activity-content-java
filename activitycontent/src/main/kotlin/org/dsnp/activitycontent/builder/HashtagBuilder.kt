package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentHashtag
import org.dsnp.activitycontent.model.ConcreteActivityContentHashtag

/**
 * Builder for creating [ActivityContentHashtag] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Tag#mention) for details.
 *
 * @property name The name of the hashtag.
 */
class HashtagBuilder(
    private val name: String
) {
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Adds an additional field that will be added to the [ActivityContentHashtag] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [HashtagBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): HashtagBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentHashtag] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentHashtag] object to JSON.
     * @return The [HashtagBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): HashtagBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Validates and builds the [ActivityContentHashtag] instance using the configured values.
     *
     * @return The configured [ActivityContentHashtag] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentHashtag {
        val hash = ConcreteActivityContentHashtag(name, additionalFields = additionalFields)
        hash.validate()
        return hash
    }
}