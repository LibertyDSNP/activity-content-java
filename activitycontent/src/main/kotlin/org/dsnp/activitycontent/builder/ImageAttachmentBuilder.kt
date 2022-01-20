package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.*

/**
 * Builder for creating [ActivityContentImage] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#image) for details.
*/
class ImageAttachmentBuilder {
    private var name: String? = null
    private var urls = mutableListOf<ActivityContentImageLink>()
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Sets the name of the image attachment.
     *
     * @param name The name of the image attachment.
     * @return The [ImageAttachmentBuilder] object to be used for chaining method calls.
     */
    fun withName(name: String): ImageAttachmentBuilder {
        this.name = name
        return this
    }

    /**
     * Adds an [ActivityContentImageLink].
     * IMPORTANT: At least one added URL must have a supported image MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types)).
     *
     * @param mediaType The MIME type ([ActivityContentImageLink.MediaType]) of the image link.
     * @param hash The hash of the content the image link is pointing to.
     * @param href The URL of the content. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
     * @param additionalFields Optional additional fields that will be added to the [ActivityContentImageLink] object when it is converted to JSON.
     * @return The [ImageAttachmentBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addUrl(
        mediaType: ActivityContentImageLink.MediaType,
        hash: List<ActivityContentHash>,
        href: String,
        height: Int? = null,
        width: Int? = null,
        additionalFields: Map<String, Any>? = null
    ): ImageAttachmentBuilder {
        this.urls.add(
            ConcreteActivityContentImageLink(
                mediaType,
                hash,
                href,
                height,
                width,
                additionalFields = additionalFields
            )
        )
        return this
    }

    /**
     * Adds an [ActivityContentImageLink].
     * IMPORTANT: At least one added URL must have a supported image MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types)).
     *
     * @param url The [ActivityContentImageLink] to be added.
     * @return The [ImageAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addUrl(url: ActivityContentImageLink): ImageAttachmentBuilder {
        this.urls.add(url)
        return this
    }

    /**
     * Adds an additional field that will be added to the [ActivityContentImage] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [ImageAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): ImageAttachmentBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentImage] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentImage] object to JSON.
     * @return The [ImageAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): ImageAttachmentBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Validates and builds the [ActivityContentImage] instance using the configured values.
     *
     * @return The configured [ActivityContentImage] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentImage {
        val image = ConcreteActivityContentImage(name, urls, additionalFields = additionalFields)
        image.validate()
        return image
    }
}