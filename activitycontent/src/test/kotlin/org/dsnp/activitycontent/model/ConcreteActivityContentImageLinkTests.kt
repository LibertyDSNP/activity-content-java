package org.dsnp.activitycontent.model

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ConcreteActivityContentImageLinkTests {
    @Test
    fun invalid_keccak256_hash() {
        val exception = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.Link.image(
                ActivityContentImageLink.MediaType.PNG,
                "https://example"
            ).addHash("invalidHashValue", "keccak256")
                .build()
        }

        assertThat(exception.message).isEqualTo("At least one hash has to have been generated using keccak256")
    }
}