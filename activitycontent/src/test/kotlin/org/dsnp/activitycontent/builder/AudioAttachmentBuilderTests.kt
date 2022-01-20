package org.dsnp.activitycontent.builder

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentAudioLink
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AudioAttachmentBuilderTests {
    @Test
    fun build() {
        val attachment = ActivityContent.Builders.Attachment.audio()
            .withName("test name")
            .withDuration("PT11S")
            .addUrl(
                ActivityContentAudioLink.MediaType.MP3,
                href = "http://www.dsnp.org/exampleAudioContent1.mp3",
                hash = listOf(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                additionalFields = mapOf(
                    "field1" to 123,
                    "field2" to 1.23
                )
            ).addUrl(ActivityContent.Builders.Link.audio(
                ActivityContentAudioLink.MediaType.OGG,
                href = "http://www.dsnp.org/exampleAudioContent1.ogg"
            ).addHash(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build())
                .addHash("0xabcdef2000000000000000000000000000000000000000000000000000000000", "keccak256")
                .build()
            ).addAdditionalField("field1", "234")
            .addAdditionalFields(
                mapOf(
                    "field2" to 234,
                    "field3" to 2.34
                )
            ).build()

        assertThat(attachment.type).isEqualTo("Audio")
        assertThat(attachment.name).isEqualTo("test name")
        assertThat(attachment.duration).isEqualTo("PT11S")
        assertThat(attachment.url).hasSize(2)
        assertThat(attachment.url[0].mediaType)
        assertThat(attachment.url[0].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.MP3)
        assertThat(attachment.url[0].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.mp3")
        assertThat(attachment.url[0].hash).hasSize(1)
        assertThat(attachment.url[0].hash[0].value).isEqualTo("0xabcdef1000000000000000000000000000000000000000000000000000000000")
        assertThat(attachment.url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat(attachment.url[0].getInt("field1")).isEqualTo(123)
        assertThat(attachment.url[0].getDouble("field2")).isEqualTo(1.23)
        assertThat(attachment.url[1].mediaType).isEqualTo(ActivityContentAudioLink.MediaType.OGG)
        assertThat(attachment.url[1].href).isEqualTo("http://www.dsnp.org/exampleAudioContent1.ogg")
        assertThat(attachment.url[1].hash).hasSize(2)
        assertThat(attachment.url[1].hash[0].value).isEqualTo("0xabcdef1000000000000000000000000000000000000000000000000000000000")
        assertThat(attachment.url[1].hash[0].algorithm).isEqualTo("keccak256")
        assertThat(attachment.url[1].hash[1].value).isEqualTo("0xabcdef2000000000000000000000000000000000000000000000000000000000")
        assertThat(attachment.url[1].hash[1].algorithm).isEqualTo("keccak256")
        assertThat(attachment.getString("field1")).isEqualTo("234")
        assertThat(attachment.getInt("field2")).isEqualTo(234)
        assertThat(attachment.getDouble("field3")).isEqualTo(2.34)
    }

    @Test
    fun invalid_noUrl() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Attachment.audio().build()
        }
        assertThat(exception.message).isEqualTo("No url provided for audio")
    }

    @Test
    fun invalid_url() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Attachment.audio()
                .addUrl(
                    ActivityContentAudioLink.MediaType.MP3,
                    hash = listOf(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                    href = "ftp://www.dsnp.org/exampleAudioContent1.mp3"
                ).build()
        }
        assertThat(exception.message).isEqualTo("Invalid href: ftp://www.dsnp.org/exampleAudioContent1.mp3")
    }

    @Test
    fun invalid_mimeTypes() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Attachment.audio()
                .addUrl(
                    ActivityContentAudioLink.MediaType.Other("audio/flac"),
                    hash = listOf(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                    href = "https://www.dsnp.org/exampleAudioContent1.audio"
                ).build()
        }
        assertThat(exception.message).isEqualTo("At least one url has to have a supported mime type (MP3/OGG/WebM)")
    }

    @Test
    fun invalid_duration() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Attachment.audio()
                .withDuration("11seconds")
                .addUrl(
                    ActivityContentAudioLink.MediaType.MP3,
                    href = "http://www.dsnp.org/exampleAudioContent1.mp3",
                    hash = listOf(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build())
                ).build()
        }
        assertThat(exception.message).isEqualTo("Invalid duration: 11seconds")
    }

    @Test
    fun invalid_custom_fields() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Attachment.audio()
                .addUrl(
                    ActivityContentAudioLink.MediaType.MP3,
                    href = "http://www.dsnp.org/exampleAudioContent1.mp3",
                    hash = listOf(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                ).addAdditionalField("name", "123")
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: name")
    }
}