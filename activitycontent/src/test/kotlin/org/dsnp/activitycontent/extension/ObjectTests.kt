package org.dsnp.activitycontent.extension

import com.google.gson.JsonElement
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.model.ConcreteActivityContentHashtag
import org.junit.jupiter.api.Test

class ObjectTests {
    @Test
    fun with_jsonElement_nonJsonObject() {
        val jsonElement = mockk<JsonElement>()
        every { jsonElement.isJsonObject } returns false
        every { jsonElement.asJsonObject } throws(IllegalStateException())

        val hashtag = ActivityContent.Builders.Tag.hashtag("#hashtag")
            .build() as ConcreteActivityContentHashtag
        hashtag.with(jsonElement)
        verify { jsonElement.isJsonObject }
        verify(exactly = 0) { jsonElement.asJsonObject }
    }
}