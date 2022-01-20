package org.dsnp.activitycontent.builder

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentVideoLink
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class VideoLinkBuilderTests {
    @Test
    fun build() {
        val link = ActivityContent.Builders.Link.video(
            ActivityContentVideoLink.MediaType.MP4,
            href = "http://www.dsnp.org/exampleVideoContent1.mp4"
        ).withHeight(100)
            .withWidth(200)
            .addHash(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build())
            .addHash("0xabcdef2000000000000000000000000000000000000000000000000000000000", "keccak256")
            .addHash("content1")
            .addHash("content2".toByteArray())
            .addAdditionalField("field1", "test value")
            .addAdditionalFields(mapOf(
                "field2" to 123,
                "field3" to 1.23
            ))
            .build()

        assertThat(link.type).isEqualTo("Link")
        assertThat(link.mediaType).isEqualTo(ActivityContentVideoLink.MediaType.MP4)
        assertThat(link.href).isEqualTo("http://www.dsnp.org/exampleVideoContent1.mp4")
        assertThat(link.height).isEqualTo(100)
        assertThat(link.width).isEqualTo(200)
        assertThat(link.hash).hasSize(4)
        assertThat(link.hash[0].value).isEqualTo("0xabcdef1000000000000000000000000000000000000000000000000000000000")
        assertThat(link.hash[0].algorithm).isEqualTo("keccak256")
        assertThat(link.hash[1].value).isEqualTo("0xabcdef2000000000000000000000000000000000000000000000000000000000")
        assertThat(link.hash[1].algorithm).isEqualTo("keccak256")
        assertThat(link.hash[2].value).isEqualTo("0x397377778b65288382218837c69247cd1ec792d2e67be47ed45514d529e1c239")
        assertThat(link.hash[2].algorithm).isEqualTo("keccak256")
        assertThat(link.hash[3].value).isEqualTo("0x2e112640cec37046c7d1362ca75798a78b0d31a73d08359539ae1ec9b0053824")
        assertThat(link.hash[3].algorithm).isEqualTo("keccak256")
        assertThat(link.getString("field1")).isEqualTo("test value")
        assertThat(link.getInt("field2")).isEqualTo(123)
        assertThat(link.getDouble("field3")).isEqualTo(1.23)
    }


    @Test
    fun invalid_href() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.video(
                ActivityContentVideoLink.MediaType.MP4,
                href = "ftp://www.dsnp.org/exampleVideoContent1.mp4").build()
        }
        assertThat(exception.message).isEqualTo("Invalid href: ftp://www.dsnp.org/exampleVideoContent1.mp4")
    }

    @Test
    fun invalid_noHash() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.video(
                ActivityContentVideoLink.MediaType.MP4,
                href = "https://www.dsnp.org/exampleVideoContent1.mp4"
            ).build()
        }
        assertThat(exception.message).isEqualTo("No hash provided for link: https://www.dsnp.org/exampleVideoContent1.mp4")
    }

    @Test
    fun invalid_hash() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.video(
                ActivityContentVideoLink.MediaType.MP4,
                href = "https://www.dsnp.org/exampleVideoContent1.mp4"
            ).addHash("0x123", "keccak256", additionalFields = mapOf("value" to "random"))
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: value")
    }

    @Test
    fun invalid_noKeccak256Hash() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.video(
                ActivityContentVideoLink.MediaType.MP4,
                href = "https://www.dsnp.org/exampleVideoContent1.mp4"
            ).addHash("123", "sha256")
                .build()
        }
        assertThat(exception.message).isEqualTo("At least one hash has to have been generated using keccak256")
    }

    @Test
    fun invalid_mime_type() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.video(
                ActivityContentVideoLink.MediaType.Other("image/bmp"),
                href = "https://www.dsnp.org/exampleVideoContent1.flac"
            ).addHash("content")
                .build()
        }
        assertThat(exception.message).isEqualTo("Invalid video mime type: image/bmp")
    }

    @Test
    fun invalid_custom_fields() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.video(
                ActivityContentVideoLink.MediaType.MP4,
                href = "http://www.dsnp.org/exampleVideoContent1.mp4"
            ).addHash(
                ActivityContent.Builders.hash(
                    "0xabcdef1000000000000000000000000000000000000000000000000000000000",
                    "keccak256"
                ).build()
            )
                .addAdditionalField("hash", "123")
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: hash")
    }
}