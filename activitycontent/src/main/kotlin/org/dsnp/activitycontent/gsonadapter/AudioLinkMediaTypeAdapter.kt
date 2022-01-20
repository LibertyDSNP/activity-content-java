package org.dsnp.activitycontent.gsonadapter

import com.google.gson.*
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentAudioLink
import java.lang.reflect.Type

private const val MIME_TYPE_AUDIO_MP3 = "audio/mpeg"
private const val MIME_TYPE_AUDIO_OGG = "audio/ogg"
private const val MIME_TYPE_AUDIO_WEBM = "audio/webm"
internal const val MIME_TYPE_AUDIO_PREFIX = "audio/"

/**
 * Converts JSON into an [ActivityContentAudioLink.MediaType] and vice versa.
 * The output will be one of [ActivityContentAudioLink.MediaType.MP3], [ActivityContentAudioLink.MediaType.OGG], [ActivityContentAudioLink.MediaType.WebM], or [ActivityContentAudioLink.MediaType.Other].
*/
open class AudioLinkMediaTypeAdapter : JsonSerializer<ActivityContentAudioLink.MediaType>,
    JsonDeserializer<ActivityContentAudioLink.MediaType> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ActivityContentAudioLink.MediaType {
        return when (val mimeType = json.asString!!) {
            MIME_TYPE_AUDIO_MP3 -> ActivityContentAudioLink.MediaType.MP3
            MIME_TYPE_AUDIO_OGG -> ActivityContentAudioLink.MediaType.OGG
            MIME_TYPE_AUDIO_WEBM -> ActivityContentAudioLink.MediaType.WebM
            else -> {
                if (mimeType.startsWith(MIME_TYPE_AUDIO_PREFIX)) {
                    ActivityContentAudioLink.MediaType.Other(json.asString)
                } else {
                    throw ValidationException("Invalid audio MIME type: $mimeType")
                }
            }
        }
    }

    override fun serialize(
        src: ActivityContentAudioLink.MediaType,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val mimeType = when (src) {
            is ActivityContentAudioLink.MediaType.MP3 -> MIME_TYPE_AUDIO_MP3
            is ActivityContentAudioLink.MediaType.OGG -> MIME_TYPE_AUDIO_OGG
            is ActivityContentAudioLink.MediaType.WebM -> MIME_TYPE_AUDIO_WEBM
            is ActivityContentAudioLink.MediaType.Other -> src.mimeType
        }
        return JsonPrimitive(mimeType)
    }
}