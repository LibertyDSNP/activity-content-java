package org.dsnp.activitycontent.gsonadapter

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AdditionalFieldAdapterTests {
    @Test
    fun write() {
        val jsonWriter = mockk<JsonWriter>()
        every { jsonWriter.nullValue() } returns jsonWriter

        AdditionalFieldAdapter().write(jsonWriter, null)
        verify(exactly = 0) { jsonWriter.name(any()) }
        verify(exactly = 0) { jsonWriter.jsonValue(any()) }
    }

    @Test
    fun read() {
        val jsonReader = mockk<JsonReader>()
        assertThat(AdditionalFieldAdapter().read(jsonReader)).isNull()
    }
}