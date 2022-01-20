package org.dsnp.activitycontent.builder

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentImageLink
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ImageAttachmentBuilderTests {
    @Test
    fun build() {
        val attachment = ActivityContent.Builders.Attachment.image()
            .withName("test name")
            .addUrl(
                ActivityContentImageLink.MediaType.PNG,
                href = "http://www.dsnp.org/exampleImageContent1.png",
                hash = listOf(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                additionalFields = mapOf(
                    "field1" to 123,
                    "field2" to 1.23
                )
            ).addUrl(ActivityContent.Builders.Link.image(
                ActivityContentImageLink.MediaType.JPEG,
                href = "http://www.dsnp.org/exampleImageContent1.jpeg"
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

        assertThat(attachment.type).isEqualTo("Image")
        assertThat(attachment.name).isEqualTo("test name")
        assertThat(attachment.url).hasSize(2)
        assertThat(attachment.url[0].mediaType)
        assertThat(attachment.url[0].mediaType).isEqualTo(ActivityContentImageLink.MediaType.PNG)
        assertThat(attachment.url[0].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.png")
        assertThat(attachment.url[0].hash).hasSize(1)
        assertThat(attachment.url[0].hash[0].value).isEqualTo("0xabcdef1000000000000000000000000000000000000000000000000000000000")
        assertThat(attachment.url[0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat(attachment.url[0].getInt("field1")).isEqualTo(123)
        assertThat(attachment.url[0].getDouble("field2")).isEqualTo(1.23)
        assertThat(attachment.url[1].mediaType).isEqualTo(ActivityContentImageLink.MediaType.JPEG)
        assertThat(attachment.url[1].href).isEqualTo("http://www.dsnp.org/exampleImageContent1.jpeg")
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
            ActivityContent.Builders.Attachment.image().build()
        }
        assertThat(exception.message).isEqualTo("No url provided for image")
    }

    @Test
    fun invalid_url() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Attachment.image()
                .addUrl(
                    ActivityContentImageLink.MediaType.PNG,
                    hash = listOf(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                    href = "ftp://www.dsnp.org/exampleImageContent1.png"
                ).build()
        }
        assertThat(exception.message).isEqualTo("Invalid href: ftp://www.dsnp.org/exampleImageContent1.png")
    }

    @Test
    fun invalid_mimeTypes() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Attachment.image()
                .addUrl(
                    ActivityContentImageLink.MediaType.Other("image/flac"),
                    hash = listOf(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                    href = "https://www.dsnp.org/exampleImageContent1.image"
                ).build()
        }
        assertThat(exception.message).isEqualTo("At least one url has to have a supported mime type (GIF/SVG/PNG/WebP/JPEG)")
    }

    @Test
    fun invalid_custom_fields() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Attachment.image()
                .addUrl(
                    ActivityContentImageLink.MediaType.PNG,
                    href = "http://www.dsnp.org/exampleImageContent1.png",
                    hash = listOf(ActivityContent.Builders.hash("0xabcdef1000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                ).addAdditionalField("name", "123")
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: name")
    }
}