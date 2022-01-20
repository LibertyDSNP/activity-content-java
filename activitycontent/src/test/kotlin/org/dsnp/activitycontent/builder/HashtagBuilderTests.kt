package org.dsnp.activitycontent.builder

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class HashtagBuilderTests {
    @Test
    fun valid() {
        val mention = ActivityContent.Builders.Tag.hashtag(
            "#some_hashtag"
        ).addAdditionalField("field1", "test value")
            .addAdditionalFields(mapOf(
                "field2" to 123,
                "field3" to 1.23
            ))
            .build()

        assertThat(mention.name).isEqualTo("#some_hashtag")
        assertThat(mention.getString("field1")).isEqualTo("test value")
        assertThat(mention.getInt("field2")).isEqualTo(123)
        assertThat(mention.getDouble("field3")).isEqualTo(1.23)
    }

    @Test
    fun invalid_custom_fields() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Tag.hashtag(
                "#some_hashtag"
            ).addAdditionalField("name", "123")
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: name")
    }
}