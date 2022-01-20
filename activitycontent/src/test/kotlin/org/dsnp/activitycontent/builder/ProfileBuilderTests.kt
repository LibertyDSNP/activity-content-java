package org.dsnp.activitycontent.builder

import org.assertj.core.api.Assertions.assertThat
import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.test.ExtraData
import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentHashtag
import org.dsnp.activitycontent.model.ActivityContentImageLink
import org.dsnp.activitycontent.model.ActivityContentLocation
import org.dsnp.activitycontent.model.ActivityContentMention
import org.junit.jupiter.api.Test
import java.util.*

class ProfileBuilderTests {

    @Test
    fun valid_and_json_conversion() {
        val extraData = ExtraData("value1", 123)
        val profile = ActivityContent.Builders.profile()
            .withName("user 1")
            .withPublished(Date(1000))
            .withSummary("a new user with user name 'user 1'")
            .withLocation(
                name = "Test Location",
                accuracy = 1f,
                altitude = 70f,
                latitude = 40.76567f,
                longitude = -73.980835f,
                radius = 20f,
                units = ActivityContentLocation.Unit.METER,
                additionalFields = mapOf(
                    "locationExtraField1" to "test value",
                    "locationExtraField2" to 123,
                    "locationExtraField3" to 1.23,
                    "locationExtraField4" to true,
                    "locationExtraField5" to extraData,
                    "locationExtraField6" to listOf(1,2,3)
                )
            ).addTagAsMention("dsnp://101", "test name",
                additionalFields = mapOf(
                    "mentionExtraField1" to "test value",
                    "mentionExtraField2" to 123,
                    "mentionExtraField3" to 1.23,
                    "mentionExtraField4" to true,
                    "mentionExtraField5" to extraData,
                    "mentionExtraField6" to listOf(1,2,3)
                )
            ).addTagAsHashtag("#hashtag",
                additionalFields = mapOf(
                    "hashtagExtraField1" to "test value",
                    "hashtagExtraField2" to 123,
                    "hashtagExtraField3" to 1.23,
                    "hashtagExtraField4" to true,
                    "hashtagExtraField5" to extraData,
                    "hashtagExtraField6" to listOf(1,2,3)
                )
            ).addIcon(
                mediaType = ActivityContentImageLink.MediaType.JPEG,
                listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                href = "http://www.dsnp.org/user1Image.jpeg",
                height = 100,
                width = 200,
                additionalFields = mapOf(
                    "iconExtraField1" to "test value",
                    "iconExtraField2" to 123,
                    "iconExtraField3" to 1.23,
                    "iconExtraField4" to true,
                    "iconExtraField5" to extraData,
                    "iconExtraField6" to listOf(1,2,3)
                )
            ).addIcon(
                mediaType = ActivityContentImageLink.MediaType.PNG,
                listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                href = "http://www.dsnp.org/user1Image.png",
                height = 100
            ).addIcon(
                mediaType = ActivityContentImageLink.MediaType.SVG,
                listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                href = "http://www.dsnp.org/user1Image.svg",
                width = 200
            ).addIcon(
                mediaType = ActivityContentImageLink.MediaType.WebP,
                listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                href = "http://www.dsnp.org/user1Image.webp",
            ).addIcon(
                mediaType = ActivityContentImageLink.MediaType.GIF,
                listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                href = "http://www.dsnp.org/user1Image.gif",
                height = 100,
                width = 200
            ).addIcon(
                mediaType = ActivityContentImageLink.MediaType.Other("image/bmp"),
                listOf(ActivityContent.Builders.hash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256").build()),
                href = "http://www.dsnp.org/user1Image.bmp",
                height = 100,
                width = 200
            ).addAdditionalField("profileExtraField1", "test value")
            .addAdditionalFields(
                mapOf(
                    "profileExtraField2" to 123,
                    "profileExtraField3" to 1.23,
                    "profileExtraField4" to true,
                    "profileExtraField5" to extraData,
                    "profileExtraField6" to listOf(1,2,3)
                )
            ).build()

        assertThat(profile.context).isEqualTo("https://www.w3.org/ns/activitystreams")
        assertThat(profile.type).isEqualTo("Profile")
        assertThat(profile.name).isEqualTo("user 1")
        assertThat(profile.published).isEqualTo(Date(1000))
        assertThat(profile.summary).isEqualTo("a new user with user name 'user 1'")
        // location
        assertThat(profile.location).isNotNull()
        assertThat(profile.location!!.type).isEqualTo("Place")
        assertThat(profile.location!!.name).isEqualTo("Test Location")
        assertThat(profile.location!!.accuracy).isEqualTo(1f)
        assertThat(profile.location!!.altitude).isEqualTo(70f)
        assertThat(profile.location!!.latitude).isEqualTo(40.76567f)
        assertThat(profile.location!!.longitude).isEqualTo(-73.980835f)
        assertThat(profile.location!!.radius).isEqualTo(20f)
        assertThat(profile.location!!.units).isEqualTo(ActivityContentLocation.Unit.METER)
        assertThat(profile.location!!.getString("locationExtraField1")).isEqualTo("test value")
        assertThat(profile.location!!.getInt("locationExtraField2")).isEqualTo(123)
        assertThat(profile.location!!.getDouble("locationExtraField3")).isEqualTo(1.23)
        assertThat(profile.location!!.getBoolean("locationExtraField4")).isTrue()
        assertThat(profile.location!!.getObject("locationExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat(profile.location!!.getObject("locationExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat(profile.location!!.getList("locationExtraField6", Int::class.java)).hasSize(3)
        assertThat(profile.location!!.getList("locationExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat(profile.location!!.getList("locationExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat(profile.location!!.getList("locationExtraField6", Int::class.java)!![2]).isEqualTo(3)
        // tags
        assertThat(profile.tag).hasSize(2)
        assertThat((profile.tag!![0] as ActivityContentMention).type).isEqualTo("Mention")
        assertThat((profile.tag!![0] as ActivityContentMention).id).isEqualTo("dsnp://101")
        assertThat((profile.tag!![0] as ActivityContentMention).name).isEqualTo("test name")
        assertThat((profile.tag!![0] as ActivityContentMention).getString("mentionExtraField1")).isEqualTo("test value")
        assertThat((profile.tag!![0] as ActivityContentMention).getInt("mentionExtraField2")).isEqualTo(123)
        assertThat((profile.tag!![0] as ActivityContentMention).getDouble("mentionExtraField3")).isEqualTo(1.23)
        assertThat((profile.tag!![0] as ActivityContentMention).getBoolean("mentionExtraField4")).isTrue()
        assertThat((profile.tag!![0] as ActivityContentMention).getObject("mentionExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((profile.tag!![0] as ActivityContentMention).getObject("mentionExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((profile.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)).hasSize(3)
        assertThat((profile.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((profile.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((profile.tag!![0] as ActivityContentMention).getList("mentionExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat((profile.tag!![1] as ActivityContentHashtag).name).isEqualTo("#hashtag")
        assertThat((profile.tag!![1] as ActivityContentHashtag).getString("hashtagExtraField1")).isEqualTo("test value")
        assertThat((profile.tag!![1] as ActivityContentHashtag).getInt("hashtagExtraField2")).isEqualTo(123)
        assertThat((profile.tag!![1] as ActivityContentHashtag).getDouble("hashtagExtraField3")).isEqualTo(1.23)
        assertThat((profile.tag!![1] as ActivityContentHashtag).getBoolean("hashtagExtraField4")).isTrue()
        assertThat((profile.tag!![1] as ActivityContentHashtag).getObject("hashtagExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat((profile.tag!![1] as ActivityContentHashtag).getObject("hashtagExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat((profile.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)).hasSize(3)
        assertThat((profile.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat((profile.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat((profile.tag!![1] as ActivityContentHashtag).getList("hashtagExtraField6", Int::class.java)!![2]).isEqualTo(3)
        // icons
        assertThat(profile.icon).hasSize(6)
        assertThat(profile.icon!![0].mediaType).isEqualTo(ActivityContentImageLink.MediaType.JPEG)
        assertThat(profile.icon!![0].hash).hasSize(1)
        assertThat(profile.icon!![0].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat(profile.icon!![0].hash[0].algorithm).isEqualTo("keccak256")
        assertThat(profile.icon!![0].href).isEqualTo("http://www.dsnp.org/user1Image.jpeg")
        assertThat(profile.icon!![0].height).isEqualTo(100)
        assertThat(profile.icon!![0].width).isEqualTo(200)
        assertThat(profile.icon!![0].getString("iconExtraField1")).isEqualTo("test value")
        assertThat(profile.icon!![0].getInt("iconExtraField2")).isEqualTo(123)
        assertThat(profile.icon!![0].getDouble("iconExtraField3")).isEqualTo(1.23)
        assertThat(profile.icon!![0].getBoolean("iconExtraField4")).isTrue()
        assertThat(profile.icon!![0].getObject("iconExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat(profile.icon!![0].getObject("iconExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat(profile.icon!![0].getList("iconExtraField6", Int::class.java)).hasSize(3)
        assertThat(profile.icon!![0].getList("iconExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat(profile.icon!![0].getList("iconExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat(profile.icon!![0].getList("iconExtraField6", Int::class.java)!![2]).isEqualTo(3)
        assertThat(profile.icon!![1].mediaType).isEqualTo(ActivityContentImageLink.MediaType.PNG)
        assertThat(profile.icon!![1].hash).hasSize(1)
        assertThat(profile.icon!![1].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat(profile.icon!![1].hash[0].algorithm).isEqualTo("keccak256")
        assertThat(profile.icon!![1].href).isEqualTo("http://www.dsnp.org/user1Image.png")
        assertThat(profile.icon!![1].height).isEqualTo(100)
        assertThat(profile.icon!![1].width).isNull()
        assertThat(profile.icon!![2].mediaType).isEqualTo(ActivityContentImageLink.MediaType.SVG)
        assertThat(profile.icon!![2].hash).hasSize(1)
        assertThat(profile.icon!![2].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat(profile.icon!![2].hash[0].algorithm).isEqualTo("keccak256")
        assertThat(profile.icon!![2].href).isEqualTo("http://www.dsnp.org/user1Image.svg")
        assertThat(profile.icon!![2].height).isNull()
        assertThat(profile.icon!![2].width).isEqualTo(200)
        assertThat(profile.icon!![3].mediaType).isEqualTo(ActivityContentImageLink.MediaType.WebP)
        assertThat(profile.icon!![3].hash).hasSize(1)
        assertThat(profile.icon!![3].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat(profile.icon!![3].hash[0].algorithm).isEqualTo("keccak256")
        assertThat(profile.icon!![3].href).isEqualTo("http://www.dsnp.org/user1Image.webp")
        assertThat(profile.icon!![3].height).isNull()
        assertThat(profile.icon!![3].width).isNull()
        assertThat(profile.icon!![4].mediaType).isEqualTo(ActivityContentImageLink.MediaType.GIF)
        assertThat(profile.icon!![4].hash).hasSize(1)
        assertThat(profile.icon!![4].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat(profile.icon!![4].hash[0].algorithm).isEqualTo("keccak256")
        assertThat(profile.icon!![4].href).isEqualTo("http://www.dsnp.org/user1Image.gif")
        assertThat(profile.icon!![5].mediaType).isEqualTo(ActivityContentImageLink.MediaType.Other("image/bmp"))
        assertThat(profile.icon!![5].hash).hasSize(1)
        assertThat(profile.icon!![5].hash[0].value).isEqualTo("0xabcdef0000000000000000000000000000000000000000000000000000000000")
        assertThat(profile.icon!![5].hash[0].algorithm).isEqualTo("keccak256")
        assertThat(profile.icon!![5].href).isEqualTo("http://www.dsnp.org/user1Image.bmp")
        assertThat(profile.icon!![5].height).isEqualTo(100)
        assertThat(profile.icon!![5].width).isEqualTo(200)
        // additional fields
        assertThat(profile.getString("profileExtraField1")).isEqualTo("test value")
        assertThat(profile.getInt("profileExtraField2")).isEqualTo(123)
        assertThat(profile.getDouble("profileExtraField3")).isEqualTo(1.23)
        assertThat(profile.getBoolean("profileExtraField4")).isTrue()
        assertThat(profile.getObject("profileExtraField5", ExtraData::class.java)!!.value1).isEqualTo("value1")
        assertThat(profile.getObject("profileExtraField5", ExtraData::class.java)!!.value2).isEqualTo(123)
        assertThat(profile.getList("profileExtraField6", Int::class.java)).hasSize(3)
        assertThat(profile.getList("profileExtraField6", Int::class.java)!![0]).isEqualTo(1)
        assertThat(profile.getList("profileExtraField6", Int::class.java)!![1]).isEqualTo(2)
        assertThat(profile.getList("profileExtraField6", Int::class.java)!![2]).isEqualTo(3)

        val createdJson = profile.toJson()
        val originalJson = this::class.java.getResource("/profile_valid.json")!!.readText()

        assertThat(createdJson).isEqualTo(originalJson)
    }

    @Test
    fun withLocation_ActivityContentLocation() {
        val profile = ActivityContent.Builders.profile()
            .withLocation(
                ActivityContent.Builders.location(
                    "Test Location"
                ).withAccuracy(1f)
                    .withAltitude(70f)
                    .withLatitude(40.76567f)
                    .withLongitude(-73.980835f)
                    .withRadius(20f)
                    .withUnits(ActivityContentLocation.Unit.METER)
                    .build()
            ).build()

        // location
        assertThat(profile.location).isNotNull()
        assertThat(profile.location!!.type).isEqualTo("Place")
        assertThat(profile.location!!.name).isEqualTo("Test Location")
        assertThat(profile.location!!.accuracy).isEqualTo(1f)
        assertThat(profile.location!!.altitude).isEqualTo(70f)
        assertThat(profile.location!!.latitude).isEqualTo(40.76567f)
        assertThat(profile.location!!.longitude).isEqualTo(-73.980835f)
        assertThat(profile.location!!.radius).isEqualTo(20f)
        assertThat(profile.location!!.units).isEqualTo(ActivityContentLocation.Unit.METER)
    }

    @Test
    fun addTagAsHashtag_noAdditionalFields() {
        val profile = ActivityContent.Builders.profile()
            .addTagAsHashtag("#hashtag")
            .build()

        assertThat(profile.tag).hasSize(1)
        assertThat((profile.tag!![0] as ActivityContentHashtag).name).isEqualTo("#hashtag")
    }

    @Test
    fun addTagAsMention_noName() {
        val profile = ActivityContent.Builders.profile()
            .addTagAsMention("dsnp://101")
            .build()

        assertThat(profile.tag).hasSize(1)
        assertThat((profile.tag!![0] as ActivityContentMention).id).isEqualTo("dsnp://101")
        assertThat((profile.tag!![0] as ActivityContentMention).name).isNull()
    }

    @Test
    fun addTag() {
        val profile = ActivityContent.Builders.profile()
            .addTag(ActivityContent.Builders.Tag.hashtag("#hashtag").build())
            .build()

        assertThat(profile.tag).hasSize(1)
        assertThat((profile.tag!![0] as ActivityContentHashtag).name).isEqualTo("#hashtag")
    }

    @Test
    fun withLocation_noAdditionalFields() {
        val profile = ActivityContent.Builders.profile()
            .withLocation(
                name = "Test Location",
                accuracy = 1f,
                altitude = 70f,
                latitude = 40.76567f,
                longitude = -73.980835f,
                radius = 20f,
                units = ActivityContentLocation.Unit.METER
            )
            .build()

        assertThat(profile.location).isNotNull()
        assertThat(profile.location!!.type).isEqualTo("Place")
        assertThat(profile.location!!.name).isEqualTo("Test Location")
        assertThat(profile.location!!.accuracy).isEqualTo(1f)
        assertThat(profile.location!!.altitude).isEqualTo(70f)
        assertThat(profile.location!!.latitude).isEqualTo(40.76567f)
        assertThat(profile.location!!.longitude).isEqualTo(-73.980835f)
        assertThat(profile.location!!.radius).isEqualTo(20f)
        assertThat(profile.location!!.units).isEqualTo(ActivityContentLocation.Unit.METER)
    }

    @Test
    fun invalidLocation() {
        val exception = org.junit.jupiter.api.Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.profile()
                .withLocation(
                    name = "Test Location",
                    accuracy = 1f,
                    altitude = 70f,
                    latitude = 40.76567f,
                    longitude = -73.980835f,
                    radius = 20f,
                    units = ActivityContentLocation.Unit.METER,
                    additionalFields = mapOf("name" to "invalid field")
                ).build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: name")
    }

    @Test
    fun invalidTag() {
        val exception = org.junit.jupiter.api.Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.profile()
                .addTagAsMention("invalidId", "test name")
                .build()
        }
        assertThat(exception.message).isEqualTo("Invalid DSNP user URI: invalidId")
    }

    @Test
    fun invalidIcon() {
        val exception = org.junit.jupiter.api.Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.profile()
                .withName("user 1")
                .addIcon(
                    ActivityContent.Builders.Link.image(
                        mediaType = ActivityContentImageLink.MediaType.Other("image/bmp"),
                        href = "http://www.dsnp.org/user1Image.bmp"
                    ).addHash("0xabcdef0000000000000000000000000000000000000000000000000000000000", "keccak256")
                        .build()
                ).build()
        }
        assertThat(exception.message).isEqualTo("At least one icon has to have a supported mime type (GIF/SVG/PNG/WebP/JPEG)")
    }

    @Test
    fun invalidAdditionalField() {
        val exception = org.junit.jupiter.api.Assertions.assertThrows(
            ValidationException::class.java
        ) {
            ActivityContent.Builders.profile()
                .addAdditionalField("name", "invalid field")
                .build()
        }
        assertThat(exception.message).isEqualTo("Some additional fields are in conflict with default fields: name")
    }
}