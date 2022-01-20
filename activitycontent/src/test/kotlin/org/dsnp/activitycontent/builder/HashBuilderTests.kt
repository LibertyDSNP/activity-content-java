package org.dsnp.activitycontent.builder

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class HashBuilderTests {
    @Test
    fun content_as_string_valid() {
        val hash = ActivityContent.Builders.hash(
            "test content"
        ).addAdditionalField("field1", "test value")
            .addAdditionalFields(mapOf(
                "field2" to 123,
                "field3" to 1.23
            ))
            .build()

        assertThat(hash.value).isEqualTo("0x8012a18ea9e4271bd7f2d42009d06492351cc0b4cbc1865ab6aec0f6c200cfe8")
        assertThat(hash.algorithm).isEqualTo("keccak256")
        assertThat(hash.getString("field1")).isEqualTo("test value")
        assertThat(hash.getInt("field2")).isEqualTo(123)
        assertThat(hash.getDouble("field3")).isEqualTo(1.23)
    }

    @Test
    fun content_as_byte_array_valid() {
        val hash = ActivityContent.Builders.hash(
            "test content".toByteArray()
        ).addAdditionalField("field1", "test value")
            .addAdditionalFields(mapOf(
                "field2" to 123,
                "field3" to 1.23
            ))
            .build()

        assertThat(hash.value).isEqualTo("0x8012a18ea9e4271bd7f2d42009d06492351cc0b4cbc1865ab6aec0f6c200cfe8")
        assertThat(hash.algorithm).isEqualTo("keccak256")
        assertThat(hash.getString("field1")).isEqualTo("test value")
        assertThat(hash.getInt("field2")).isEqualTo(123)
        assertThat(hash.getDouble("field3")).isEqualTo(1.23)
    }

    @Test
    fun value_valid() {
        val hash = ActivityContent.Builders.hash(
            "someHash", "sha256"
        ).addAdditionalField("field1", "test value")
            .addAdditionalFields(mapOf(
                "field2" to 123,
                "field3" to 1.23
            ))
            .build()

        assertThat(hash.value).isEqualTo("someHash")
        assertThat(hash.algorithm).isEqualTo("sha256")
        assertThat(hash.getString("field1")).isEqualTo("test value")
        assertThat(hash.getInt("field2")).isEqualTo(123)
        assertThat(hash.getDouble("field3")).isEqualTo(1.23)
    }

    @Test
    fun invalid_custom_fields() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.hash(
                "test content"
            ).addAdditionalField("value", "123")
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: value")
    }
}