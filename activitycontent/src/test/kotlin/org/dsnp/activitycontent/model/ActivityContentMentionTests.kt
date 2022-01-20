package org.dsnp.activitycontent.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ActivityContentMentionTests {
    @Test
    fun getType() {
        class ExampleActivityContentMention(
            override val id: DSNPUserURI,
            override val name: String? = null
        ) : ActivityContentMention {
            override fun getString(fieldName: String): String? = null
            override fun getInt(fieldName: String): Int? = null
            override fun getDouble(fieldName: String): Double? = null
            override fun getBoolean(fieldName: String): Boolean? = null
            override fun <T> getObject(fieldName: String, klass: Class<T>): T? = null
            override fun <T> getList(fieldName: String, klass: Class<T>): List<T>? = null
            override fun validate() {}
        }

        val mention = ExampleActivityContentMention(
            "dsnp://101"
        )
        assertThat(mention.type).isEqualTo("Mention")
    }
}