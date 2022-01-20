package org.dsnp.activitycontent.model

import com.google.gson.Gson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class ActivityContentProfileTests {
    @Test
    fun getType() {
        class ExampleActivityContentProfile(
            override val context: String,
            override val name: String? = null,
            override val published: Date? = null,
            override val location: ActivityContentLocation? = null,
            override val tag: List<ActivityContentTag>? = null,
            override val summary: String? = null,
            override val icon: List<ActivityContentImageLink>? = null
        ) : ActivityContentProfile {
            override fun toJson(gson: Gson?): String = ""
            override fun getString(fieldName: String): String? = null
            override fun getInt(fieldName: String): Int? = null
            override fun getDouble(fieldName: String): Double? = null
            override fun getBoolean(fieldName: String): Boolean? = null
            override fun <T> getObject(fieldName: String, klass: Class<T>): T? = null
            override fun <T> getList(fieldName: String, klass: Class<T>): List<T>? = null
            override fun validate() {}
        }

        val profile = ExampleActivityContentProfile(
            "https://www.w3.org/ns/activitystreams"
        )
        assertThat(profile.type).isEqualTo("Profile")
    }
}