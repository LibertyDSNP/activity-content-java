package org.dsnp.activitycontent

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.dsnp.activitycontent.builder.*
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.extension.with
import org.dsnp.activitycontent.gsonadapter.*
import org.dsnp.activitycontent.model.*
import java.util.*

/**
 * This class supports creating and reading Activity Content as it is defined in the [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview).
 */
class ActivityContent private constructor() {
    companion object {
        internal var outputGson: Gson = Gson()

        /**
         * Parses a JSON string and converts it to an [ActivityContentNote] object.
         * IMPORTANT: If you want to use additional fields, you need to enable support for that by passing in true for the parameter [supportAdditionalFields].
         * An additional field is a JSON field that is not part of the default DSNP Activity Content definition (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview)).
         * By default, additional field support is turned OFF in order to reduce memory usage.
         *
         * @param json String containing the JSON that will be parsed and converted to an [ActivityContentNote] object.
         * @param supportAdditionalFields True if you want to support additional fields, false, otherwise.
         * @param gson [Gson] instance used to convert JSON into an [ActivityContentNote].
         * @return The parsed [ActivityContentNote].
         */
        @JvmStatic
        @JvmOverloads
        @Throws(ValidationException::class)
        fun parseNote(
            json: String,
            supportAdditionalFields: Boolean = false,
            gson: Gson = buildInputGson(supportAdditionalFields)
        ): ActivityContentNote {
            val jsonElement = gson.fromJson(json, JsonObject::class.java)
            try {
                return gson
                    .fromJson(jsonElement, ConcreteActivityContentNote::class.java)
                    .apply {
                        if (supportAdditionalFields) {
                            with(jsonElement)
                        }
                    }
            } catch (t: Throwable) {
                throw ValidationException("Invalid json", t)
            }
        }

        /**
         * Parses a JSON string and converts it to an [ActivityContentProfile] object.
         * IMPORTANT: If you want to use additional fields, you need to enable support for that by passing in true for the parameter [supportAdditionalFields].
         * An additional field is a JSON field that is not part of the default DSNP Activity Content definition (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview)).
         * By default, additional field support is turned OFF in order to reduce memory usage.
         *
         * @param json String containing the JSON that will be parsed and converted to an [ActivityContentProfile] object.
         * @param supportAdditionalFields True if you want to support additional fields, false, otherwise.
         * @param gson [Gson] instance used to convert JSON into an [ActivityContentProfile].
         * @return The parsed [ActivityContentProfile].
         */
        @JvmStatic
        @JvmOverloads
        @Throws(ValidationException::class)
        fun parseProfile(
            json: String,
            supportAdditionalFields: Boolean = false,
            gson: Gson = buildInputGson(supportAdditionalFields)
        ): ActivityContentProfile {
            val jsonElement = gson.fromJson(json, JsonObject::class.java)
            try {
                return gson
                    .fromJson(json, ConcreteActivityContentProfile::class.java)
                    .apply {
                        if (supportAdditionalFields) {
                            with(jsonElement)
                        }
                    }
            } catch (t: Throwable) {
                throw ValidationException("Invalid json", t)
            }
        }

        /**
         * Creates a [Gson] object that can be used to write JSON. The returned [Gson] object has all the required type adapters (i.e. [com.google.gson.TypeAdapter], [com.google.gson.InstanceCreator], [com.google.gson.JsonSerializer], or [com.google.gson.JsonDeserializer]) registered that are necessary to write Activity Content objects.
         * If you want to register your own type adapters or customize [Gson] in other ways, you can pass in your own [Gson]. The type adapters from this SDK will be added in addition.
         *
         * @param gson Type adapters necessary for writing Activity Content objects will be registered using this [Gson] instance.
         * @return [Gson] object with all necessary type adapters to convert Activity Content objects into JSON. If a [GsonBuilder] was passed in, it will be used to create the [Gson] object.
         */
        fun buildOutputGson(gson: Gson? = null): Gson {
            return (gson?.newBuilder() ?: GsonBuilder())
                .registerTypeAdapter(Date::class.java, DateAdapter())
                .registerTypeAdapter(ActivityContentAudioLink.MediaType::class.java, AudioLinkMediaTypeAdapter())
                .registerTypeAdapter(ActivityContentImageLink.MediaType::class.java, ImageLinkMediaTypeAdapter())
                .registerTypeAdapter(ActivityContentVideoLink.MediaType::class.java, VideoLinkMediaTypeAdapter())
                .create().apply {
                    outputGson = this
                }
        }

        /**
         * Creates a [Gson] object that can be used to read JSON. The returned [Gson] object has all the required type adapters (i.e. [com.google.gson.TypeAdapter], [com.google.gson.InstanceCreator], [com.google.gson.JsonSerializer], or [com.google.gson.JsonDeserializer]) registered that are necessary to read Activity Content objects from JSON.
         * If you want to register your own type adapters or customize [Gson] in other ways, you can pass in your own [Gson]. The type adapters from this SDK will be added in addition.
         * IMPORTANT: If you want to use additional fields, you need to enable support for that by passing in true for the parameter [supportAdditionalFields].
         * An additional field is a JSON field that is not part of the default DSNP Activity Content definition (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview)).
         * By default, additional field support is turned OFF in order to reduce memory usage.
         *
         *
         * @param supportAdditionalFields True if you want to use additional fields (i.e. JSON fields that are not part of the default DSNP Activity Content definition (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview),) false otherwise.
         * @param gson Type adapters necessary for writing Activity Content objects will be registered using this [Gson] instance.
         * @return [Gson] object with all necessary type adapters to convert JSON into Activity Content objects. If a [GsonBuilder] was passed in, it will be used to create the [Gson] object.
         */
        fun buildInputGson(supportAdditionalFields: Boolean, gson: Gson? = null): Gson {
            class GsonFactory(var gson: Gson)

            val gsonFactory = GsonFactory(Gson())
            val finalGson = (gson?.newBuilder() ?: GsonBuilder())
                .registerTypeAdapter(
                    Date::class.java,
                    DateAdapter()
                )
                .registerTypeAdapter(
                    ActivityContentLocation::class.java,
                    LocationDeserializer(supportAdditionalFields) { gsonFactory.gson })
                .registerTypeAdapter(
                    ActivityContentTag::class.java,
                    TagDeserializer(supportAdditionalFields) { gsonFactory.gson })
                .registerTypeAdapter(
                    ActivityContentAttachment::class.java,
                    AttachmentDeserializer(supportAdditionalFields) { gsonFactory.gson })
                .registerTypeAdapter(
                    ActivityContentLink::class.java,
                    LinkDeserializer(supportAdditionalFields) { gsonFactory.gson })
                .registerTypeAdapter(
                    ActivityContentHash::class.java,
                    HashDeserializer(supportAdditionalFields) { gsonFactory.gson })
                .registerTypeAdapter(
                    ActivityContentAudioLink::class.java,
                    AudioLinkDeserializer(supportAdditionalFields) { gsonFactory.gson })
                .registerTypeAdapter(ActivityContentAudioLink.MediaType::class.java, AudioLinkMediaTypeAdapter())
                .registerTypeAdapter(
                    ActivityContentImageLink::class.java,
                    ImageLinkDeserializer(supportAdditionalFields) { gsonFactory.gson })
                .registerTypeAdapter(ActivityContentImageLink.MediaType::class.java, ImageLinkMediaTypeAdapter())
                .registerTypeAdapter(
                    ActivityContentVideoLink::class.java,
                    VideoLinkDeserializer(supportAdditionalFields) { gsonFactory.gson })
                .registerTypeAdapter(ActivityContentVideoLink.MediaType::class.java, VideoLinkMediaTypeAdapter())
                .create()

            gsonFactory.gson = finalGson
            return finalGson
        }
    }

    /**
     * Namespace for Builders.
     *
     */
    class Builders private constructor() {
        companion object {
            /**
             * Creates and returns a [ProfileBuilder] that allows you to build [ActivityContentProfile] instances.
             *
             * @return A [ProfileBuilder] instance.
             */
            @JvmStatic
            fun profile() = ProfileBuilder()

            /**
             * Creates and returns a [NoteBuilder] that allows you to build [ActivityContentNote] instances.
             *
             * @param content The required content of the note.
             * @return A [NoteBuilder] instance.
             */
            @JvmStatic
            fun note(content: String) = NoteBuilder(content)

            /**
             * Creates and returns a [HashBuilder] that allows you to build [ActivityContentHash] instances.
             *
             * @param content The content will be automatically hashed by the [HashBuilder] using the appropriate hash algorithm (keccak256).
             * @return A [HashBuilder] instance.
             */
            @JvmStatic
            fun hash(content: String) = HashBuilder(content)

            /**
             * Creates and returns a [HashBuilder] that allows you to build [ActivityContentHash] instances.
             *
             * @param content The content will be automatically hashed by the [HashBuilder] using the appropriate hash algorithm (keccak256).
             * @return A [HashBuilder] instance.
             */
            @JvmStatic
            fun hash(content: ByteArray) = HashBuilder(content)

            /**
             * Creates and returns a [HashBuilder] that allows you to build [ActivityContentHash] instances.
             *
             * @param value A hash value that will be saved in the [ActivityContentHash]. IMPORTANT: This parameter takes a hash as a value, NOT content that will be hashed by the [HashBuilder].
             * @param algorithm The name of the algorithm that was used to generate the hash [value].
             * @return A [HashBuilder] instance.
             */
            @JvmStatic
            fun hash(value: String, algorithm: String) = HashBuilder(value, algorithm)

            /**
             * Creates and returns a [LocationBuilder] that allows you to build [ActivityContentLocation] instances.
             *
             * @param name The required name of the location.
             * @return A [LocationBuilder] instance.
             */
            @JvmStatic
            fun location(name: String) = LocationBuilder(name)
        }

        /**
         * Namespace for Attachment builders.
         * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments) for details.
         *
         */
        class Attachment private constructor() {
            companion object {
                /**
                 * Creates and returns an [AudioAttachmentBuilder] that allows you to build [ActivityContentAudio] instances.
                 *
                 * @return An [AudioAttachmentBuilder] instance.
                 */
                @JvmStatic
                fun audio() = AudioAttachmentBuilder()

                /**
                 * Creates and returns a [ImageAttachmentBuilder] that allows you to build [ActivityContentImage] instances.
                 *
                 * @return An [ImageAttachmentBuilder] instance.
                 */
                @JvmStatic
                fun image() = ImageAttachmentBuilder()

                /**
                 * Creates and returns a [VideoAttachmentBuilder] that allows you to build [ActivityContentVideo] instances.
                 *
                 * @return A [VideoAttachmentBuilder] instance.
                 */
                @JvmStatic
                fun video() = VideoAttachmentBuilder()

                /**
                 * Creates and returns a [LinkAttachmentBuilder] that allows you to build [ActivityContentLink] instances.
                 *
                 * @param href The URL of the link. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
                 * @return A [LinkAttachmentBuilder] instance.
                 */
                @JvmStatic
                fun link(href: String) = LinkAttachmentBuilder(href)
            }
        }

        /**
         * Namespace for attachment links
         * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments) for details.
         */
        class Link private constructor() {
            companion object {
                /**
                 * Creates and returns an [AudioLinkBuilder] that allows you to build [ActivityContentAudioLink] instances.
                 *
                 * @param mediaType The [ActivityContentAudioLink.MediaType] of the [ActivityContentAudioLink]. IMPORTANT: At least one [ActivityContentAudioLink] of the [ActivityContentAudio] object has to have a supported MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-audio-mime-types) for details).
                 * @param href The URL of the content. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
                 * @return An [AudioLinkBuilder] instance.
                 */
                @JvmStatic
                fun audio(mediaType: ActivityContentAudioLink.MediaType, href: String) =
                    AudioLinkBuilder(mediaType, href)

                /**
                 * Creates and returns an [ImageLinkBuilder] that allows you to build [ActivityContentImageLink] instances.
                 *
                 * @param mediaType The [ActivityContentImageLink.MediaType] of the [ActivityContentImageLink]. IMPORTANT: At least one [ActivityContentImageLink] of the [ActivityContentImage] object has to have a supported MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-image-mime-types) for details).
                 * @param href The URL of the content. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
                 * @return An [ImageLinkBuilder] instance.
                 */
                @JvmStatic
                fun image(mediaType: ActivityContentImageLink.MediaType, href: String) =
                    ImageLinkBuilder(mediaType, href)

                /**
                 * Creates and returns a [VideoLinkBuilder] that allows you to build [ActivityContentVideoLink] instances.
                 *
                 * @param mediaType The [ActivityContentVideoLink.MediaType] of the [ActivityContentVideoLink]. IMPORTANT: At least one [ActivityContentVideoLink] of the [ActivityContentVideo] object has to have a supported MIME type (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#supported-video-mime-types) for details).
                 * @param href The URL of the content. The URL MUST follow a supported URL schema (see [DSNP Spec](https://spec.dsnp.org/ActivityContent/Overview#supported-url-schema)).
                 * @return A [VideoLinkBuilder] instance.
                 */
                @JvmStatic
                fun video(mediaType: ActivityContentVideoLink.MediaType, href: String) =
                    VideoLinkBuilder(mediaType, href)
            }
        }

        /**
         * Namespace for tags.
         * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Tag) for details.
         *
         */
        class Tag private constructor() {
            companion object {
                /**
                 * Creates and returns a [MentionBuilder] that allows you to build [ActivityContentMention] instances.
                 *
                 * @param id A DSNPUserURI starting with "dsnp://". See [DSNP Spec](https://spec.dsnp.org/Identifiers#dsnp-user-uri) for details.
                 * @return A [MentionBuilder] instance.
                 */
                @JvmStatic
                fun mention(id: DSNPUserURI) = MentionBuilder(id)

                /**
                 * Creates and returns a [HashtagBuilder] that allows you to build [ActivityContentHashtag] instances.
                 *
                 * @param name The name of the hashtag.
                 * @return A [HashtagBuilder] instance.
                 */
                @JvmStatic
                fun hashtag(name: String) = HashtagBuilder(name)
            }
        }
    }
}