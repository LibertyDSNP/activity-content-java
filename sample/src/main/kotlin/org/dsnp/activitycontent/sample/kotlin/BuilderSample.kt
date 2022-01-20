package org.dsnp.activitycontent.sample.kotlin

import org.dsnp.activitycontent.ActivityContent
import org.dsnp.activitycontent.model.ActivityContentLocation
import org.dsnp.activitycontent.model.ActivityContentVideoLink
import java.util.*

class BuilderSample {
    companion object {
        fun createSimpleNote(): String {
            val note = ActivityContent.Builders.note("Note Content")
                .withName("Note Name")
                .withPublished(Date())
                .addAttachment(
                    ActivityContent.Builders.Attachment.video()
                        .addUrl(
                            mediaType = ActivityContentVideoLink.MediaType.MP4,
                            hash = listOf(ActivityContent.Builders.hash("9da9ec7069ee6ad9f4e58929462db0f04f49034a356d1a36f631ce6457101bdd").build()),
                            href = "https://commons.wikimedia.org/wiki/File:Two-Horned_Chameleon.webm",
                            height = 1280,
                            width = 720
                        ).build()
                )
                .addTagAsMention(
                    id = "dsnp://1",
                    name = "newUser"
                )
                .addTagAsHashtag("#content")
                .withLocation(
                    name = "Unfinished",
                    accuracy = 1f,
                    altitude = 70f,
                    latitude = 40.76567f,
                    longitude = -73.980835f,
                    radius = 20f,
                    units = ActivityContentLocation.Unit.METER
                )
                .build()

            note.validate()
            val json = note.toJson()
            println("\n++++++ Simple Note as Json ++++++")
            println(json)

            return json
        }

        fun createNoteWithAdditionalFields(): String {
            val extraData = ExtraData("value 1", 123)
            val note = ActivityContent.Builders.note("Note Content")
                .withName("Note Name")
                .addAdditionalField("backgroundColor", 123)
                .addAdditionalField("title", "Note Title")
                .addAdditionalFields(mapOf(
                    "subtitle" to "Note Subtitle",
                    "extraData" to extraData
                ))
                .addTagAsHashtag("#content", mapOf("extraField" to "with a value"))
                .build()

            note.validate()
            val json = note.toJson()
            println("\n++++++ Note with custom fields as Json ++++++")
            println(json)

            return json
        }

        fun readSimpleJsonNote(json: String) {
            val note = ActivityContent.parseNote(json)

            println("\n++++++ Simple Note as object ++++++")
            println(note)
        }

        fun readJsonNoteWithAdditionalFields(json: String) {
            val note = ActivityContent.parseNote(json, supportAdditionalFields = true)

            println("\n++++++ Note with additional fields as object ++++++")
            println(note)
            println("Custom note field 'backgroundColor': ${note.getInt("backgroundColor")}")
        }
    }
}