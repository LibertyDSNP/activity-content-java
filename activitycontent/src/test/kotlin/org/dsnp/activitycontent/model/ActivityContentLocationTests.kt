package org.dsnp.activitycontent.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ActivityContentLocationTests {
    @Test
    fun getType() {
        class ExampleActivityContentLocation(
            override val name: String,
            override val accuracy: Float? = null,
            override val altitude: Float? = null,
            override val latitude: Float? = null,
            override val longitude: Float? = null,
            override val radius: Float? = null,
            override val units: ActivityContentLocation.Unit? = null
        ) : ActivityContentLocation {
            override fun getString(fieldName: String): String? = null
            override fun getInt(fieldName: String): Int? = null
            override fun getDouble(fieldName: String): Double? = null
            override fun getBoolean(fieldName: String): Boolean? = null
            override fun <T> getObject(fieldName: String, klass: Class<T>): T? = null
            override fun <T> getList(fieldName: String, klass: Class<T>): List<T>? = null
            override fun validate() {}
        }

        val location = ExampleActivityContentLocation("Test Location")
        assertThat(location.type).isEqualTo("Place")
    }
}