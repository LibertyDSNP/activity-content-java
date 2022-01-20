package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.*

/**
 * Builder for creating [ActivityContentVideo] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#video) for details.
*/
class VideoAttachmentBuilder {
    private var name: String? = null
    private var urls = mutableListOf<ActivityContentVideoLink>()
    private var duration: String? = null
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Sets the name of the video attachment.
     *
     * @param name The name of the video attachment.
     * @return The [VideoAttachmentBuilder] object to be used for chaining method calls.
     */
    fun withName(name: String): VideoAttachmentBuilder {
        this.name = name
        return this
    }

    /**
     * Sets the duration of the video attachment.
     *
     * @param duration The name of the video attachment.
     * @return The [VideoAttachmentBuilder] object to be used for chaining method calls.
     */
    fun withDuration(duration: String): VideoAttachmentBuilder {
        this.duration = duration
        return this
    }

    /**
     * Adds an [ActivityContentVideoLink].
     * IMPORTANT: At least one added URL must have a supported video MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-video-mime-types)).
     *
     * @param mediaType The MIME type ([ActivityContentVideoLink.MediaType]) of the video link.
     * @param hash The hash of the content the video link is pointing to.
     * @param href The URL of the content. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
     * @param additionalFields Optional additional fields that will be added to the [ActivityContentVideoLink] object when it is converted to JSON.
     * @return The [VideoAttachmentBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addUrl(
        mediaType: ActivityContentVideoLink.MediaType,
        hash: List<ActivityContentHash>,
        href: String,
        height: Int? = null,
        width: Int? = null,
        additionalFields: Map<String, Any>? = null
    ): VideoAttachmentBuilder {
        this.urls.add(
            ConcreteActivityContentVideoLink(
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
     * Adds an [ActivityContentVideoLink].
     * IMPORTANT: At least one added URL must have a supported video MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-audio-mime-types)).
     *
     * @param url The [ActivityContentVideoLink] to be added.
     * @return The [VideoAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addUrl(url: ActivityContentVideoLink): VideoAttachmentBuilder {
        this.urls.add(url)
        return this
    }

    /**
     * Adds an additional field that will be added to the [ActivityContentVideo] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [VideoAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): VideoAttachmentBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentVideo] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentVideo] object to JSON.
     * @return The [VideoAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): VideoAttachmentBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Validates and builds the [ActivityContentVideo] instance using the configured values.
     *
     * @return The configured [ActivityContentVideo] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentVideo {
        val video = ConcreteActivityContentVideo(name, urls, duration, additionalFields = additionalFields)
        video.validate()
        return video
    }
}