package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentLink
import org.dsnp.activitycontent.model.ConcreteActivityContentLink

/**
 * Builder for creating [ActivityContentLink] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#link) for details.
 *
 * @property href The URL of the link. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
 */
class LinkAttachmentBuilder(
    private var href: String
) {
    private var name: String? = null
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Sets the name of the link.
     *
     * @param name The name of the link.
     * @return The [LinkAttachmentBuilder] object to be used for chaining method calls.
     */
    fun withName(name: String): LinkAttachmentBuilder {
        this.name = name
        return this
    }

    /**
     * Adds an additional field that will be added to the [ActivityContentLink] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [LinkAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): LinkAttachmentBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentLink] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentLink] object to JSON.
     * @return The [LinkAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): LinkAttachmentBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Validates and builds the [ActivityContentLink] instance using the configured values.
     *
     * @return The configured [ActivityContentLink] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentLink {
        val link = ConcreteActivityContentLink(name, href, additionalFields = additionalFields)
        link.validate()
        return link
    }
}