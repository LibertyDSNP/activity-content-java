package org.dsnp.activitycontent.model

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.junit.jupiter.api.Test

class ConcreteAdditionalFieldAccessorTests {
    @Test
    fun getString_noJsonObject_noAdditionalField() {
        val jsonObject = mockk<JsonObject>()
        val jsonElement = mockk<JsonElement>()
        every { jsonObject.get("test") } returns jsonElement
        every { jsonElement.asJsonPrimitive } returns null

        val hashtag = ActivityContent.Builders.Tag.hashtag("@test").build() as ConcreteActivityContentHashtag
        hashtag.jsonObject = jsonObject

        assertThat(hashtag.getString("test")).isNull()
    }
}