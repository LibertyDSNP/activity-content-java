package org.dsnp.activitycontent.model

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.junit.jupiter.api.Test

class ConcreteActivityContentNoteTests {
    @Test
    fun toJson_noGson() {
        val json = ActivityContent.Builders.note("test content")
            .build()
            .toJson(null)

        assertThat(json).isNotNull()
    }
}