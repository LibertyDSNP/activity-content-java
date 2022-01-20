package org.dsnp.activitycontent.builder

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentAudioLink
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class MentionBuilderTests {
    @Test
    fun valid() {
        val mention = ActivityContent.Builders.Tag.mention(
            "dsnp://101"
        ).withName("test name")
            .addAdditionalField("field1", "test value")
            .addAdditionalFields(mapOf(
                "field2" to 123,
                "field3" to 1.23
            ))
            .build()

        assertThat(mention.type).isEqualTo("Mention")
        assertThat(mention.id).isEqualTo("dsnp://101")
        assertThat(mention.name).isEqualTo("test name")
        assertThat(mention.getString("field1")).isEqualTo("test value")
        assertThat(mention.getInt("field2")).isEqualTo(123)
        assertThat(mention.getDouble("field3")).isEqualTo(1.23)
    }

    @Test
    fun invalid_id() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Tag.mention(
                "101"
            ).build()
        }
        assertThat(exception.message).isEqualTo("Invalid DSNP user URI: 101")
    }

    @Test
    fun invalid_noHash() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.audio(
                ActivityContentAudioLink.MediaType.MP3,
                href = "https://www.dsnp.org/exampleAudioContent1.mp3"
            ).build()
        }
        assertThat(exception.message).isEqualTo("No hash provided for link: https://www.dsnp.org/exampleAudioContent1.mp3")
    }

    @Test
    fun invalid_hash() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.audio(
                ActivityContentAudioLink.MediaType.MP3,
                href = "https://www.dsnp.org/exampleAudioContent1.mp3"
            ).addHash("0x123", "keccak256", additionalFields = mapOf("value" to "random"))
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: value")
    }

    @Test
    fun invalid_noKeccak256Hash() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.audio(
                ActivityContentAudioLink.MediaType.MP3,
                href = "https://www.dsnp.org/exampleAudioContent1.mp3"
            ).addHash("123", "sha256")
                .build()
        }
        assertThat(exception.message).isEqualTo("At least one hash has to have been generated using keccak256")
    }

    @Test
    fun invalid_mime_type() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.audio(
                ActivityContentAudioLink.MediaType.Other("image/bmp"),
                href = "https://www.dsnp.org/exampleAudioContent1.flac"
            ).addHash("content")
                .build()
        }
        assertThat(exception.message).isEqualTo("Invalid audio mime type: image/bmp")
    }

    @Test
    fun invalid_custom_fields() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Tag.mention(
                "dsnp://101"
            ).addAdditionalField("id", "123")
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: id")
    }
}