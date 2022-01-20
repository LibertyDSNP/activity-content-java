package org.dsnp.activitycontent.builder

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LinkAttachmentBuilderTests {
    @Test
    fun build() {
        val attachment = ActivityContent.Builders.Attachment.link("http://www.dsnp.org/exampleContent1.html")
            .withName("test name")
            .addAdditionalField("field1", "234")
            .addAdditionalFields(
                mapOf(
                    "field2" to 234,
                    "field3" to 2.34
                )
            ).build()

        assertThat(attachment.type).isEqualTo("Link")
        assertThat(attachment.name).isEqualTo("test name")
        assertThat(attachment.href).isEqualTo("http://www.dsnp.org/exampleContent1.html")
        assertThat(attachment.getString("field1")).isEqualTo("234")
        assertThat(attachment.getInt("field2")).isEqualTo(234)
        assertThat(attachment.getDouble("field3")).isEqualTo(2.34)
    }

    @Test
    fun invalid_href() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Attachment.link("ftp://www.dsnp.org/exampleContent1.html").build()
        }
        assertThat(exception.message).isEqualTo("Invalid href: ftp://www.dsnp.org/exampleContent1.html")
    }

    @Test
    fun invalid_custom_fields() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Attachment.link("http://www.dsnp.org/exampleContent1.html")
                .addAdditionalField("name", "123")
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: name")
    }
}