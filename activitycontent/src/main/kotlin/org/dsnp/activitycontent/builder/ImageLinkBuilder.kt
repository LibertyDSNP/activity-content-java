package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.extension.hash
import org.dsnp.activitycontent.model.*

/**
 * Builder for creating [ActivityContentVideoLink] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#video-link) for details.
 *
 * @property mediaType The MIME type ([ActivityContentImageLink.MediaType]) of the image link.
 * @property href The URL of the content. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
 */
class ImageLinkBuilder(
    private val mediaType: ActivityContentImageLink.MediaType,
    private val href: String,
) {
    private var height: Int? = null
    private var width: Int? = null
    private val hash = mutableListOf<ActivityContentHash>()
    private val additionalFields = mutableMapOf<String, Any>()

    /**
     * Sets the height of the image that this [ImageLinkBuilder] links to.
     *
     * @param height Height of the image.
     * @return The [ImageLinkBuilder] object to be used for chaining method calls.
     */
    fun withHeight(height: Int): ImageLinkBuilder {
        this.height = height
        return this
    }

    /**
     * Sets the width of the image that this [ImageLinkBuilder] links to.
     *
     * @param width Width of the image
     * @return The [AudioLinkBuilder] object to be used for chaining method calls.
     */
    fun withWidth(width: Int): ImageLinkBuilder {
        this.width = width
        return this
    }

    /**
     * Adds an [ActivityContentHash].
     * IMPORTANT: At least one added hash must use a supported algorithm (see [DSNP Spec](https://spec.dsnp.org/ActivityContentHash#supported-algorithms)).
     *
     * @param hash The [ActivityContentHash] that will be added.
     * @return The [VideoLinkBuilder] object to be used for chaining method calls.
     */
    fun addHash(hash: ActivityContentHash): ImageLinkBuilder {
        this.hash.add(hash)
        return this
    }

    /**
     * Adds an [ActivityContentHash].
     *
     * @param content The content will be automatically hashed by using the appropriate hash algorithm (keccak256).
     * @param additionalFields Optional additional fields that will be added to the [ActivityContentHash] object when it is converted to JSON.
     * @return The [VideoLinkBuilder] object to be used for chaining method calls.
     */
    fun addHash(
        content: String,
        additionalFields: Map<String, Any>? = null
    ): ImageLinkBuilder {
        addHash(
            ConcreteActivityContentHash(
                content.hash(),
                ALGORITHM_HASH
            ).apply {
                this.additionalFields = additionalFields
            }
        )
        return this
    }

    /**
     * Adds an [ActivityContentHash].
     *
     * @param content The content will be automatically hashed by using the appropriate hash algorithm (keccak256).
     * @param additionalFields Optional additional fields that will be added to the [ActivityContentHash] object when it is converted to JSON.
     * @return The [VideoLinkBuilder] object to be used for chaining method calls.
     */
    fun addHash(
        content: ByteArray,
        additionalFields: Map<String, Any>? = null
    ): ImageLinkBuilder {
        addHash(
            ConcreteActivityContentHash(
                content.hash(),
                ALGORITHM_HASH
            ).apply {
                this.additionalFields = additionalFields
            }
        )
        return this
    }

    /**
     * Adds an [ActivityContentHash].
     * IMPORTANT: At least one added hash must use a supported algorithm (see [DSNP Spec](https://spec.dsnp.org/ActivityContentHash#supported-algorithms)).
     *
     * @param value A hash value that will be saved in the [ActivityContentHash]. IMPORTANT: This parameter takes a hash as a value, NOT content that will be hashed.
     * @param algorithm The name of the algorithm that was used to generate the hash [value].
     * @param additionalFields Optional additional fields that will be added to the [ActivityContentHash] object when it is converted to JSON.
     * @return The [VideoLinkBuilder] object to be used for chaining method calls.
     */
    fun addHash(
        value: String,
        algorithm: String,
        additionalFields: Map<String, Any>? = null
    ): ImageLinkBuilder {
        addHash(
            ConcreteActivityContentHash(
                value,
                algorithm
            ).apply {
                this.additionalFields = additionalFields
            }
        )
        return this
    }

    /**
     * Adds an additional field that will be added to the [ActivityContentVideoLink] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [VideoLinkBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): ImageLinkBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentVideoLink] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentVideoLink] object to JSON.
     * @return The [VideoLinkBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): ImageLinkBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Validates and builds the [ActivityContentVideoLink] instance using the configured values.
     *
     * @return The configured [ActivityContentVideoLink] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentImageLink {
        val link = ConcreteActivityContentImageLink(mediaType, hash, href, height, width, additionalFields = additionalFields)
        link.validate()
        return link
    }
}