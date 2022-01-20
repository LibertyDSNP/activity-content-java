package org.dsnp.activitycontent.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ActivityContentImageTests {
    @Test
    fun getType() {
        class ExampleActivityContentImage(
            override val name: String? = null,
            override val url: List<ActivityContentImageLink> = emptyList()
        ) : ActivityContentImage {
            override fun getString(fieldName: String): String? = null
            override fun getInt(fieldName: String): Int? = null
            override fun getDouble(fieldName: String): Double? = null
            override fun getBoolean(fieldName: String): Boolean? = null
            override fun <T> getObject(fieldName: String, klass: Class<T>): T? = null
            override fun <T> getList(fieldName: String, klass: Class<T>): List<T>? = null
            override fun validate() {}
        }

        val image = ExampleActivityContentImage()
        assertThat(image.type).isEqualTo("Image")
    }
}