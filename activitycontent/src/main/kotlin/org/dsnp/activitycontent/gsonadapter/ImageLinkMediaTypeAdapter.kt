package org.dsnp.activitycontent.gsonadapter

import com.google.gson.*
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentImageLink
import java.lang.reflect.Type

private const val MIME_TYPE_IMAGE_JPEG = "image/jpeg"
private const val MIME_TYPE_IMAGE_PNG = "image/png"
private const val MIME_TYPE_IMAGE_SVG = "image/svg+xml"
private const val MIME_TYPE_IMAGE_WEBP = "image/webp"
private const val MIME_TYPE_IMAGE_GIF = "image/gif"
internal const val MIME_TYPE_IMAGE_PREFIX = "image/"

/**
 * Converts JSON into an [ActivityContentImageLink.MediaType] and vice versa.
 * The output will be one of [ActivityContentImageLink.MediaType.JPEG], [ActivityContentImageLink.MediaType.PNG], [ActivityContentImageLink.MediaType.SVG], [ActivityContentImageLink.MediaType.WebP], [ActivityContentImageLink.MediaType.GIF], or [ActivityContentImageLink.MediaType.Other].
*/
open class ImageLinkMediaTypeAdapter : JsonSerializer<ActivityContentImageLink.MediaType>,
    JsonDeserializer<ActivityContentImageLink.MediaType> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ActivityContentImageLink.MediaType {
        return when (val mimeType = json.asString!!) {
            MIME_TYPE_IMAGE_JPEG -> ActivityContentImageLink.MediaType.JPEG
            MIME_TYPE_IMAGE_PNG -> ActivityContentImageLink.MediaType.PNG
            MIME_TYPE_IMAGE_SVG -> ActivityContentImageLink.MediaType.SVG
            MIME_TYPE_IMAGE_WEBP -> ActivityContentImageLink.MediaType.WebP
            MIME_TYPE_IMAGE_GIF -> ActivityContentImageLink.MediaType.GIF
            else -> {
                if (mimeType.startsWith(MIME_TYPE_IMAGE_PREFIX)) {
                    ActivityContentImageLink.MediaType.Other(json.asString)
                } else {
                    throw ValidationException("Invalid image MIME type: $mimeType")
                }
            }
        }
    }

    override fun serialize(
        src: ActivityContentImageLink.MediaType,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val mimeType = when (src) {
            is ActivityContentImageLink.MediaType.JPEG -> MIME_TYPE_IMAGE_JPEG
            is ActivityContentImageLink.MediaType.PNG -> MIME_TYPE_IMAGE_PNG
            is ActivityContentImageLink.MediaType.SVG -> MIME_TYPE_IMAGE_SVG
            is ActivityContentImageLink.MediaType.WebP -> MIME_TYPE_IMAGE_WEBP
            is ActivityContentImageLink.MediaType.GIF -> MIME_TYPE_IMAGE_GIF
            is ActivityContentImageLink.MediaType.Other -> src.mimeType
        }
        return JsonPrimitive(mimeType)
    }
}