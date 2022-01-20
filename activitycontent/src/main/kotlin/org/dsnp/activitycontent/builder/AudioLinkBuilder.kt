package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.extension.hash
import org.dsnp.activitycontent.model.*

/**
 * Builder for creating [ActivityContentAudioLink] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#audio-link) for details.
 *
 * @property mediaType The MIME type ([ActivityContentAudioLink.MediaType]) of the audio link.
 * @property href The URL of the content. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
 */
class AudioLinkBuilder(
    private val mediaType: ActivityContentAudioLink.MediaType,
    private val href: String,
) {
    private val hash = mutableListOf<ActivityContentHash>()
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Adds an [ActivityContentHash].
     * IMPORTANT: At least one added hash must use a supported algorithm (see [DSNP Spec](https://spec.dsnp.org/ActivityContentHash#supported-algorithms)).
     *
     * @param hash The [ActivityContentHash] that will be added.
     * @return The [AudioLinkBuilder] object to be used for chaining method calls.
     */
    fun addHash(hash: ActivityContentHash): AudioLinkBuilder {
        this.hash.add(hash)
        return this
    }

    /**
     * Adds an [ActivityContentHash].
     *
     * @param content The content will be automatically hashed by using the appropriate hash algorithm (keccak256).
     * @param additionalFields Optional additional fields that will be added to the [ActivityContentHash] object when it is converted to JSON.
     * @return The [AudioLinkBuilder] object to be used for chaining method calls.
     */
    fun addHash(
        content: String,
        additionalFields: Map<String, Any>? = null
    ): AudioLinkBuilder {
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
     * @return The [AudioLinkBuilder] object to be used for chaining method calls.
     */
    fun addHash(
        content: ByteArray,
        additionalFields: Map<String, Any>? = null
    ): AudioLinkBuilder {
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
     * @return The [AudioLinkBuilder] object to be used for chaining method calls.
     */
    fun addHash(
        value: String,
        algorithm: String,
        additionalFields: Map<String, Any>? = null
    ): AudioLinkBuilder {
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
     * Adds an additional field that will be added to the [ActivityContentAudioLink] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [AudioLinkBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): AudioLinkBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentAudioLink] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentAudioLink] object to JSON.
     * @return The [AudioLinkBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): AudioLinkBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Validates and builds the [ActivityContentAudioLink] instance using the configured values.
     *
     * @return The configured [ActivityContentAudioLink] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentAudioLink {
        val link = ConcreteActivityContentAudioLink(mediaType, hash, href, additionalFields = additionalFields)
        link.validate()
        return link
    }
}