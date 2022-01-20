package org.dsnp.activitycontent.sample.java;

import org.dsnp.activitycontent.ActivityContent;
import org.dsnp.activitycontent.builder.AudioLinkBuilder;
import org.dsnp.activitycontent.exception.ValidationException;
import org.dsnp.activitycontent.model.ActivityContentHash;
import org.dsnp.activitycontent.model.ActivityContentLocation;
import org.dsnp.activitycontent.model.ActivityContentNote;
import org.dsnp.activitycontent.model.ActivityContentVideoLink;
import org.dsnp.activitycontent.sample.kotlin.ExtraData;

import java.util.*;

public class BuilderSample {
    public static String createSimpleNote() throws ValidationException {
        // create hash
        List<ActivityContentHash> hashes = new ArrayList<>();
        hashes.add(ActivityContent.Builders.hash("9da9ec7069ee6ad9f4e58929462db0f04f49034a356d1a36f631ce6457101bdd").build());
        
        ActivityContentNote note = ActivityContent.Builders.note("Note Content")
                .withName("Note Name")
                .withPublished(new Date())
                .addAttachment(
                        ActivityContent.Builders.Attachment.video()
                                .addUrl(
                                        ActivityContentVideoLink.MediaType.MP4.INSTANCE,
                                        hashes,
                                        "https://commons.wikimedia.org/wiki/File:Two-Horned_Chameleon.webm",
                                        1280,
                                        720
                                ).build()
                )
                .addTagAsMention(
                        "dsnp://1",
                        "newUser"
                )
                .addTagAsHashtag("#content")
                .withLocation(
                        "Unfinished",
                        1f,
                        70f,
                        40.76567f,
                        -73.980835f,
                        20f,
                        ActivityContentLocation.Unit.METER
                )
                .build();

        note.validate();
        String json = note.toJson(null);
        System.out.println("\n++++++ Simple Note as Json ++++++");
        System.out.println(json);

        return json;
    }

    public static String createNoteWithAdditionalFields() throws ValidationException {
        ExtraData extraData = new ExtraData("value 1", 123);

        // create custom fields for tag
        Map<String, Object> customTagFields = new HashMap<>();
        customTagFields.put("extraField", "with a value");

        // create note
        ActivityContentNote note = ActivityContent.Builders.note("Note Content")
                .withName("Note Name")
                .addAdditionalField("backgroundColor", 123)
                .addAdditionalField("title", "Note Title")
                .addAdditionalField("subtitle", "Note Subtitle")
                .addAdditionalField("extraData", extraData)
                .addTagAsHashtag("#content", customTagFields)
                .build();

        note.validate();
        String json = note.toJson(null);
        System.out.println("\n++++++ Simple Note as Json ++++++");
        System.out.println(json);

        return json;
    }

    public static void readSimpleJsonNote(String json) throws ValidationException {
        ActivityContentNote note = ActivityContent.parseNote(json);

        System.out.println("\n++++++ Simple Note as object ++++++");
        System.out.println(note);
    }

    public static void readJsonNoteWithAdditionalFields(String json) throws ValidationException {
        ActivityContentNote note = ActivityContent.parseNote(json, true);

        System.out.println("\n++++++ Simple Note as object ++++++");
        System.out.println(note);
        System.out.println("Custom note field 'backgroundColor': " + note.getInt("backgroundColor"));
    }
}
