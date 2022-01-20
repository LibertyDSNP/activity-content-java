package org.dsnp.activitycontent.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ActivityContentMediaLinkTests {
    @Test
    fun getType() {
        class ExampleActivityContentMediaLink(
            override val href: String,
            override val hash: List<ActivityContentHash> = emptyList()
        ) : ActivityContentMediaLink {
            override fun getString(fieldName: String): String? = null
            override fun getInt(fieldName: String): Int? = null
            override fun getDouble(fieldName: String): Double? = null
            override fun getBoolean(fieldName: String): Boolean? = null
            override fun <T> getObject(fieldName: String, klass: Class<T>): T? = null
            override fun <T> getList(fieldName: String, klass: Class<T>): List<T>? = null
            override fun validate() {}
        }

        val link = ExampleActivityContentMediaLink("https://test")
        assertThat(link.type).isEqualTo("Link")
    }
}