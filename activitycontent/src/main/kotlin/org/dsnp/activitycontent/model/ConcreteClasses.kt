package org.dsnp.activitycontent.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.gsonadapter.AdditionalFieldAdapter
import org.dsnp.activitycontent.gsonadapter.MIME_TYPE_AUDIO_PREFIX
import org.dsnp.activitycontent.gsonadapter.MIME_TYPE_IMAGE_PREFIX
import org.dsnp.activitycontent.gsonadapter.MIME_TYPE_VIDEO_PREFIX
import java.util.*

private const val CONTEXT_ACTIVITY_STREAMS = "https://www.w3.org/ns/activitystreams"
private val REGEX_HREF = "^https?://.+".toRegex()
private val REGEX_DSNP = "^dsnp://[1-9][0-9]{0,19}".toRegex()
private val REGEX_DURATION =
    "^-?P(([0-9]+Y)?([0-9]+M)?([0-9]+D)?(T([0-9]+H)?([0-9]+M)?([0-9]+(\\.[0-9]+)?S)?)?)+$".toRegex()
private val REGEX_HASH = "^0x[0-9A-Fa-f]{64}$".toRegex()
internal const val ALGORITHM_HASH = "keccak256"

abstract class ConcreteAdditionalFieldAccessor : AdditionalFieldAccessor {
    abstract var additionalFields: Map<String, Any>?

    abstract var jsonObject: JsonObject?

    override fun getString(fieldName: String): String? {
        return jsonObject?.get(fieldName)?.asJsonPrimitive?.let {
            if (it.isString) {
                it.asString
            } else {
                null
            }
        } ?: additionalFields?.get(fieldName) as? String
    }

    override fun getInt(fieldName: String): Int? {
        return try {
            jsonObject?.get(fieldName)?.asInt ?: additionalFields?.get(fieldName) as? Int
        } catch (t: Throwable) {
            null
        }
    }

    override fun getDouble(fieldName: String): Double? {
        return try {
            jsonObject?.get(fieldName)?.asDouble ?: additionalFields?.get(fieldName) as? Double
        } catch (t: Throwable) {
            null
        }
    }

    override fun getBoolean(fieldName: String): Boolean? {
        return try {
            jsonObject?.get(fieldName)?.asBoolean ?: additionalFields?.get(fieldName) as? Boolean
        } catch (t: Throwable) {
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> getObject(fieldName: String, klass: Class<T>): T? {
        return try {
            jsonObject?.get(fieldName)?.let {
                Gson().fromJson(it.toString(), klass)
            } ?: klass.cast(additionalFields?.get(fieldName))
        } catch (t: Throwable) {
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> getList(fieldName: String, klass: Class<T>): List<T>? {
        return try {
            val typeToken = object : TypeToken<List<T>>() {}.type
            jsonObject?.get(fieldName)?.let {
                Gson().fromJson(it.toString(), typeToken)
            } ?: additionalFields?.get(fieldName) as? List<T>
        } catch (t: Throwable) {
            null
        }
    }
}

internal data class ConcreteActivityContentNote(
    override val name: String?,
    override val published: Date?,
    override val location: ActivityContentLocation?,
    override val tag: List<ActivityContentTag>?,
    override val content: String,
    override val attachment: List<ActivityContentAttachment>?,
    override val type: String = TYPE_NOTE,
    override val mediaType: String = MEDIA_TYPE_TEXT_PLAIN,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentNote, ConcreteAdditionalFieldAccessor() {
    @field:SerializedName(value = "@context")
    override val context: String = CONTEXT_ACTIVITY_STREAMS
    override fun toJson(gson: Gson?): String {
        validate()
        return (gson ?: ActivityContent.buildOutputGson()).toJson(this)
    }

    override fun validate() {
        location?.validate()
        tag?.forEach { it.validate() }
        attachment?.forEach { it.validate() }
        validateAdditionalFields("name", "published", "location", "tag", "content", "attachment", "type", "mediaType")
    }
}

internal data class ConcreteActivityContentProfile(
    override val name: String?,
    override val published: Date?,
    override val location: ActivityContentLocation?,
    override val tag: List<ActivityContentTag>?,
    override val summary: String?,
    override val icon: List<ActivityContentImageLink>?,
    override val type: String = TYPE_PROFILE,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentProfile, ConcreteAdditionalFieldAccessor() {
    @field:SerializedName(value = "@context")
    override val context: String = CONTEXT_ACTIVITY_STREAMS
    override fun toJson(gson: Gson?): String {
        validate()
        return (gson ?: ActivityContent.buildOutputGson()).toJson(this)
    }

    override fun validate() {
        location?.validate()
        tag?.forEach { it.validate() }
        icon?.forEach { it.validate() }
        val validMimeTypes = setOf(
            ActivityContentImageLink.MediaType.JPEG,
            ActivityContentImageLink.MediaType.PNG,
            ActivityContentImageLink.MediaType.SVG,
            ActivityContentImageLink.MediaType.WebP,
            ActivityContentImageLink.MediaType.GIF
        )
        if (icon?.none { validMimeTypes.contains(it.mediaType) } == true) {
            throw ValidationException("At least one icon has to have a supported mime type (GIF/SVG/PNG/WebP/JPEG)")
        }
        validateAdditionalFields("name", "published", "location", "tag", "summary", "icon", "type")
    }
}

internal data class ConcreteActivityContentLocation(
    override val name: String,
    override val accuracy: Float?,
    override val altitude: Float?,
    override val latitude: Float?,
    override val longitude: Float?,
    override val radius: Float?,
    override val units: ActivityContentLocation.Unit?,
    override val type: String = TYPE_PLACE,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentLocation, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        validateAdditionalFields("name", "accuracy", "altitude", "longitude", "radius", "units", "type")
    }
}

internal data class ConcreteActivityContentHashtag(
    override val name: String,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentHashtag, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        validateAdditionalFields("name")
    }
}

internal data class ConcreteActivityContentMention(
    override val id: DSNPUserURI,
    override val name: String?,
    override val type: String = TYPE_MENTION,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentMention, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        id.validate(REGEX_DSNP, "Invalid DSNP user URI: $id")
        validateAdditionalFields("id", "name", "type")
    }
}

internal data class ConcreteActivityContentAudio(
    override val name: String?,
    override val url: List<ActivityContentAudioLink>,
    override val duration: String?,
    override val type: String = TYPE_AUDIO,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentAudio, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        if (url.isEmpty()) {
            throw ValidationException("No url provided for audio")
        }
        url.forEach { it.validate() }
        val validMimeTypes = setOf(
            ActivityContentAudioLink.MediaType.MP3,
            ActivityContentAudioLink.MediaType.OGG,
            ActivityContentAudioLink.MediaType.WebM
        )
        if (url.none { validMimeTypes.contains(it.mediaType) }) {
            throw ValidationException("At least one url has to have a supported mime type (MP3/OGG/WebM)")
        }
        duration?.validate(REGEX_DURATION, "Invalid duration: $duration")
        validateAdditionalFields("name", "url", "duration", "type")
    }
}

internal data class ConcreteActivityContentImage(
    override val name: String?,
    override val url: List<ActivityContentImageLink>,
    override val type: String = TYPE_IMAGE,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentImage, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        if (url.isEmpty()) {
            throw ValidationException("No url provided for image")
        }
        url.forEach { it.validate() }
        val validMimeTypes = setOf(
            ActivityContentImageLink.MediaType.JPEG,
            ActivityContentImageLink.MediaType.PNG,
            ActivityContentImageLink.MediaType.SVG,
            ActivityContentImageLink.MediaType.WebP,
            ActivityContentImageLink.MediaType.GIF
        )
        if (url.none { validMimeTypes.contains(it.mediaType) }) {
            throw ValidationException("At least one url has to have a supported mime type (GIF/SVG/PNG/WebP/JPEG)")
        }
        validateAdditionalFields("name", "url", "type")
    }
}

internal data class ConcreteActivityContentVideo(
    override val name: String?,
    override val url: List<ActivityContentVideoLink>,
    override val duration: String?,
    override val type: String = TYPE_VIDEO,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentVideo, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        if (url.isEmpty()) {
            throw ValidationException("No url provided for video")
        }
        url.forEach { it.validate() }
        val validMimeTypes = setOf(
            ActivityContentVideoLink.MediaType.MPEG,
            ActivityContentVideoLink.MediaType.OGG,
            ActivityContentVideoLink.MediaType.WEBM,
            ActivityContentVideoLink.MediaType.H265,
            ActivityContentVideoLink.MediaType.MP4
        )
        if (url.none { validMimeTypes.contains(it.mediaType) }) {
            throw ValidationException("At least one url has to have a supported mime type (MPEG/OGG/WEBM/H265/MP4)")
        }
        duration?.validate(REGEX_DURATION, "Invalid duration: $duration")
        validateAdditionalFields("name", "url", "duration", "type")
    }
}

internal data class ConcreteActivityContentLink(
    override val name: String?,
    override val href: String,
    override val type: String = TYPE_LINK,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentLink, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        href.validate(REGEX_HREF, "Invalid href: $href")
        validateAdditionalFields("name", "href", "type")
    }
}

internal data class ConcreteActivityContentAudioLink(
    override val mediaType: ActivityContentAudioLink.MediaType,
    override val hash: List<ActivityContentHash>,
    override val href: String,
    override val type: String = TYPE_LINK,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentAudioLink, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        href.validate(REGEX_HREF, "Invalid href: $href")
        if (hash.isEmpty()) {
            throw ValidationException("No hash provided for link: $href")
        }
        hash.forEach { it.validate() }
        if (hash.none { it.algorithm == ALGORITHM_HASH && it.value.matches(REGEX_HASH) }) {
            throw ValidationException("At least one hash has to have been generated using $ALGORITHM_HASH")
        }
        if (mediaType is ActivityContentAudioLink.MediaType.Other && !mediaType.mimeType.startsWith(
                MIME_TYPE_AUDIO_PREFIX
            )
        ) {
            throw ValidationException("Invalid audio mime type: ${mediaType.mimeType}")
        }
        validateAdditionalFields("mediaType", "hash", "href", "type")
    }
}

internal data class ConcreteActivityContentImageLink(
    override val mediaType: ActivityContentImageLink.MediaType,
    override val hash: List<ActivityContentHash>,
    override val href: String,
    override val height: Int?,
    override val width: Int?,
    override val type: String = TYPE_LINK,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentImageLink, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        href.validate(REGEX_HREF, "Invalid href: $href")
        if (hash.isEmpty()) {
            throw ValidationException("No hash provided for link: $href")
        }
        hash.forEach { it.validate() }
        if (hash.none { it.algorithm == ALGORITHM_HASH && it.value.matches(REGEX_HASH) }) {
            throw ValidationException("At least one hash has to have been generated using $ALGORITHM_HASH")
        }
        if (mediaType is ActivityContentImageLink.MediaType.Other && !mediaType.mimeType.startsWith(
                MIME_TYPE_IMAGE_PREFIX
            )
        ) {
            throw ValidationException("Invalid image mime type: ${mediaType.mimeType}")
        }
        validateAdditionalFields("mediaType", "hash", "href", "height", "width", "type")
    }
}

internal data class ConcreteActivityContentVideoLink(
    override val mediaType: ActivityContentVideoLink.MediaType,
    override val hash: List<ActivityContentHash>,
    override val href: String,
    override val height: Int?,
    override val width: Int?,
    override val type: String = TYPE_LINK,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentVideoLink, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        href.validate(REGEX_HREF, "Invalid href: $href")
        if (hash.isEmpty()) {
            throw ValidationException("No hash provided for link: $href")
        }
        hash.forEach { it.validate() }
        if (hash.none { it.algorithm == ALGORITHM_HASH && it.value.matches(REGEX_HASH) }) {
            throw ValidationException("At least one hash has to have been generated using $ALGORITHM_HASH")
        }
        if (mediaType is ActivityContentVideoLink.MediaType.Other && !mediaType.mimeType.startsWith(
                MIME_TYPE_VIDEO_PREFIX
            )
        ) {
            throw ValidationException("Invalid video mime type: ${mediaType.mimeType}")
        }
        validateAdditionalFields("mediaType", "hash", "href", "height", "width", "type")
    }
}

internal data class ConcreteActivityContentHash(
    override val value: String,
    override val algorithm: String,
    @JsonAdapter(AdditionalFieldAdapter::class)
    override var additionalFields: Map<String, Any>? = null,
    @Transient
    override var jsonObject: JsonObject? = null
) : ActivityContentHash, ConcreteAdditionalFieldAccessor() {
    override fun validate() {
        validateAdditionalFields("value", "algorithm")
    }
}

private fun String.validate(regex: Regex, errorMessage: String) {
    if (!this.matches(regex)) {
        throw ValidationException(errorMessage)
    }
}

private fun ConcreteAdditionalFieldAccessor.validateAdditionalFields(vararg fieldNames: String) {
    val conflictingFields = additionalFields?.let {
        it.keys intersect fieldNames.toSet()
    }
    if (conflictingFields?.isEmpty() == false) {
        throw ValidationException("Some additional fields are in conflict with default fields: ${conflictingFields.joinToString()}")
    }
}