package org.dsnp.activitycontent.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import java.util.*

internal const val TYPE_NOTE = "Note"
internal const val TYPE_PROFILE = "Profile"
internal const val TYPE_MENTION = "Mention"
internal const val TYPE_AUDIO = "Audio"
internal const val TYPE_IMAGE = "Image"
internal const val TYPE_VIDEO = "Video"
internal const val TYPE_LINK = "Link"
internal const val TYPE_PLACE = "Place"
internal const val MEDIA_TYPE_TEXT_PLAIN = "text/plain"

typealias DSNPUserURI = String

/**
 * Classes implementing this interface can be validated for correctness.
*/
interface Validation {
    /**
     * If validation fails, a [ValidationException] is being thrown.
     *
     */
    @Throws(ValidationException::class)
    fun validate()
}

/**
 * Interface that adds support for accessing additional fields when reading from JSON.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#additional-fields)
*/
interface AdditionalFieldAccessor {
    /**
     * If a field with the given [fieldName] exists, and it's type is string, it will return that string, null otherwise.
     *
     * @param fieldName The name of the JSON field that should be returned as a [String].
     * @return The field value if it's type is string, null otherwise or if the field doesn't exist.
     */
    fun getString(fieldName: String): String?

    /**
     * If a field with the given [fieldName] exists, and it's type is integer, it will return that integer, null otherwise.
     *
     * @param fieldName The name of the JSON field that should be returned as a [Int].
     * @return The field value if it's type is integer, null otherwise or if the field doesn't exist.
     */
    fun getInt(fieldName: String): Int?

    /**
     * If a field with the given [fieldName] exists, and it's type is double, it will return that double, null otherwise.
     *
     * @param fieldName The name of the JSON field that should be returned as a [Double].
     * @return The field value if it's type is double, null otherwise or if the field doesn't exist.
     */
    fun getDouble(fieldName: String): Double?

    /**
     * If a field with the given [fieldName] exists, and it's type is integer, it will return that boolean, null otherwise.
     *
     * @param fieldName The name of the JSON field that should be returned as a [Boolean].
     * @return The field value if it's type is boolean null otherwise or if the field doesn't exist.
     */
    fun getBoolean(fieldName: String): Boolean?

    /**
     * If a field with the given [fieldName] exists, and it's type is [T], it will cast and return that object, null otherwise.
     *
     * @param T
     * @param fieldName The name of the JSON field that should be returned as [T].
     * @param klass A Class object representing [T]. This is necessary to cast the return value into the correct type.
     * @return The field value if it can be cast to T, null otherwise or if the field doesn't exist.
     */
    fun <T> getObject(fieldName: String, klass: Class<T>): T?

    /**
     * If a field with the given [fieldName] exists, and it's a list of [T], it will return that list, null otherwise.
     *
     * @param fieldName The name of the JSON field that should be returned as a List<T>.
     * @return The field value if it's type is List<T>, null otherwise or if the field doesn't exist.
     */
    fun <T> getList(fieldName: String, klass: Class<T>): List<T>?
}

/**
 * Base interface for top level activity content.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview)
 */
interface ActivityContentBase : AdditionalFieldAccessor, Validation {
    /**
     * Is always "https://www.w3.org/ns/activitystreams"
     */
    val context: String
    val type: String
    val name: String?
    val published: Date?
    val location: ActivityContentLocation?
    val tag: List<ActivityContentTag>?

    /**
     * Converts the object into JSON using the passed in [Gson] instance.
     *
     * @param gson Gson instance that's used to convert the object to JSON.
     * @return The JSON string.
     */
    @Throws(ValidationException::class)
    fun toJson(gson: Gson? = ActivityContent.buildOutputGson()): String
}

/**
 * ActivityContentNote represents a post created by the user.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Types/Note)
*/
interface ActivityContentNote : ActivityContentBase {
    /**
     * Is always "Note"
     */
    override val type: String
        get() = TYPE_NOTE
    /**
     * MIME type of the [content] field. Is always "plain/text".
     */
    val mediaType: String
        get() = MEDIA_TYPE_TEXT_PLAIN
    val content: String
    val attachment: List<ActivityContentAttachment>?
}

/**
 * ActivityContentProfile represents profile data for the posting user.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Types/Profile)
*/
interface ActivityContentProfile : ActivityContentBase {
    override val type: String
        get() = TYPE_PROFILE
    /**
     * The plain text biography of the profile.
     */
    val summary: String?
    val icon: List<ActivityContentImageLink>?
}

/**
 * ActivityContentLocation represents location data associated with an
 * []ActivityContentNote] or [ActivityContentProfile].
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Location)
*/
interface ActivityContentLocation : AdditionalFieldAccessor, Validation {
    /**
     * Is always "Place"
     */
    val type: String
        get() = TYPE_PLACE
    val name: String
    /**
     * 	The accuracy of the coordinates as a percentage. (e.g. "94.0" means "94.0% accurate").
     */
    val accuracy: Float?
    val altitude: Float?
    val latitude: Float?
    val longitude: Float?
    /**
     * The area around the given point that comprises the location.
     */
    val radius: Float?
    /**
     * The units for [radius] and [altitude].
     */
    val units: Unit?

    enum class Unit {
        @SerializedName("cm")
        CENTIMETER,

        @SerializedName("feet")
        FEET,

        @SerializedName("inches")
        INCHES,

        @SerializedName("km")
        KILOMETER,

        @SerializedName("m")
        METER,

        @SerializedName("miles")
        MILES
    }
}

/**
 * ActivityContentTag is either an [ActivityContentHashtag] or an
 * [ActivityContentMention].
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Tag)
 */
interface ActivityContentTag : AdditionalFieldAccessor, Validation

/**
 * ActivityContentHashtag represents a hashtag associated with an
 * [ActivityContentNote] or an [ActivityContentProfile].
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Tag#hashtag)
*/
interface ActivityContentHashtag : ActivityContentTag {
    val name: String
}

/**
 * ActivityContentMention represents a mention associated with an
 * ActivityContentNote or an ActivityContentProfile.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Tag#mention)
*/
interface ActivityContentMention : ActivityContentTag {
    /**
     * Is always "Mention".
     */
    val type: String
        get() = TYPE_MENTION
    /**
     * The [DSNP User URI](https://spec.dsnp.org/Identifiers#dsnp-user-uri) of the mentioned user.
     */
    val id: DSNPUserURI
    val name: String?
}

/**
 * ActivityContentAttachment represents a piece of external content associated
 * with an [ActivityContentNote], such as a picture, video, audio clip or website.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments)
 */
interface ActivityContentAttachment : AdditionalFieldAccessor, Validation {
    val type: String
    val name: String?
}

/**
 * ActivityContentAudio represents an audio clip attached to an
 * [ActivityContentNote]. ActivityContentAudio objects contain an array of
 * [ActivityContentAudioLink]s with different versions of the same content. For
 * example, a single item of audio content may be available in multiple formats,
 * such as OGG or MP3, which may be included as individual
 * [ActivityContentAudioLink] objects. The semantic content of each file should
 * always be identical.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#audio)
 */
interface ActivityContentAudio : ActivityContentAttachment {
    /**
     * Is always "Audio"
     */
    override val type: String
        get() = TYPE_AUDIO
    /**
     * An array of links for given audio content in different formats.
     * IMPORTANT: At least one URL must have a supported audio MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-audio-mime-types)).
     */
    val url: List<ActivityContentAudioLink>
    /**
     * The duration of the audio attachment.
     * IMPORTANT: The string has to have the supported [format](https://www.w3.org/TR/xmlschema11-2/#duration).
     */
    val duration: String?
}

/**
 * ActivityContentImage represents an image file attached to an
 * [ActivityContentNote]. ActivityContentImage objects contain an array of
 * [ActivityContentImageLink]s with different versions of the same content. For
 * example, a single picture may be available in multiple formats, such as JPEG
 * or PNG, which may be included as individual [ActivityContentImageLink]
 * objects. The [ActivityContentImageLink.height] and [ActivityContentImageLink.width] of the included [ActivityContentImageLink]
 * objects may also vary to provide faster loading on different screen size
 * devices. The semantic content of each file should always be identical.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#image)
 */
interface ActivityContentImage : ActivityContentAttachment {
    /**
     * Is always "Image".
     */
    override val type: String
        get() = TYPE_IMAGE
    /**
     * An array of links for given image content in different formats.
     * IMPORTANT: At least one URL must have a supported image MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types)).
     */
    val url: List<ActivityContentImageLink>
}

/**
 * ActivityContentVideo represents an video file attached to an
 * [ActivityContentNote]. ActivityContentVideo objects contain an array of
 * [ActivityContentVideoLink]s with different versions of the same content. For
 * example, a single video may be available in multiple formats, such as MPEG
 * or MKV, which may be included as individual [ActivityContentVideoLink]
 * objects. The [ActivityContentVideoLink.height] and [ActivityContentVideoLink.width] of the included [ActivityContentVideoLink]
 * objects may also vary to provide faster loading on different screen size
 * devices. The semantic content of each file should always be identical.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#video)
 */
interface ActivityContentVideo : ActivityContentAttachment {
    /**
     * Is always "Video".
     */
    override val type: String
        get() = TYPE_VIDEO
    /**
     * 	An array of links for given video content in different formats.
     * IMPORTANT: At least one URL must have a supported image MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types)).
     */
    val url: List<ActivityContentVideoLink>
    /**
     * The duration of the audio attachment.
     * IMPORTANT: The string has to have the supported [format](https://www.w3.org/TR/xmlschema11-2/#duration).
     */
    val duration: String?
}

/**
 * ActivityContentLink represents a link attached to an [ActivityContentNote].
 * Unlike [ActivityContentAudio], [ActivityContentImage], and [ActivityContentVideo]
 * objects, link objects may point to dynamic content, such as a news article,
 * which should not be hashed to prove their authenticity.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#link)
 */
interface ActivityContentLink : ActivityContentAttachment {
    /**
     * Is always "Link".
     */
    override val type: String
        get() = TYPE_LINK

    /**
     * The URL for the given link.
     * IMPORTANT: The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
     */
    val href: String
}

/**
 * Super interface for [ActivityContentAudioLink], [ActivityContentImageLink], and [ActivityContentVideoLink].
 */
interface ActivityContentMediaLink : AdditionalFieldAccessor, Validation {
    /**
     * Is always "Link".
     */
    val type: String
        get() = TYPE_LINK
    /**
     * The URL for the given link.
     * IMPORTANT: The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
     */
    val href: String
    /**
     * Array of hashes for linked content validation.
     * IMPORTANT: At least one hash must use a supported algorithm (see [DSNP Spec](https://spec.dsnp.org/ActivityContentHash#supported-algorithms)).
     */
    val hash: List<ActivityContentHash>
}

/**
 * ActivityContentAudioLink represents a specific audio file included in an
 * ActivityContentAudio object.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#audio-link)
 */
interface ActivityContentAudioLink : ActivityContentMediaLink {
    /**
     * MIME type of href content.
     * IMPORTANT: At least one [ActivityContentAudioLink] of a [ActivityContentAudio] object must have a supported image MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types)).
     */
    val mediaType: MediaType

    sealed class MediaType {
        /**
         * MIME type: audio/mpeg
         * @see [RFC3003](https://tools.ietf.org/html/rfc3003)
         */
        object MP3 : MediaType()
        /**
         * MIME type: audio/ogg
         * @see [RFC5334](https://tools.ietf.org/html/rfc5334)
         */
        object OGG : MediaType()
        /**
         * MIME type: audio/webm
         * @see [WebM standard](https://www.webmproject.org/docs/container)
         */
        object WebM : MediaType()
        /**
         * Can be used for unsupported MIME types.
         */
        data class Other(val mimeType: String) : MediaType()
    }
}

/**
 * ActivityContentImageLink represents a specific image file included in an
 * [ActivityContentImage] object.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#image-link)
 */
interface ActivityContentImageLink : ActivityContentMediaLink {
    /**
     * MIME type of href content.
     * IMPORTANT: At least one [ActivityContentImageLink] of a [ActivityContentImage] object must have a supported image MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types)).
     */
    val mediaType: MediaType
    /**
     * A hint as to the rendering height in device-independent pixels.
     */
    val height: Int?
    /**
     * A hint as to the rendering width in device-independent pixels.
     */
    val width: Int?

    sealed class MediaType {
        /**
         * MIME type: image/jpeg
         * @see [RFC2045](https://www.iana.org/go/rfc2045)
         */
        object JPEG : MediaType()
        /**
         * MIME type: image/png
         * @see [W3C PNG Standard](https://www.w3.org/TR/2003/REC-PNG-20031110/)
         */
        object PNG : MediaType()
        /**
         * MIME type: image/svg+xml
         * @see [W3C SVG standard](https://www.w3.org/Graphics/SVG/)
         */
        object SVG : MediaType()
        /**
         * MIME type: image/webp
         * @see [WebP standard](https://developers.google.com/speed/webp/)
         */
        object WebP : MediaType()
        /**
         * MIME type: image/gif
         * @see [RFC2045](https://www.iana.org/go/rfc2045)
         */
        object GIF : MediaType()
        /**
         * Can be used for unsupported MIME types.
         */
        data class Other(val mimeType: String) : MediaType()
    }
}

/**
 * ActivityContentVideoLink represents a specific video file included in an
 * [ActivityContentVideo] object.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#video-link)
 */
interface ActivityContentVideoLink : ActivityContentMediaLink {
    /**
     * MIME type of href content.
     * IMPORTANT: At least one [ActivityContentVideoLink] of a [ActivityContentVideo] object must have a supported image MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types)).
     */
    val mediaType: MediaType
    /**
     * A hint as to the rendering height in device-independent pixels.
     */
    val height: Int?
    /**
     * A hint as to the rendering width in device-independent pixels.
     */
    val width: Int?

    sealed class MediaType {
        /**
         * MIME type: video/mpeg
         * @see [RFC2045](https://www.iana.org/go/rfc2045)
         */
        object MPEG : MediaType()
        /**
         * MIME type: video/ogg
         * @see [RFC5334](https://www.iana.org/go/rfc5334)
         */
        object OGG : MediaType()
        /**
         * MIME type: video/webm
         * @see [WebM standard](https://www.webmproject.org/docs/container/)
         */
        object WEBM : MediaType()
        /**
         * MIME type: video/H256
         * @see [RFC7798](https://www.iana.org/go/rfc7798)
         */
        object H265 : MediaType()
        /**
         * MIME type: video/mp4
         * @see [RFC4337](https://www.iana.org/go/rfc4337)
         */
        object MP4 : MediaType()
        /**
         * Can be used for unsupported MIME types.
         */
        data class Other(val mimeType: String) : MediaType()
    }
}

/**
 * ActivityContentHash represents a hash included in the hash field of an
 * [ActivityContentAudioLink], [ActivityContentImageLink], or
 * [ActivityContentVideoLink] object to prove its authenticity.
 * @see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Hash)
 */
interface ActivityContentHash : AdditionalFieldAccessor, Validation {
    /**
     * The name of the algorithm that was used to generate the hash [value].
     * IMPORTANT: At least one hash of a [ActivityContentAudioLink], [ActivityContentImageLink], or [ActivityContentVideoLink] must use a supported algorithm (see [DSNP Spec](https://spec.dsnp.org/ActivityContentHash#supported-algorithms)).
     */
    val algorithm: String
    /**
     * The hash value that was generated from the content using [algorithm].
     * IMPORTANT: At least one hash of a [ActivityContentAudioLink], [ActivityContentImageLink], or [ActivityContentVideoLink] must use a supported algorithm (see [DSNP Spec](https://spec.dsnp.org/ActivityContentHash#supported-algorithms)).
     */
    val value: String
}