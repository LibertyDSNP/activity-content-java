package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentMention
import org.dsnp.activitycontent.model.ConcreteActivityContentMention
import org.dsnp.activitycontent.model.DSNPUserURI

/**
 * Builder for creating [ActivityContentMention] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Tag#mention for) details.
 *
 * @property id The [DSNP User URI](https://spec.dsnp.org/Identifiers#dsnp-user-uri) of the mentioned user.
 */
class MentionBuilder(
    private val id: DSNPUserURI
) {
    private var name: String? = null
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Sets the name of the user that's referenced in [id].
     *
     * @param name The name of the user that's referenced in [id].
     * @return The [MentionBuilder] object to be used for chaining method calls.
     */
    fun withName(name: String): MentionBuilder {
        this.name = name
        return this
    }

    /**
     * Adds an additional field that will be added to the [ActivityContentMention] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [MentionBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): MentionBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentMention] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentMention] object to JSON.
     * @return The [MentionBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): MentionBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Validates and builds the [ActivityContentMention] instance using the configured values.
     *
     * @return The configured [ActivityContentMention] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentMention {
        val hash = ConcreteActivityContentMention(id, name, additionalFields = additionalFields)
        hash.validate()
        return hash
    }
}