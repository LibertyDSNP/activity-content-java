package org.dsnp.activitycontent.gsonadapter

import com.google.gson.*
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentVideoLink
import java.lang.reflect.Type

private const val MIME_TYPE_VIDEO_MPEG = "video/mpeg"
private const val MIME_TYPE_VIDEO_OGG = "video/ogg"
private const val MIME_TYPE_VIDEO_WEBM = "video/webm"
private const val MIME_TYPE_VIDEO_H265 = "video/H265"
private const val MIME_TYPE_VIDEO_MP4 = "video/mp4"
internal const val MIME_TYPE_VIDEO_PREFIX = "video/"

/**
 * Converts JSON into an [ActivityContentVideoLink.MediaType] and vice versa.
 * The output will be one of [ActivityContentVideoLink.MediaType.MPEG], [ActivityContentVideoLink.MediaType.OGG], [ActivityContentVideoLink.MediaType.WEBM], [ActivityContentVideoLink.MediaType.H265], [ActivityContentVideoLink.MediaType.MP4], or [ActivityContentVideoLink.MediaType.Other].
*/
open class VideoLinkMediaTypeAdapter : JsonSerializer<ActivityContentVideoLink.MediaType>,
    JsonDeserializer<ActivityContentVideoLink.MediaType> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ActivityContentVideoLink.MediaType {
        return when (val mimeType = json.asString!!) {
            MIME_TYPE_VIDEO_MPEG -> ActivityContentVideoLink.MediaType.MPEG
            MIME_TYPE_VIDEO_OGG -> ActivityContentVideoLink.MediaType.OGG
            MIME_TYPE_VIDEO_WEBM -> ActivityContentVideoLink.MediaType.WEBM
            MIME_TYPE_VIDEO_H265 -> ActivityContentVideoLink.MediaType.H265
            MIME_TYPE_VIDEO_MP4 -> ActivityContentVideoLink.MediaType.MP4
            else -> {
                if (mimeType.startsWith(MIME_TYPE_VIDEO_PREFIX)) {
                    ActivityContentVideoLink.MediaType.Other(json.asString)
                } else {
                    throw ValidationException("Invalid video MIME type: $mimeType")
                }
            }
        }
    }

    override fun serialize(
        src: ActivityContentVideoLink.MediaType,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val mimeType = when (src) {
            is ActivityContentVideoLink.MediaType.MPEG -> MIME_TYPE_VIDEO_MPEG
            is ActivityContentVideoLink.MediaType.OGG -> MIME_TYPE_VIDEO_OGG
            is ActivityContentVideoLink.MediaType.WEBM -> MIME_TYPE_VIDEO_WEBM
            is ActivityContentVideoLink.MediaType.H265 -> MIME_TYPE_VIDEO_H265
            is ActivityContentVideoLink.MediaType.MP4 -> MIME_TYPE_VIDEO_MP4
            is ActivityContentVideoLink.MediaType.Other -> src.mimeType
        }
        return JsonPrimitive(mimeType)
    }
}