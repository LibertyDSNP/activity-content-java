package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.*
import java.util.*

/**
 * Builder for creating [ActivityContentProfile] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Types/Profile) for details.
*/
class ProfileBuilder {
    private var name: String? = null
    private var published: Date? = null
    private var location: ActivityContentLocation? = null
    private val tags = mutableListOf<ActivityContentTag>()
    private var summary: String? = null
    private val icons = mutableListOf<ActivityContentImageLink>()
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Sets the display name of the profile.
     *
     * @param name The display name of the profile.
     * @return The [ProfileBuilder] object to be used for chaining method calls.
     */
    fun withName(name: String): ProfileBuilder {
        this.name = name
        return this
    }
    
    /**
     * Sets the published date of the profile.
     *
     * @param date The published date of the profile. When converted to JSON, the published date will be converted into an [ISO8601](https://www.iso.org/iso-8601-date-and-time-format.html) formatted date string.
     * @return The [ProfileBuilder] object to be used for chaining method calls.
     */
    fun withPublished(date: Date): ProfileBuilder {
        this.published = date
        return this
    }

    /**
     * Sets the location of the profile.
     *
     * @param name The name of the location
     * @param accuracy The accuracy of the coordinates as a percentage. (e.g. "94.0" means "94.0% accurate").
     * @param altitude The altitude of the location.
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param radius The radius of the location.
     * @param units The units for [radius] and [altitude].
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentLocation] object to JSON.
     * @return The [ProfileBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun withLocation(
        name: String,
        accuracy: Float?,
        altitude: Float?,
        latitude: Float?,
        longitude: Float?,
        radius: Float?,
        units: ActivityContentLocation.Unit?,
        additionalFields: Map<String, Any>? = null
    ): ProfileBuilder {
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
     * Sets the location of the profile.
     *
     * @param location The [ActivityContentLocation] object used for the profile.
     * @return The [ProfileBuilder] object to be used for chaining method calls.
     */
    fun withLocation(location: ActivityContentLocation): ProfileBuilder {
        this.location = location
        return this
    }

    /**
     * Sets the summary (i.e. plain text biography) of the profile.
     *
     * @param summary The summary (i.e. plain text biography) of the profile.
     * @return The [ProfileBuilder] object to be used for chaining method calls.
     */
    fun withSummary(summary: String): ProfileBuilder {
        this.summary = summary
        return this
    }

    /**
     * Adds an additional field that will be added to the [ActivityContentProfile] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [ProfileBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): ProfileBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentProfile] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentProfile] object to JSON.
     * @return The [ProfileBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): ProfileBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Adds an [ActivityContentHashtag] to the profile.
     *
     * @param name The name of the hashtag.
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentHashtag] object to JSON.
     * @return The [ProfileBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addTagAsHashtag(name: String, additionalFields: Map<String, Any>? = null): ProfileBuilder {
        this.tags.add(ConcreteActivityContentHashtag(name, additionalFields))
        return this
    }

    /**
     * Adds an [ActivityContentMention] to the profile.
     *
     * @param id The [DSNP User URI](https://spec.dsnp.org/Identifiers#dsnp-user-uri) of the mentioned user.
     * @param name The name of the user that's referenced in [id].
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentMention] object to JSON.
     * @return The [ProfileBuilder] object to be used for chaining method calls.
     */
    @JvmOverloads
    fun addTagAsMention(
        id: DSNPUserURI,
        name: String? = null,
        additionalFields: Map<String, Any>? = null
    ): ProfileBuilder {
        this.tags.add(ConcreteActivityContentMention(id, name, additionalFields = additionalFields))
        return this
    }

    /**
     * Adds an [ActivityContentTag] to the profile.
     *
     * @param tag The tag to be added.
     * @return The [ProfileBuilder] object to be used for chaining method calls.
     */
    fun addTag(tag: ActivityContentTag): ProfileBuilder {
        this.tags.add(tag)
        return this
    }

    /**
     * Adds an [ActivityContentImageLink] to the profile that represents an avatar of the profile.
     * IMPORTANT: At least one added URL must have a supported image MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types)).
     *
     * @param mediaType The MIME type ([ActivityContentImageLink.MediaType]) of the avatar icon.
     * @param hash The hash of the avatar icon.
     * @param href The URL of the avatar icon. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
     * @param additionalFields Optional additional fields that will be added to the [ActivityContentImageLink] object when it is converted to JSON.
     * @return The [ImageAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addIcon(
        mediaType: ActivityContentImageLink.MediaType,
        hash: List<ActivityContentHash>,
        href: String,
        height: Int? = null,
        width: Int? = null,
        additionalFields: Map<String, Any>? = null
    ): ProfileBuilder {
        this.icons.add(
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
     * Adds an [ActivityContentImageLink] to the profile that represents an avatar of the profile.
     * IMPORTANT: At least one added URL must have a supported image MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types)).
     *
     * @param link The [ActivityContentImageLink] to be added as an avatar icon to the profile.
     * @return The [ImageAttachmentBuilder] object to be used for chaining method calls.
     */
    fun addIcon(link: ActivityContentImageLink): ProfileBuilder {
        this.icons.add(link)
        return this
    }

    /**
     * Validates and builds the [ActivityContentProfile] instance using the configured values.
     *
     * @return The configured [ActivityContentProfile] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentProfile {
        val profile = ConcreteActivityContentProfile(
            name,
            published,
            location,
            tags.ifEmpty { null },
            summary,
            icons.ifEmpty { null },
            additionalFields = additionalFields
        )
        profile.validate()
        return profile
    }
}