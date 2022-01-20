package org.dsnp.activitycontent.builder

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.*
import org.dsnp.activitycontent.test.ExtraData
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

class NoteBuilderTests {
    @Test
    fun valid_and_json_conversion() {
        val extraData = ExtraData("value1", 123)
        val note = ActivityContent.Builders.note("test content")
            .withName("test name")
            .withPublished(Date(1000))
            .addAttachment(
                ActivityContent.Builders.Attachment.audio()
                    .addUrl(
                        mediaType = ActivityContentAudioLink.MediaType.MP3,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleAudioContent1.mp3",
                        additionalFields = mapOf(
                            "audioLinkExtraField1" to "test value",
                            "audioLinkExtraField2" to 123,
                            "audioLinkExtraField3" to 1.23,
                            "audioLinkExtraField4" to true,
                            "audioLinkExtraField5" to extraData,
                            "audioLinkExtraField6" to listOf(1,2,3)
                        )
                    ).addUrl(
                        mediaType = ActivityContentAudioLink.MediaType.OGG,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleAudioContent1.ogg"
                    ).addUrl(
                        mediaType = ActivityContentAudioLink.MediaType.WebM,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleAudioContent1.webm"
                    ).addUrl(
                        mediaType = ActivityContentAudioLink.MediaType.Other("audio/flac"),
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleAudioContent1.flac"
                    ).withName("example audio content 1")
                    .withDuration("PT11S")
                    .addAdditionalField("audioExtraField1", "test value")
                    .addAdditionalField("audioExtraField2", 123)
                    .addAdditionalField("audioExtraField3", 1.23)
                    .addAdditionalField("audioExtraField4", true)
                    .addAdditionalField("audioExtraField5", extraData)
                    .addAdditionalField("audioExtraField6", listOf(1,2,3))
                    .build()
            ).addAttachment(
                ActivityContent.Builders.Attachment.image()
                    .addUrl(
                        mediaType = ActivityContentImageLink.MediaType.JPEG,
                        listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleImageContent1.jpeg",
                        height = 100,
                        width = 200,
                        additionalFields = mapOf(
                            "imageLinkExtraField1" to "test value",
                            "imageLinkExtraField2" to 123,
                            "imageLinkExtraField3" to 1.23,
                            "imageLinkExtraField4" to true,
                            "imageLinkExtraField5" to extraData,
                            "imageLinkExtraField6" to listOf(1,2,3)
                        )
                    ).addUrl(
                        mediaType = ActivityContentImageLink.MediaType.PNG,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleImageContent1.png",
                        height = 100,
                        width = 200
                    ).addUrl(
                        mediaType = ActivityContentImageLink.MediaType.SVG,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleImageContent1.svg",
                        height = 100,
                        width = 200
                    ).addUrl(
                        mediaType = ActivityContentImageLink.MediaType.WebP,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleImageContent1.webp",
                        height = 100,
                        width = 200
                    ).addUrl(
                        mediaType = ActivityContentImageLink.MediaType.GIF,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleImageContent1.gif",
                        height = 100,
                        width = 200
                    ).addUrl(
                        mediaType = ActivityContentImageLink.MediaType.Other("image/bmp"),
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleImageContent1.bmp",
                        height = 100,
                        width = 200
                    ).withName("example image content 1")
                    .addAdditionalField("imageExtraField1", "test value")
                    .addAdditionalField("imageExtraField2", 123)
                    .addAdditionalField("imageExtraField3", 1.23)
                    .addAdditionalField("imageExtraField4", true)
                    .addAdditionalField("imageExtraField5", extraData)
                    .addAdditionalField("imageExtraField6", listOf(1,2,3))
                    .build()
            ).addAttachment(
                ActivityContent.Builders.Attachment.video()
                    .addUrl(
                        mediaType = ActivityContentVideoLink.MediaType.MPEG,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleVideoContent1.mpeg",
                        height = 100,
                        width = 200,
                        additionalFields = mapOf(
                            "videoLinkExtraField1" to "test value",
                            "videoLinkExtraField2" to 123,
                            "videoLinkExtraField3" to 1.23,
                            "videoLinkExtraField4" to true,
                            "videoLinkExtraField5" to extraData,
                            "videoLinkExtraField6" to listOf(1,2,3)
                        )
                    ).addUrl(
                        mediaType = ActivityContentVideoLink.MediaType.OGG,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleVideoContent1.ogg",
                        height = 100,
                        width = 200
                    ).addUrl(
                        mediaType = ActivityContentVideoLink.MediaType.WEBM,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleVideoContent1.webm",
                        height = 100,
                        width = 200
                    ).addUrl(
                        mediaType = ActivityContentVideoLink.MediaType.H265,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleVideoContent1.mov",
                        height = 100,
                        width = 200
                    ).addUrl(
                        mediaType = ActivityContentVideoLink.MediaType.MP4,
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleVideoContent1.mp4",
                        height = 100,
                        width = 200
                    ).addUrl(
                        mediaType = ActivityContentVideoLink.MediaType.Other("video/avi"),
                        hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                        href = "http://www.dsnp.org/exampleVideoContent1.avi",
                        height = 100,
                        width = 200
                    ).withName("example video content 1")
                    .withDuration("PT12S")
                    .addAdditionalField("videoExtraField1", "test value")
                    .addAdditionalField("videoExtraField2", 123)
                    .addAdditionalField("videoExtraField3", 1.23)
                    .addAdditionalField("videoExtraField4", true)
                    .addAdditionalField("videoExtraField5", extraData)
                    .addAdditionalField("videoExtraField6", listOf(1,2,3))
                    .build()
            ).addAttachmentAsLink(
                href = "http://www.dsnp.org/someContent.html",
                name = "some example link 1",
                additionalFields = mapOf(
                    "linkExtraField1" to "test value",
                    "linkExtraField2" to 123,
                    "linkExtraField3" to 1.23,
                    "linkExtraField4" to true,
                    "linkExtraField5" to extraData,
                    "linkExtraField6" to listOf(1,2,3)
                )
            ).addTagAsMention(
                "dsnp://101",
                "test name",
                additionalFields = mapOf(
                    "mentionExtraField1" to "test value",
                    "mentionExtraField2" to 123,
                    "mentionExtraField3" to 1.23,
                    "mentionExtraField4" to true,
                    "mentionExtraField5" to extraData,
                    "mentionExtraField6" to listOf(1,2,3)
                )
            )
            .addTagAsHashtag(
                "#hashtag",
                additionalFields = mapOf(
                    "hashtagExtraField1" to "test value",
                    "hashtagExtraField2" to 123,
                    "hashtagExtraField3" to 1.23,
                    "hashtagExtraField4" to true,
                    "hashtagExtraField5" to extraData,
                    "hashtagExtraField6" to listOf(1,2,3)
                )
            )
            .withLocation(
                name = "Test Location",
                accuracy = 1f,
                altitude = 70f,
                latitude = 40.76567f,
                longitude = -73.980835f,
                radius = 20f,
                units = ActivityContentLocation.Unit.METER,
                additionalFields = mapOf(
                    "locationExtraField1" to "test value",
                    "locationExtraField2" to 123,
                    "locationExtraField3" to 1.23,
                    "locationExtraField4" to true,
                    "locationExtraField5" to extraData,
                    "locationExtraField6" to listOf(1,2,3)
                )
            ).addAdditionalField("noteExtraField1", "test value")
            .addAdditionalField("noteExtraField2", 123)
            .addAdditionalField("noteExtraField3", 1.23)
            .addAdditionalField("noteExtraField4", true)
            .addAdditionalField("noteExtraField5", extraData)
            .addAdditionalField("noteExtraField6", listOf(1,2,3))
            .build()

        assertThat(note.context).isEqualTo("https://www.w3.org/ns/activitystreams")
        assertThat(note.type).isEqualTo("Note")
        assertThat(note.content).isEqualTo("test content")
        assertThat(note.name).isEqualTo("test name")
        assertThat(note.published).isEqualTo(Date(1000))

        // additional fields
        assertThat(note.getString("noteExtraField1")).isEqualTo("test value")
        assertThat(note.getInt("noteExtraField2")).isEqualTo(123)
        assertThat(note.getDouble("noteExtraField3")).isEqualTo(1.23)
        assertThat(note.getBoolean("noteExtraField4")).isTrue()
        assertThat(note.getObject("noteExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat(note.getObject("noteExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat(note.getList("noteExtraField6", Int::class.java)).hasSize(3)
        assertThat(note.getList("noteExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat(note.getList("noteExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat(note.getList("noteExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat(note.getObject("noteExtraField1", Date::class.java)).isNull()
        assertThat(note.getList("noteExtraField2", Any::class.java)).isNull()
        assertThat(note.getString("noteExtraField3")).isNull()
        assertThat(note.getInt("noteExtraField4")).isNull()
        assertThat(note.getDouble("noteExtraField5")).isNull()
        assertThat(note.getBoolean("noteExtraField6")).isNull()
        assertThat(note.getObject("nonExistentField", Date::class.java)).isNull()
        assertThat(note.getList("nonExistentField", Any::class.java)).isNull()
        assertThat(note.getString("nonExistentField")).isNull()
        assertThat(note.getInt("nonExistentField")).isNull()
        assertThat(note.getDouble("nonExistentField")).isNull()
        assertThat(note.getBoolean("nonExistentField")).isNull()

        // attachments
        assertThat(note.attachment).hasSize(4)
        // audio attachment
        assertThat(note.attachment!![0].type).isEqualTo("Audio")
        assertThat(note.attachment!![0].name).isEqualTo("example audio content 1")
        assertThat((note.attachment!![0] as ActivityContentAudio).duration).isEqualTo("PT11S")
        assertThat((note.attachment!![0] as ActivityContentAudio).url).hasSize(4)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.MP3)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].hash).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.mp3")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getString("audioLinkExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getInt("audioLinkExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getDouble("audioLinkExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getBoolean("audioLinkExtraField4")).isTrue()
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getObject("audioLinkExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getObject("audioLinkExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getList("audioLinkExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getList("audioLinkExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getList("audioLinkExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getList("audioLinkExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[1].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.OGG)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[1].hash).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[1].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[1].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[1].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.ogg")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[2].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.WebM)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[2].hash).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[2].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[2].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[2].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.webm")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[3].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.Other("audio/flac"))
        assertThat((note.attachment!![0] as ActivityContentAudio).url[3].hash).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[3].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[3].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[3].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.flac")
        assertThat((note.attachment!![0] as ActivityContentAudio).getString("audioExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![0] as ActivityContentAudio).getInt("audioExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![0] as ActivityContentAudio).getDouble("audioExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![0] as ActivityContentAudio).getBoolean("audioExtraField4")).isTrue()
        assertThat((note.attachment!![0] as ActivityContentAudio).getObject("audioExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![0] as ActivityContentAudio).getObject("audioExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![0] as ActivityContentAudio).getList("audioExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![0] as ActivityContentAudio).getList("audioExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).getList("audioExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![0] as ActivityContentAudio).getList("audioExtraField6", Int::class.java)!![2]).isEqualTo(3)
        // image attachment
        assertThat(note.attachment!![1].type).isEqualTo("Image")
        assertThat(note.attachment!![1].name).isEqualTo("example image content 1")
        assertThat((note.attachment!![1] as ActivityContentImage).url).hasSize(6)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].mediaType).isEqualTo(ActivityContentImageLink.MediaType.JPEG)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.jpeg")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getString("imageLinkExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getInt("imageLinkExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getDouble("imageLinkExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getBoolean("imageLinkExtraField4")).isTrue()
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getObject("imageLinkExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getObject("imageLinkExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getList("imageLinkExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getList("imageLinkExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getList("imageLinkExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getList("imageLinkExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].mediaType).isEqualTo(ActivityContentImageLink.MediaType.PNG)
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.png")
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].mediaType).isEqualTo(ActivityContentImageLink.MediaType.SVG)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.svg")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].mediaType).isEqualTo(ActivityContentImageLink.MediaType.WebP)
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.webp")
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).url[4].mediaType).isEqualTo(ActivityContentImageLink.MediaType.GIF)
        assertThat((note.attachment!![1] as ActivityContentImage).url[4].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[4].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[4].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[4].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.gif")
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].mediaType).isEqualTo(ActivityContentImageLink.MediaType.Other("image/bmp"))
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.bmp")
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).getString("imageExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![1] as ActivityContentImage).getInt("imageExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![1] as ActivityContentImage).getDouble("imageExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![1] as ActivityContentImage).getBoolean("imageExtraField4")).isTrue()
        assertThat((note.attachment!![1] as ActivityContentImage).getObject("imageExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![1] as ActivityContentImage).getObject("imageExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![1] as ActivityContentImage).getList("imageExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![1] as ActivityContentImage).getList("imageExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![1] as ActivityContentImage).getList("imageExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![1] as ActivityContentImage).getList("imageExtraField6", Int::class.java)!![2]).isEqualTo(3)
        // video attachment
        assertThat(note.attachment!![2].type).isEqualTo("Video")
        assertThat(note.attachment!![2].name).isEqualTo("example video content 1")
        assertThat((note.attachment!![2] as ActivityContentVideo).duration).isEqualTo("PT12S")
        assertThat((note.attachment!![2] as ActivityContentVideo).url).hasSize(6)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.MPEG)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.mpeg")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getString("videoLinkExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getInt("videoLinkExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getDouble("videoLinkExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getBoolean("videoLinkExtraField4")).isTrue()
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getObject("videoLinkExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getObject("videoLinkExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getList("videoLinkExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getList("videoLinkExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getList("videoLinkExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getList("videoLinkExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.OGG)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.ogg")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.WEBM)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.webm")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.H265)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.mov")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.MP4)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.mp4")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.Other("video/avi"))
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.avi")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).getString("videoExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![2] as ActivityContentVideo).getInt("videoExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![2] as ActivityContentVideo).getDouble("videoExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![2] as ActivityContentVideo).getBoolean("videoExtraField4")).isTrue()
        assertThat((note.attachment!![2] as ActivityContentVideo).getObject("videoExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![2] as ActivityContentVideo).getObject("videoExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![2] as ActivityContentVideo).getList("videoExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![2] as ActivityContentVideo).getList("videoExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).getList("videoExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![2] as ActivityContentVideo).getList("videoExtraField6", Int::class.java)!![2]).isEqualTo(3)
        // link attachment
        assertThat((note.attachment!![3] as ActivityContentLink).type).isEqualTo("Link")
        assertThat((note.attachment!![3] as ActivityContentLink).name).isEqualTo("some example link 1")
        assertThat((note.attachment!![3] as ActivityContentLink).href).isEqualTo("http://www.dsnp.org/someContent.html")
        assertThat((note.attachment!![3] as ActivityContentLink).getString("linkExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![3] as ActivityContentLink).getInt("linkExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![3] as ActivityContentLink).getDouble("linkExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![3] as ActivityContentLink).getBoolean("linkExtraField4")).isTrue()
        assertThat((note.attachment!![3] as ActivityContentLink).getObject("linkExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![3] as ActivityContentLink).getObject("linkExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![3] as ActivityContentLink).getList("linkExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![3] as ActivityContentLink).getList("linkExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![3] as ActivityContentLink).getList("linkExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![3] as ActivityContentLink).getList("linkExtraField6", Int::class.java)!![2]).isEqualTo(3)
        // tags
        assertThat(note.tag).hasSize(2)
        assertThat((note.tag!![0] as ActivityContentMention).type).isEqualTo("Mention")
        assertThat((note.tag!![0] as ActivityContentMention).id).isEqualTo("dsnp://101")
        assertThat((note.tag!![0] as ActivityContentMention).name).isEqualTo("test name")
        assertThat((note.tag!![0] as ActivityContentMention).getString("mentionExtraField1")).isEqualTo("test value")
        assertThat((note.tag!![0] as ActivityContentMention).getInt("mentionExtraField2")).isEqualTo(123)
        assertThat((note.tag!![0] as ActivityContentMention).getDouble("mentionExtraField3")).isEqualTo(1.23)
        assertThat((note.tag!![0] as ActivityContentMention).getBoolean("mentionExtraField4")).isTrue()
        assertThat((note.tag!![0] as ActivityContentMention).getObject("mentionExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.tag!![0] as ActivityContentMention).getObject("mentionExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat((note.tag!![1] as ActivityContentHashtag).name).isEqualTo("#hashtag")
        assertThat((note.tag!![1] as ActivityContentHashtag).getString("hashtagExtraField1")).isEqualTo("test value")
        assertThat((note.tag!![1] as ActivityContentHashtag).getInt("hashtagExtraField2")).isEqualTo(123)
        assertThat((note.tag!![1] as ActivityContentHashtag).getDouble("hashtagExtraField3")).isEqualTo(1.23)
        assertThat((note.tag!![1] as ActivityContentHashtag).getBoolean("hashtagExtraField4")).isTrue()
        assertThat((note.tag!![1] as ActivityContentHashtag).getObject("hashtagExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.tag!![1] as ActivityContentHashtag).getObject("hashtagExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)!![2]).isEqualTo(3)

        // location
        assertThat(note.location).isNotNull()
        assertThat(note.location!!.type).isEqualTo("Place")
        assertThat(note.location!!.name).isEqualTo("Test Location")
        assertThat(note.location!!.accuracy).isEqualTo(1f)
        assertThat(note.location!!.altitude).isEqualTo(70f)
        assertThat(note.location!!.latitude).isEqualTo(40.76567f)
        assertThat(note.location!!.longitude).isEqualTo(-73.980835f)
        assertThat(note.location!!.radius).isEqualTo(20f)
        assertThat(note.location!!.units).isEqualTo(ActivityContentLocation.Unit.METER)
        assertThat(note.location!!.getString("locationExtraField1")).isEqualTo("test value")
        assertThat(note.location!!.getInt("locationExtraField2")).isEqualTo(123)
        assertThat(note.location!!.getDouble("locationExtraField3")).isEqualTo(1.23)
        assertThat(note.location!!.getBoolean("locationExtraField4")).isTrue()
        assertThat(note.location!!.getObject("locationExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat(note.location!!.getObject("locationExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat(note.location!!.getList("locationExtraField6", Int::class.java)).hasSize(3)
        assertThat(note.location!!.getList("locationExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat(note.location!!.getList("locationExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat(note.location!!.getList("locationExtraField6", Int::class.java)!![2]).isEqualTo(3)

        val createdJson = note.toJson()
        val originalJson = this::class.java.getResource("/note_valid.json")!!.readText()

        assertThat(createdJson).isEqualTo(originalJson)
    }

    @Test
    fun valid_and_json_conversion_withAddAttachmentAsType() {
        val extraData = ExtraData("value1", 123)
        val note = ActivityContent.Builders.note("test content")
            .withName("test name")
            .withPublished(Date(1000))
            .addAttachmentAsAudio(
                url = listOf(
                    ActivityContent.Builders.Link.audio(
                        mediaType = ActivityContentAudioLink.MediaType.MP3,
                        href = "http://www.dsnp.org/exampleAudioContent1.mp3")
                        .addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .addAdditionalFields(
                            mapOf(
                                "audioLinkExtraField1" to "test value",
                                "audioLinkExtraField2" to 123,
                                "audioLinkExtraField3" to 1.23,
                                "audioLinkExtraField4" to true,
                                "audioLinkExtraField5" to extraData,
                                "audioLinkExtraField6" to listOf(1,2,3)
                            )
                        ).build(),
                    ActivityContent.Builders.Link.audio(
                        mediaType = ActivityContentAudioLink.MediaType.OGG,
                        href = "http://www.dsnp.org/exampleAudioContent1.ogg"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .build(),
                    ActivityContent.Builders.Link.audio(
                        mediaType = ActivityContentAudioLink.MediaType.WebM,
                        href = "http://www.dsnp.org/exampleAudioContent1.webm"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .build(),
                    ActivityContent.Builders.Link.audio(
                        mediaType = ActivityContentAudioLink.MediaType.Other("audio/flac"),
                        href = "http://www.dsnp.org/exampleAudioContent1.flac"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .build(),
                ),
                name = "example audio content 1",
                duration = "PT11S",
                additionalFields = mapOf(
                    "audioExtraField1" to "test value",
                    "audioExtraField2" to 123,
                    "audioExtraField3" to 1.23,
                    "audioExtraField4" to true,
                    "audioExtraField5" to extraData,
                    "audioExtraField6" to listOf(1,2,3)
                )
            ).addAttachmentAsImage(
                url = listOf(
                    ActivityContent.Builders.Link.image(
                        mediaType = ActivityContentImageLink.MediaType.JPEG,
                        href = "http://www.dsnp.org/exampleImageContent1.jpeg",
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .addAdditionalFields(mapOf(
                            "imageLinkExtraField1" to "test value",
                            "imageLinkExtraField2" to 123,
                            "imageLinkExtraField3" to 1.23,
                            "imageLinkExtraField4" to true,
                            "imageLinkExtraField5" to extraData,
                            "imageLinkExtraField6" to listOf(1,2,3)
                        )).build(),
                    ActivityContent.Builders.Link.image(
                        mediaType = ActivityContentImageLink.MediaType.PNG,
                        href = "http://www.dsnp.org/exampleImageContent1.png"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .build(),
                    ActivityContent.Builders.Link.image(
                        mediaType = ActivityContentImageLink.MediaType.SVG,
                        href = "http://www.dsnp.org/exampleImageContent1.svg"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .build(),
                    ActivityContent.Builders.Link.image(
                        mediaType = ActivityContentImageLink.MediaType.WebP,
                        href = "http://www.dsnp.org/exampleImageContent1.webp"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .build(),
                    ActivityContent.Builders.Link.image(
                        mediaType = ActivityContentImageLink.MediaType.GIF,
                        href = "http://www.dsnp.org/exampleImageContent1.gif"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .build(),
                    ActivityContent.Builders.Link.image(
                        mediaType = ActivityContentImageLink.MediaType.Other("image/bmp"),
                        href = "http://www.dsnp.org/exampleImageContent1.bmp"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .build()
                ),
                name = "example image content 1",
                additionalFields = mapOf(
                    "imageExtraField1" to "test value",
                    "imageExtraField2" to 123,
                    "imageExtraField3" to 1.23,
                    "imageExtraField4" to true,
                    "imageExtraField5" to extraData,
                    "imageExtraField6" to listOf(1,2,3)
                )
            ).addAttachmentAsVideo(
                url = listOf(
                    ActivityContent.Builders.Link.video(
                        mediaType = ActivityContentVideoLink.MediaType.MPEG,
                        href = "http://www.dsnp.org/exampleVideoContent1.mpeg"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .addAdditionalFields(mapOf(
                            "videoLinkExtraField1" to "test value",
                            "videoLinkExtraField2" to 123,
                            "videoLinkExtraField3" to 1.23,
                            "videoLinkExtraField4" to true,
                            "videoLinkExtraField5" to extraData,
                            "videoLinkExtraField6" to listOf(1,2,3)
                        )).build(),
                    ActivityContent.Builders.Link.video(
                        mediaType = ActivityContentVideoLink.MediaType.OGG,
                        href = "http://www.dsnp.org/exampleVideoContent1.ogg"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .build(),
                    ActivityContent.Builders.Link.video(
                        mediaType = ActivityContentVideoLink.MediaType.WEBM,
                        href = "http://www.dsnp.org/exampleVideoContent1.webm"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .build(),
                    ActivityContent.Builders.Link.video(
                        mediaType = ActivityContentVideoLink.MediaType.H265,
                        href = "http://www.dsnp.org/exampleVideoContent1.mov",
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .build(),
                    ActivityContent.Builders.Link.video(
                        mediaType = ActivityContentVideoLink.MediaType.MP4,
                        href = "http://www.dsnp.org/exampleVideoContent1.mp4"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .build(),
                    ActivityContent.Builders.Link.video(
                        mediaType = ActivityContentVideoLink.MediaType.Other("video/avi"),
                        href = "http://www.dsnp.org/exampleVideoContent1.avi"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .withWidth(200)
                        .build()
                ),
                name = "example video content 1",
                duration = "PT12S",
                additionalFields = mapOf(
                    "videoExtraField1" to "test value",
                    "videoExtraField2" to 123,
                    "videoExtraField3" to 1.23,
                    "videoExtraField4" to true,
                    "videoExtraField5" to extraData,
                    "videoExtraField6" to listOf(1,2,3)
                )
            ).addAttachmentAsLink(
                href = "http://www.dsnp.org/someContent.html",
                name = "some example link 1",
                additionalFields = mapOf(
                    "linkExtraField1" to "test value",
                    "linkExtraField2" to 123,
                    "linkExtraField3" to 1.23,
                    "linkExtraField4" to true,
                    "linkExtraField5" to extraData,
                    "linkExtraField6" to listOf(1,2,3)
                )
            ).addTagAsMention(
                "dsnp://101",
                "test name",
                additionalFields = mapOf(
                    "mentionExtraField1" to "test value",
                    "mentionExtraField2" to 123,
                    "mentionExtraField3" to 1.23,
                    "mentionExtraField4" to true,
                    "mentionExtraField5" to extraData,
                    "mentionExtraField6" to listOf(1,2,3)
                )
            )
            .addTagAsHashtag(
                "#hashtag",
                additionalFields = mapOf(
                    "hashtagExtraField1" to "test value",
                    "hashtagExtraField2" to 123,
                    "hashtagExtraField3" to 1.23,
                    "hashtagExtraField4" to true,
                    "hashtagExtraField5" to extraData,
                    "hashtagExtraField6" to listOf(1,2,3)
                )
            ).withLocation(
                name = "Test Location",
                accuracy = 1f,
                altitude = 70f,
                latitude = 40.76567f,
                longitude = -73.980835f,
                radius = 20f,
                units = ActivityContentLocation.Unit.METER,
                additionalFields = mapOf(
                    "locationExtraField1" to "test value",
                    "locationExtraField2" to 123,
                    "locationExtraField3" to 1.23,
                    "locationExtraField4" to true,
                    "locationExtraField5" to extraData,
                    "locationExtraField6" to listOf(1,2,3)
                )
            ).addAdditionalField("noteExtraField1", "test value")
            .addAdditionalField("noteExtraField2", 123)
            .addAdditionalField("noteExtraField3", 1.23)
            .addAdditionalField("noteExtraField4", true)
            .addAdditionalField("noteExtraField5", extraData)
            .addAdditionalField("noteExtraField6", listOf(1,2,3)).build()

        assertThat(note.context).isEqualTo("https://www.w3.org/ns/activitystreams")
        assertThat(note.type).isEqualTo("Note")
        assertThat(note.content).isEqualTo("test content")
        assertThat(note.name).isEqualTo("test name")
        assertThat(note.published).isEqualTo(Date(1000))

        // additional fields
        assertThat(note.getString("noteExtraField1")).isEqualTo("test value")
        assertThat(note.getInt("noteExtraField2")).isEqualTo(123)
        assertThat(note.getDouble("noteExtraField3")).isEqualTo(1.23)
        assertThat(note.getBoolean("noteExtraField4")).isTrue()
        assertThat(note.getObject("noteExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat(note.getObject("noteExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat(note.getList("noteExtraField6", Int::class.java)).hasSize(3)
        assertThat(note.getList("noteExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat(note.getList("noteExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat(note.getList("noteExtraField6", Int::class.java)!![2]).isEqualTo(3)

        // attachments
        assertThat(note.attachment).hasSize(4)
        // audio attachment
        assertThat(note.attachment!![0].type).isEqualTo("Audio")
        assertThat(note.attachment!![0].name).isEqualTo("example audio content 1")
        assertThat((note.attachment!![0] as ActivityContentAudio).duration).isEqualTo("PT11S")
        assertThat((note.attachment!![0] as ActivityContentAudio).url).hasSize(4)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.MP3)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].hash).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.mp3")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getString("audioLinkExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getInt("audioLinkExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getDouble("audioLinkExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getBoolean("audioLinkExtraField4")).isTrue()
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getObject("audioLinkExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getObject("audioLinkExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getList("audioLinkExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getList("audioLinkExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getList("audioLinkExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].getList("audioLinkExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[1].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.OGG)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[1].hash).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[1].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[1].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[1].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.ogg")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[2].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.WebM)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[2].hash).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[2].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[2].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[2].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.webm")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[3].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.Other("audio/flac"))
        assertThat((note.attachment!![0] as ActivityContentAudio).url[3].hash).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[3].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[3].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[3].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.flac")
        assertThat((note.attachment!![0] as ActivityContentAudio).getString("audioExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![0] as ActivityContentAudio).getInt("audioExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![0] as ActivityContentAudio).getDouble("audioExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![0] as ActivityContentAudio).getBoolean("audioExtraField4")).isTrue()
        assertThat((note.attachment!![0] as ActivityContentAudio).getObject("audioExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![0] as ActivityContentAudio).getObject("audioExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![0] as ActivityContentAudio).getList("audioExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![0] as ActivityContentAudio).getList("audioExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).getList("audioExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![0] as ActivityContentAudio).getList("audioExtraField6", Int::class.java)!![2]).isEqualTo(3)
        // image attachment
        assertThat(note.attachment!![1].type).isEqualTo("Image")
        assertThat(note.attachment!![1].name).isEqualTo("example image content 1")
        assertThat((note.attachment!![1] as ActivityContentImage).url).hasSize(6)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].mediaType).isEqualTo(ActivityContentImageLink.MediaType.JPEG)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.jpeg")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getString("imageLinkExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getInt("imageLinkExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getDouble("imageLinkExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getBoolean("imageLinkExtraField4")).isTrue()
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getObject("imageLinkExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getObject("imageLinkExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getList("imageLinkExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getList("imageLinkExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getList("imageLinkExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].getList("imageLinkExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].mediaType).isEqualTo(ActivityContentImageLink.MediaType.PNG)
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.png")
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].mediaType).isEqualTo(ActivityContentImageLink.MediaType.SVG)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.svg")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].mediaType).isEqualTo(ActivityContentImageLink.MediaType.WebP)
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.webp")
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[3].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).url[4].mediaType).isEqualTo(ActivityContentImageLink.MediaType.GIF)
        assertThat((note.attachment!![1] as ActivityContentImage).url[4].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[4].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[4].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[4].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.gif")
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].mediaType).isEqualTo(ActivityContentImageLink.MediaType.Other("image/bmp"))
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.bmp")
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[5].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).getString("imageExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![1] as ActivityContentImage).getInt("imageExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![1] as ActivityContentImage).getDouble("imageExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![1] as ActivityContentImage).getBoolean("imageExtraField4")).isTrue()
        assertThat((note.attachment!![1] as ActivityContentImage).getObject("imageExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![1] as ActivityContentImage).getObject("imageExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![1] as ActivityContentImage).getList("imageExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![1] as ActivityContentImage).getList("imageExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![1] as ActivityContentImage).getList("imageExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![1] as ActivityContentImage).getList("imageExtraField6", Int::class.java)!![2]).isEqualTo(3)
        // video attachment
        assertThat(note.attachment!![2].type).isEqualTo("Video")
        assertThat(note.attachment!![2].name).isEqualTo("example video content 1")
        assertThat((note.attachment!![2] as ActivityContentVideo).duration).isEqualTo("PT12S")
        assertThat((note.attachment!![2] as ActivityContentVideo).url).hasSize(6)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.MPEG)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.mpeg")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getString("videoLinkExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getInt("videoLinkExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getDouble("videoLinkExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getBoolean("videoLinkExtraField4")).isTrue()
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getObject("videoLinkExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getObject("videoLinkExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getList("videoLinkExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getList("videoLinkExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getList("videoLinkExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].getList("videoLinkExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.OGG)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.ogg")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.WEBM)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.webm")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.H265)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.mov")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[3].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.MP4)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.mp4")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[4].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.Other("video/avi"))
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.avi")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[5].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).getString("videoExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![2] as ActivityContentVideo).getInt("videoExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![2] as ActivityContentVideo).getDouble("videoExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![2] as ActivityContentVideo).getBoolean("videoExtraField4")).isTrue()
        assertThat((note.attachment!![2] as ActivityContentVideo).getObject("videoExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![2] as ActivityContentVideo).getObject("videoExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![2] as ActivityContentVideo).getList("videoExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![2] as ActivityContentVideo).getList("videoExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).getList("videoExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![2] as ActivityContentVideo).getList("videoExtraField6", Int::class.java)!![2]).isEqualTo(3)
        // link attachment
        assertThat((note.attachment!![3] as ActivityContentLink).type).isEqualTo("Link")
        assertThat((note.attachment!![3] as ActivityContentLink).name).isEqualTo("some example link 1")
        assertThat((note.attachment!![3] as ActivityContentLink).href).isEqualTo("http://www.dsnp.org/someContent.html")
        assertThat((note.attachment!![3] as ActivityContentLink).getString("linkExtraField1")).isEqualTo("test value")
        assertThat((note.attachment!![3] as ActivityContentLink).getInt("linkExtraField2")).isEqualTo(123)
        assertThat((note.attachment!![3] as ActivityContentLink).getDouble("linkExtraField3")).isEqualTo(1.23)
        assertThat((note.attachment!![3] as ActivityContentLink).getBoolean("linkExtraField4")).isTrue()
        assertThat((note.attachment!![3] as ActivityContentLink).getObject("linkExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.attachment!![3] as ActivityContentLink).getObject("linkExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.attachment!![3] as ActivityContentLink).getList("linkExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.attachment!![3] as ActivityContentLink).getList("linkExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.attachment!![3] as ActivityContentLink).getList("linkExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.attachment!![3] as ActivityContentLink).getList("linkExtraField6", Int::class.java)!![2]).isEqualTo(3)
        // tags
        assertThat(note.tag).hasSize(2)
        assertThat((note.tag!![0] as ActivityContentMention).type).isEqualTo("Mention")
        assertThat((note.tag!![0] as ActivityContentMention).id).isEqualTo("dsnp://101")
        assertThat((note.tag!![0] as ActivityContentMention).name).isEqualTo("test name")
        assertThat((note.tag!![0] as ActivityContentMention).getString("mentionExtraField1")).isEqualTo("test value")
        assertThat((note.tag!![0] as ActivityContentMention).getInt("mentionExtraField2")).isEqualTo(123)
        assertThat((note.tag!![0] as ActivityContentMention).getDouble("mentionExtraField3")).isEqualTo(1.23)
        assertThat((note.tag!![0] as ActivityContentMention).getBoolean("mentionExtraField4")).isTrue()
        assertThat((note.tag!![0] as ActivityContentMention).getObject("mentionExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.tag!![0] as ActivityContentMention).getObject("mentionExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat((note.tag!![1] as ActivityContentHashtag).name).isEqualTo("#hashtag")
        assertThat((note.tag!![1] as ActivityContentHashtag).getString("hashtagExtraField1")).isEqualTo("test value")
        assertThat((note.tag!![1] as ActivityContentHashtag).getInt("hashtagExtraField2")).isEqualTo(123)
        assertThat((note.tag!![1] as ActivityContentHashtag).getDouble("hashtagExtraField3")).isEqualTo(1.23)
        assertThat((note.tag!![1] as ActivityContentHashtag).getBoolean("hashtagExtraField4")).isTrue()
        assertThat((note.tag!![1] as ActivityContentHashtag).getObject("hashtagExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((note.tag!![1] as ActivityContentHashtag).getObject("hashtagExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((note.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)).hasSize(3)
        assertThat((note.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((note.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((note.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)!![2]).isEqualTo(3)

        // location
        assertThat(note.location).isNotNull()
        assertThat(note.location!!.type).isEqualTo("Place")
        assertThat(note.location!!.name).isEqualTo("Test Location")
        assertThat(note.location!!.accuracy).isEqualTo(1f)
        assertThat(note.location!!.altitude).isEqualTo(70f)
        assertThat(note.location!!.latitude).isEqualTo(40.76567f)
        assertThat(note.location!!.longitude).isEqualTo(-73.980835f)
        assertThat(note.location!!.radius).isEqualTo(20f)
        assertThat(note.location!!.units).isEqualTo(ActivityContentLocation.Unit.METER)
        assertThat(note.location!!.getString("locationExtraField1")).isEqualTo("test value")
        assertThat(note.location!!.getInt("locationExtraField2")).isEqualTo(123)
        assertThat(note.location!!.getDouble("locationExtraField3")).isEqualTo(1.23)
        assertThat(note.location!!.getBoolean("locationExtraField4")).isTrue()
        assertThat(note.location!!.getObject("locationExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat(note.location!!.getObject("locationExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat(note.location!!.getList("locationExtraField6", Int::class.java)).hasSize(3)
        assertThat(note.location!!.getList("locationExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat(note.location!!.getList("locationExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat(note.location!!.getList("locationExtraField6", Int::class.java)!![2]).isEqualTo(3)

        val createdJson = note.toJson()
        val originalJson = this::class.java.getResource("/note_valid.json")!!.readText()

        assertThat(createdJson).isEqualTo(originalJson)
    }

    @Test
    fun valid_and_json_conversion_withAddAttachmentAsType_optionalFields() {
        val note = ActivityContent.Builders.note("test content")
            .addAttachmentAsAudio(
                url = listOf(
                    ActivityContent.Builders.Link.audio(
                        mediaType = ActivityContentAudioLink.MediaType.OGG,
                        href = "http://www.dsnp.org/exampleAudioContent1.ogg"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .build()
                ),
                duration = "PT11S"
            ).addAttachmentAsImage(
                url = listOf(
                    ActivityContent.Builders.Link.image(
                        mediaType = ActivityContentImageLink.MediaType.JPEG,
                        href = "http://www.dsnp.org/exampleImageContent1.jpeg"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .build(),
                    ActivityContent.Builders.Link.image(
                        mediaType = ActivityContentImageLink.MediaType.PNG,
                        href = "http://www.dsnp.org/exampleImageContent1.png")
                        .addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withWidth(200)
                        .build(),
                    ActivityContent.Builders.Link.image(
                        mediaType = ActivityContentImageLink.MediaType.SVG,
                        href = "http://www.dsnp.org/exampleImageContent1.svg"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .build()
                )
            ).addAttachmentAsVideo(
                url = listOf(
                    ActivityContent.Builders.Link.video(
                        mediaType = ActivityContentVideoLink.MediaType.MPEG,
                        href = "http://www.dsnp.org/exampleVideoContent1.mpeg"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withHeight(100)
                        .build(),
                    ActivityContent.Builders.Link.video(
                        mediaType = ActivityContentVideoLink.MediaType.OGG,
                        href = "http://www.dsnp.org/exampleVideoContent1.ogg"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .withWidth(200)
                        .build(),
                    ActivityContent.Builders.Link.video(
                        mediaType = ActivityContentVideoLink.MediaType.WEBM,
                        href = "http://www.dsnp.org/exampleVideoContent1.webm"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .build()
                ),
                duration = "PT12S"
            ).addAttachmentAsLink(
                href = "http://www.dsnp.org/someContent.html"
            ).addTagAsMention(
                "dsnp://101"
            ).withLocation(
                name = "Test Location",
                accuracy = 1f,
                altitude = 70f,
                latitude = 40.76567f,
                longitude = -73.980835f,
                radius = 20f,
                units = ActivityContentLocation.Unit.METER
            ).build()

        assertThat(note.context).isEqualTo("https://www.w3.org/ns/activitystreams")
        assertThat(note.type).isEqualTo("Note")
        assertThat(note.content).isEqualTo("test content")
        assertThat(note.name).isNull()
        assertThat(note.published).isNull()

        // attachments
        assertThat(note.attachment).hasSize(4)
        // audio attachment
        assertThat(note.attachment!![0].type).isEqualTo("Audio")
        assertThat(note.attachment!![0].name).isNull()
        assertThat((note.attachment!![0] as ActivityContentAudio).duration).isEqualTo("PT11S")
        assertThat((note.attachment!![0] as ActivityContentAudio).url).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.OGG)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].hash).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![0] as ActivityContentAudio).url[0].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.ogg")
        // image attachment
        assertThat(note.attachment!![1].type).isEqualTo("Image")
        assertThat(note.attachment!![1].name).isNull()
        assertThat((note.attachment!![1] as ActivityContentImage).url).hasSize(3)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].mediaType).isEqualTo(ActivityContentImageLink.MediaType.JPEG)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.jpeg")
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].height).isEqualTo(100)
        assertThat((note.attachment!![1] as ActivityContentImage).url[0].width).isNull()
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].mediaType).isEqualTo(ActivityContentImageLink.MediaType.PNG)
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.png")
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].height).isNull()
        assertThat((note.attachment!![1] as ActivityContentImage).url[1].width).isEqualTo(200)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].mediaType).isEqualTo(ActivityContentImageLink.MediaType.SVG)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].hash).hasSize(1)
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.svg")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].height).isNull()
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].width).isNull()
        // video attachment
        assertThat(note.attachment!![2].type).isEqualTo("Video")
        assertThat(note.attachment!![2].name).isNull()
        assertThat((note.attachment!![2] as ActivityContentVideo).duration).isEqualTo("PT12S")
        assertThat((note.attachment!![2] as ActivityContentVideo).url).hasSize(3)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.MPEG)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.mpeg")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].height).isEqualTo(100)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[0].width).isNull()
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.OGG)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.ogg")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].height).isNull()
        assertThat((note.attachment!![2] as ActivityContentVideo).url[1].width).isEqualTo(200)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.WEBM)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].hash).hasSize(1)
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![2] as ActivityContentVideo).url[2].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.webm")
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].height).isNull()
        assertThat((note.attachment!![1] as ActivityContentImage).url[2].width).isNull()
        // link attachment
        assertThat((note.attachment!![3] as ActivityContentLink).type).isEqualTo("Link")
        assertThat((note.attachment!![3] as ActivityContentLink).name).isNull()
        assertThat((note.attachment!![3] as ActivityContentLink).href).isEqualTo("http://www.dsnp.org/someContent.html")
        // tags
        assertThat(note.tag).hasSize(1)
        assertThat((note.tag!![0] as ActivityContentMention).type).isEqualTo("Mention")
        assertThat((note.tag!![0] as ActivityContentMention).id).isEqualTo("dsnp://101")
        assertThat((note.tag!![0] as ActivityContentMention).name).isNull()

        // location
        assertThat(note.location).isNotNull()
        assertThat(note.location!!.type).isEqualTo("Place")
        assertThat(note.location!!.name).isEqualTo("Test Location")
        assertThat(note.location!!.accuracy).isEqualTo(1f)
        assertThat(note.location!!.altitude).isEqualTo(70f)
        assertThat(note.location!!.latitude).isEqualTo(40.76567f)
        assertThat(note.location!!.longitude).isEqualTo(-73.980835f)
        assertThat(note.location!!.radius).isEqualTo(20f)
        assertThat(note.location!!.units).isEqualTo(ActivityContentLocation.Unit.METER)

        val createdJson = note.toJson()
        val originalJson = this::class.java.getResource("/note_valid_reduced.json")!!.readText()

        assertThat(createdJson).isEqualTo(originalJson)
    }

    @Test
    fun valid_withLocation_noUnits() {
        val note = ActivityContent.Builders.note("test content")
            .withLocation(
                name = "Test Location",
                accuracy = 1f,
                altitude = 70f,
                latitude = 40.76567f,
                longitude = -73.980835f,
                radius = 20f
            ).build()

        assertThat(note.content).isEqualTo("test content")
        assertThat(note.location).isNotNull()
        assertThat(note.location!!.type).isEqualTo("Place")
        assertThat(note.location!!.name).isEqualTo("Test Location")
        assertThat(note.location!!.accuracy).isEqualTo(1f)
        assertThat(note.location!!.altitude).isEqualTo(70f)
        assertThat(note.location!!.latitude).isEqualTo(40.76567f)
        assertThat(note.location!!.longitude).isEqualTo(-73.980835f)
        assertThat(note.location!!.radius).isEqualTo(20f)
        assertThat(note.location!!.units).isNull()
    }

    @Test
    fun valid_withLocation_noRadius() {
        val note = ActivityContent.Builders.note("test content")
            .withLocation(
                name = "Test Location",
                accuracy = 1f,
                altitude = 70f,
                latitude = 40.76567f,
                longitude = -73.980835f
            ).build()

        assertThat(note.content).isEqualTo("test content")
        assertThat(note.location).isNotNull()
        assertThat(note.location!!.type).isEqualTo("Place")
        assertThat(note.location!!.name).isEqualTo("Test Location")
        assertThat(note.location!!.accuracy).isEqualTo(1f)
        assertThat(note.location!!.altitude).isEqualTo(70f)
        assertThat(note.location!!.latitude).isEqualTo(40.76567f)
        assertThat(note.location!!.longitude).isEqualTo(-73.980835f)
        assertThat(note.location!!.radius).isNull()
        assertThat(note.location!!.units).isNull()
    }

    @Test
    fun valid_withLocation_noLongitude() {
        val note = ActivityContent.Builders.note("test content")
            .withLocation(
                name = "Test Location",
                accuracy = 1f,
                altitude = 70f,
                latitude = 40.76567f
            ).build()

        assertThat(note.content).isEqualTo("test content")
        assertThat(note.location).isNotNull()
        assertThat(note.location!!.type).isEqualTo("Place")
        assertThat(note.location!!.name).isEqualTo("Test Location")
        assertThat(note.location!!.accuracy).isEqualTo(1f)
        assertThat(note.location!!.altitude).isEqualTo(70f)
        assertThat(note.location!!.latitude).isEqualTo(40.76567f)
        assertThat(note.location!!.longitude).isNull()
        assertThat(note.location!!.radius).isNull()
        assertThat(note.location!!.units).isNull()
    }

    @Test
    fun valid_withLocation_noLatitude() {
        val note = ActivityContent.Builders.note("test content")
            .withLocation(
                name = "Test Location",
                accuracy = 1f,
                altitude = 70f
            ).build()

        assertThat(note.content).isEqualTo("test content")
        assertThat(note.location).isNotNull()
        assertThat(note.location!!.type).isEqualTo("Place")
        assertThat(note.location!!.name).isEqualTo("Test Location")
        assertThat(note.location!!.accuracy).isEqualTo(1f)
        assertThat(note.location!!.altitude).isEqualTo(70f)
        assertThat(note.location!!.latitude).isNull()
        assertThat(note.location!!.longitude).isNull()
        assertThat(note.location!!.radius).isNull()
        assertThat(note.location!!.units).isNull()
    }

    @Test
    fun valid_withLocation_noAltitude() {
        val note = ActivityContent.Builders.note("test content")
            .withLocation(
                name = "Test Location",
                accuracy = 1f
            ).build()

        assertThat(note.content).isEqualTo("test content")
        assertThat(note.location).isNotNull()
        assertThat(note.location!!.type).isEqualTo("Place")
        assertThat(note.location!!.name).isEqualTo("Test Location")
        assertThat(note.location!!.accuracy).isEqualTo(1f)
        assertThat(note.location!!.altitude).isNull()
        assertThat(note.location!!.latitude).isNull()
        assertThat(note.location!!.longitude).isNull()
        assertThat(note.location!!.radius).isNull()
        assertThat(note.location!!.units).isNull()
    }

    @Test
    fun valid_withLocation_noAccuracy() {
        val note = ActivityContent.Builders.note("test content")
            .withLocation(
                name = "Test Location"
            ).build()

        assertThat(note.content).isEqualTo("test content")
        assertThat(note.location).isNotNull()
        assertThat(note.location!!.type).isEqualTo("Place")
        assertThat(note.location!!.name).isEqualTo("Test Location")
        assertThat(note.location!!.accuracy).isNull()
        assertThat(note.location!!.altitude).isNull()
        assertThat(note.location!!.latitude).isNull()
        assertThat(note.location!!.longitude).isNull()
        assertThat(note.location!!.radius).isNull()
        assertThat(note.location!!.units).isNull()
    }

    @Test
    fun invalidLocation() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.note("test content")
                .withLocation(
                    name = "Test Location",
                    accuracy = 1f,
                    altitude = 70f,
                    latitude = 40.76567f,
                    longitude = -73.980835f,
                    radius = 20f,
                    units = ActivityContentLocation.Unit.METER,
                    additionalFields = mapOf("name" to "invalid field")
                ).build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: name")
    }

    @Test
    fun invalidTag() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.note("test content")
                .addTagAsMention("invalidId", "test name")
                .build()
        }
        assertThat(exception.message).isEqualTo("Invalid DSNP user URI: invalidId")
    }

    @Test
    fun invalidAttachment() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.note("test content")
                .addAttachment(
                    ActivityContent.Builders.Attachment.audio()
                        .addUrl(
                            mediaType = ActivityContentAudioLink.MediaType.Other("audio/flac"),
                            hash = listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                            href = "http://www.dsnp.org/exampleAudioContent1.flac"
                        ).build()
                ).build()
        }
        assertThat(exception.message).isEqualTo("At least one url has to have a supported mime type (MP3/OGG/WebM)")
    }

    @Test
    fun invalidAdditionalField() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.note("test content")
                .addAdditionalField("name", "invalid field")
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: name")
    }

    @Test
    fun addTag() {
        val note = ActivityContent.Builders.note("test content")
            .addTag(ActivityContent.Builders.Tag.hashtag("#hashtag").build())
            .build()

        assertThat(note.tag).hasSize(1)
        assertThat((note.tag!![0] as ActivityContentHashtag).name).isEqualTo("#hashtag")
    }

    @Test
    fun withLocation() {
        val note = ActivityContent.Builders.note("test content")
            .withLocation(ActivityContent.Builders.location("Test Location")
                .withAccuracy(1f)
                .withAltitude(70f)
                .withLatitude(40.76567f)
                .withLongitude(-73.980835f)
                .withRadius(20f)
                .withUnits(ActivityContentLocation.Unit.METER)
                .addAdditionalFields(mapOf(
                    "locationExtraField1" to "test value",
                    "locationExtraField2" to 123
                )).build()
            ).build()

        assertThat(note.location).isNotNull()
        assertThat(note.location!!.type).isEqualTo("Place")
        assertThat(note.location!!.name).isEqualTo("Test Location")
        assertThat(note.location!!.accuracy).isEqualTo(1f)
        assertThat(note.location!!.altitude).isEqualTo(70f)
        assertThat(note.location!!.latitude).isEqualTo(40.76567f)
        assertThat(note.location!!.longitude).isEqualTo(-73.980835f)
        assertThat(note.location!!.radius).isEqualTo(20f)
        assertThat(note.location!!.units).isEqualTo(ActivityContentLocation.Unit.METER)
        assertThat(note.location!!.getString("locationExtraField1")).isEqualTo("test value")
        assertThat(note.location!!.getInt("locationExtraField2")).isEqualTo(123)
    }
    
    @Test
    fun addAdditionalFields() {
        val note = ActivityContent.Builders.note("test content")
            .addAdditionalFields(mapOf(
                "locationExtraField1" to "test value",
                "locationExtraField2" to 123
            )).build()

        assertThat(note.getString("locationExtraField1")).isEqualTo("test value")
        assertThat(note.getInt("locationExtraField2")).isEqualTo(123)
    }

    @Test
    fun addAttachmentAsVideo_noDuration() {
        val note = ActivityContent.Builders.note("test content")
            .addAttachmentAsVideo(
            url = listOf(
                ActivityContent.Builders.Link.video(
                    mediaType = ActivityContentVideoLink.MediaType.MPEG,
                    href = "http://www.dsnp.org/exampleVideoContent1.mpeg"
                ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                    .build()
            )
        ).build()

        assertThat(note.attachment).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentVideo).url).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentVideo).url[0].mediaType).isEqualTo(ActivityContentVideoLink.MediaType.MPEG)
        assertThat((note.attachment!![0] as ActivityContentVideo).url[0].href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.mpeg")
        assertThat((note.attachment!![0] as ActivityContentVideo).url[0].hash).hasSize(1)
        assertThat((note.attachment!![0] as ActivityContentVideo).url[0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat((note.attachment!![0] as ActivityContentVideo).url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat((note.attachment!![0] as ActivityContentVideo).duration).isNull()
    }

    @Test
    fun addTagAsHashtag_noAdditionalFields() {
        val note = ActivityContent.Builders.note("test content")
            .addTagAsHashtag("#hashtag")
            .build()

        assertThat(note.tag).hasSize(1)
        assertThat((note.tag!![0] as ActivityContentHashtag).name).isEqualTo("#hashtag")
    }
}