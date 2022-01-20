package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.*
import java.util.*

/**
 * Builder for creating [ActivityContentNote] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Types/Note) for details.
 *
 * @property content The content of the note.
 */
class NoteBuilder(
    private val content: String
) {
    private var name: String? = null
    private var published: Date? = null
    private var location: ActivityContentLocation? = null
    private val tags = mutableListOf<ActivityContentTag>()
    private val attachments = mutableListOf<ActivityContentAttachment>()
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Sets the display name of the note.
     *
     * @param name The display name of the note.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    fun withName(name: String): NoteBuilder {
        this.name = name
        return this
    }

    /**
     * Sets the published date of the note.
     *
     * @param date The published date of the note. When converted to JSON, the published date will be converted into an [ISO8601](https://www.iso.org/iso-8601-date-and-time-format.html) formatted date string.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    fun withPublished(date: Date): NoteBuilder {
        this.published = date
        return this
    }

    /**
     * Sets the location of the note.
     *
     * @param name The name of the location
     * @param accuracy The accuracy of the coordinates as a percentage. (e.g. "94.0" means "94.0% accurate").
     * @param altitude The altitude of the location.
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param radius The radius of the location.
     * @param units The units for [radius] and [altitude].
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentLocation] object to JSON.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun withLocation(
        name: String,
        accuracy: Float? = null,
        altitude: Float? = null,
        latitude: Float? = null,
        longitude: Float? = null,
        radius: Float? = null,
        units: ActivityContentLocation.Unit? = null,
        additionalFields: Map<String, Any>? = null
    ): NoteBuilder {
        this.location = ConcreteActivityContentLocation(
            name,
            accuracy,
            altitude,
            latitude,
            longitude,
            radius,
            units,
            additionalFields = additionalFields
        )
        return this
    }

    /**
     * Sets the location of the note.
     *
     * @param location The [ActivityContentLocation] object used for the note.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    fun withLocation(location: ActivityContentLocation): NoteBuilder {
        this.location = location
        return this
    }

    /**
     * Adds an additional field that will be added to the [ActivityContentNote] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): NoteBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentNote] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentNote] object to JSON.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): NoteBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Adds an [ActivityContentHashtag] to the note.
     *
     * @param name The name of the hashtag.
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentHashtag] object to JSON.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addTagAsHashtag(name: String, additionalFields: Map<String, Any>? = null): NoteBuilder {
        this.tags.add(ConcreteActivityContentHashtag(name, additionalFields))
        return this
    }

    /**
     * Adds an [ActivityContentMention] to the note.
     *
     * @param id The [DSNP User URI](https://spec.dsnp.org/Identifiers#dsnp-user-uri) of the mentioned user.
     * @param name The name of the user that's referenced in [id].
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentMention] object to JSON.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addTagAsMention(
        id: DSNPUserURI,
        name: String? = null,
        additionalFields: Map<String, Any>? = null
    ): NoteBuilder {
        this.tags.add(ConcreteActivityContentMention(id, name, additionalFields = additionalFields))
        return this
    }

    /**
     * Adds an [ActivityContentTag] to the note.
     *
     * @param tag The tag to be added.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    fun addTag(tag: ActivityContentTag): NoteBuilder {
        this.tags.add(tag)
        return this
    }

    /**
     * Adds an attachment in from of an audio attachment to the note.
     * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#audio)
     *
     * @param url A list of [ActivityContentAudioLink] objects. IMPORTANT: At least one URL must have a supported audio MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-audio-mime-types)).
     * @param name The name of the audio attachment.
     * @param duration The duration of the audio attachment.
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentAudio] object to JSON.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addAttachmentAsAudio(
        url: List<ActivityContentAudioLink>,
        name: String? = null,
        duration: String?,
        additionalFields: Map<String, Any>? = null
    ): NoteBuilder {
        this.attachments.add(ConcreteActivityContentAudio(name, url, duration, additionalFields = additionalFields))
        return this
    }

    /**
     * Adds an attachment in from of an image attachment to the note.
     * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#image)
     *
     * @param url A list of [ActivityContentImageLink] objects. IMPORTANT: At least one URL must have a supported image MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types)).
     * @param name The name of the image attachment.
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentImage] object to JSON.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addAttachmentAsImage(
        url: List<ActivityContentImageLink>,
        name: String? = null,
        additionalFields: Map<String, Any>? = null
    ): NoteBuilder {
        this.attachments.add(ConcreteActivityContentImage(name, url, additionalFields = additionalFields))
        return this
    }

    /**
     * Adds an attachment in from of a video attachment to the note.
     * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#video)
     *
     * @param url A list of [ActivityContentVideoLink] objects. IMPORTANT: At least one URL must have a supported video MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-video-mime-types)).
     * @param name The name of the audio attachment.
     * @param duration The duration of the video attachment.
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentVideo] object to JSON.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addAttachmentAsVideo(
        url: List<ActivityContentVideoLink>,
        name: String? = null,
        duration: String? = null,
        additionalFields: Map<String, Any>? = null
    ): NoteBuilder {
        this.attachments.add(ConcreteActivityContentVideo(name, url, duration, additionalFields = additionalFields))
        return this
    }

    /**
     * Adds an attachment in from of a link attachment to the note.
     * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#link)
     *
     * @param href The URL of the link. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
     * @param name The name of the link.
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentAudio] object to JSON.
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addAttachmentAsLink(
        href: String,
        name: String? = null,
        additionalFields: Map<String, Any>? = null
    ): NoteBuilder {
        this.attachments.add(ConcreteActivityContentLink(name, href, additionalFields = additionalFields))
        return this
    }

    /**
     * Adds an attachment to the note.
     * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments)
     *
     * @param attachment The attachment that will be added to the note. Has to be one of [ActivityContentAudio], [ActivityContentImage], [ActivityContentVideo], or [ActivityContentLink].
     * @return The [NoteBuilder] object to be used for chaining method calls.
     */
    fun addAttachment(attachment: ActivityContentAttachment): NoteBuilder {
        this.attachments.add(attachment)
        return this
    }

    /**
     * Validates and builds the [ActivityContentNote] instance using the configured values.
     *
     * @return The configured [ActivityContentNote] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentNote {
        val note = ConcreteActivityContentNote(
            name,
            published,
            location,
            tags.ifEmpty { null },
            content,
            attachments.ifEmpty { null },
            additionalFields = additionalFields
        )
        note.validate()
        return note
    }
}