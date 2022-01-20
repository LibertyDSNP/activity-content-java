package org.dsnp.activitycontent.sample.kotlin

fun main(args: Array<String>) {
    var json = BuilderSample.createSimpleNote()
    BuilderSample.readSimpleJsonNote(json)

    json = BuilderSample.createNoteWithAdditionalFields()
    BuilderSample.readJsonNoteWithAdditionalFields(json)
}