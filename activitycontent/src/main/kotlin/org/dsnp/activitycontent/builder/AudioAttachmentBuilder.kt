package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentAudio
import org.dsnp.activitycontent.model.ActivityContentAudioLink
import org.dsnp.activitycontent.model.ActivityContentHash
import org.dsnp.activitycontent.model.ConcreteActivityContentAudio

/**
 * Builder for creating [ActivityContentAudio] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#audio for) details.
*/
class AudioAttachmentBuilder {
    private var name: String? = null
    private var urls = mutableListOf<ActivityContentAudioLink>()
    private var duration: String? = null
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Sets the name of the audio attachment.
     *
     * @param name The name of the audio attachment.
     * @return The [AudioAttachmentBuilder] object to be used for chaining method calls.
     */
    fun withName(name: String): AudioAttachmentBuilder {
        this.name = name
        return this
    }

    /**
     * Sets the duration of the audio attachment.
     *
     * @param duration The duration of the audio attachment.
     * @return The [AudioAttachmentBuilder] object to be used for chaining method calls.
     */
    fun withDuration(duration: String): AudioAttachmentBuilder {
        this.duration = duration
        return this
    }

    /**
     * Adds an [ActivityContentAudioLink].
     * IMPORTANT: At least one added URL must have a supported audio MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-audio-mime-types)).
     *
     * @param mediaType The MIME type ([ActivityContentAudioLink.MediaType]) of the audio link.
     * @param hash The hash of the content the audio link is pointing to.
     * @param href The URL of the content. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
     * @param additionalFields Optional additional fields that will be added to the [ActivityContentAudioLink] object when it is converted to JSON.
     * @return The [AudioAttachmentBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addUrl(
        mediaType: ActivityContentAudioLink.MediaType,
        hash: List<ActivityContentHash>,
        href: String,
        additionalFields: Map<String, Any>? = null
    ): AudioAttachmentBuilder {
        val linkBuilder = ActivityContent.Builders.Link.audio(
            mediaType,
            href
        )
        hash.forEach {
            linkBuilder.addHash(it)
        }
        additionalFields?.let { linkBuilder.addAdditionalFields(it) }
        this.urls.add(
            linkBuilder.build()
        )
        return this
    }

    /**
     * Adds an [ActivityContentAudioLink].
     * IMPORTANT: At least one added URL must have a supported audio MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-audio-mime-types)).
     *
     * @param url The [ActivityContentAudioLink] to be added.
     * @return The [AudioAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addUrl(url: ActivityContentAudioLink): AudioAttachmentBuilder {
        this.urls.add(url)
        return this
    }

    /**
     * Adds an additional field that will be added to the [ActivityContentAudio] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [AudioAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): AudioAttachmentBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentAudio] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentAudio] object to JSON.
     * @return The [AudioAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): AudioAttachmentBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Validates and builds the [ActivityContentAudio] instance using the configured values.
     *
     * @return The configured [ActivityContentAudio] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentAudio {
        val audio = ConcreteActivityContentAudio(name, urls, duration, additionalFields = additionalFields)
        audio.validate()
        return audio
    }
}