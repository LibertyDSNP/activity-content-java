package org.dsnp.activitycontent.builder

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentLocation
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class LocationBuilderTests {
    @Test
    fun valid() {
        val location = ActivityContent.Builders.location(
            "Test Location"
        ).withAccuracy(1f)
            .withAltitude(70f)
            .withLatitude(40.76567f)
            .withLongitude(-73.980835f)
            .withRadius(20f)
            .withUnits(ActivityContentLocation.Unit.METER)
            .addAdditionalField("locationExtraField1", "test value")
            .addAdditionalFields(mapOf(
                "locationExtraField2" to 123,
                "locationExtraField3" to 1.23
            )).build()

        assertThat(location.type).isEqualTo("Place")
        assertThat(location.name).isEqualTo("Test Location")
        assertThat(location.accuracy).isEqualTo(1f)
        assertThat(location.altitude).isEqualTo(70f)
        assertThat(location.latitude).isEqualTo(40.76567f)
        assertThat(location.longitude).isEqualTo(-73.980835f)
        assertThat(location.radius).isEqualTo(20f)
        assertThat(location.units).isEqualTo(ActivityContentLocation.Unit.METER)
        assertThat(location.getString("locationExtraField1")).isEqualTo("test value")
        assertThat(location.getInt("locationExtraField2")).isEqualTo(123)
        assertThat(location.getDouble("locationExtraField3")).isEqualTo(1.23)
    }

    @Test
    fun invalid_custom_fields() {
        val exception = assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.location(
                "some location"
            ).addAdditionalField("name", "123")
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: name")
    }
}