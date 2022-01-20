package org.dsnp.activitycontent.sample.java;

import org.dsnp.activitycontent.exception.ValidationException;

public class Main {
    public static void main(String[] args) {
        try {
            String json = BuilderSample.createSimpleNote();
            BuilderSample.readSimpleJsonNote(json);

            json = BuilderSample.createNoteWithAdditionalFields();
            BuilderSample.readJsonNoteWithAdditionalFields(json);
        } catch (ValidationException exception) {
            exception.printStackTrace();
        }
    }
}
