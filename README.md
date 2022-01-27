Activity Content SDK for JVM Languages
====================
![build & unit tests](https://github.com/LibertyDSNP/activity-content-java/workflows/Run%20Unit%20Tests/badge.svg)   ![build & unit tests](https://github.com/LibertyDSNP/activity-content-java/workflows/Publish%20to%20Maven%20Central/badge.svg)

The Activity Content SDK is a library that supports converting DSNP Activity Content data to and from JSON.
The SDK is meant for languages running on the **Java Virtual Machine**.
For details on DSNP or Activity Content please see

**[Activity Content DSNP Spec 1.0][1]**

# Download

[//]: # (## Jar)
[//]: # (You can download the latest jar from GitHub [directly][2].)

## Gradle
```gradle
repositories {
  mavenCentral()
}

dependencies {
    implementation 'org.dsnp.activitycontent:activitycontent:1.1.0'
}
```

## Maven
```maven
<dependency>
    <groupId>org.dsnp.activitycontent</groupId>
    <artifactId>activitycontent</artifactId>
    <version>1.1.0</version>
</dependency>
```

# Getting Started
## Writing JSON
### Builders
You can use Builders to create Activity Content objects. This is how you can create a builder:
```kotlin
// Builders
ActivityContent.Builders.profile()
ActivityContent.Builders.note()
ActivityContent.Builders.hash()
ActivityContent.Builders.location()
// Builders for Attachments
ActivityContent.Builders.Attachment.audio()
ActivityContent.Builders.Attachment.image()
ActivityContent.Builders.Attachment.video()
ActivityContent.Builders.Attachment.link()
// Builders for Links
ActivityContent.Builders.Link.audio()
ActivityContent.Builders.Link.image()
ActivityContent.Builders.Link.video()
// Builders for Tags
ActivityContent.Builders.Tag.mention()
ActivityContent.Builders.Tag.hashtag()
```
Following the [Activity Content DSNP Spec 1.0][1], only [notes][3] and [profiles][4] should be converted to JSON. You can do so by using the following methods:
```kotlin
ActivityContentNote::toJson
ActivityContentProfile::toJson
```
### Example

```Kotlin
// build an ActivityContentNote object
val note = ActivityContent.Builders.note("Note Content")
    .withName("Note Name")
    .withPublished(Date())
    .addAttachment(
        ActivityContent.Builders.Attachment.video()
            .addUrl(
                mediaType = ActivityContentVideoLink.MediaType.MP4,
                hash = listOf(
                    ActivityContent.Builders.hash("9da9ec7069ee6ad9f4e58929462db0f04f49034a356d1a36f631ce6457101bdd")
                        .build()
                ),
                href = "https://commons.wikimedia.org/wiki/File:Two-Horned_Chameleon.webm"
            ).build()
    )
    .addTagAsMention(
        id = "dsnp://1",
        name = "John Doe"
    )
    .addTagAsHashtag("#somecontent")
    .withLocation(
        name = "Great Location",
        accuracy = 1f,
        altitude = 70f,
        latitude = 40.76567f,
        longitude = -73.980835f,
        radius = 20f,
        units = ActivityContentLocation.Unit.METER
    )
    .build()

// convert the ActivityContentNote to json
val json = note.toJson()
```
For more detailed examples, please refer to the [kotlin][5] or [java][6] sample.
## Reading JSON
```kotlin
val json = """
{
	"@context": "https://www.w3.org/ns/activitystreams",
	"type": "Note",
	"content": "Note Content",
	"mediaType": "text/plain"
}
"""
val note = ActivityContent.parseNote(json)
```
For more detailed examples, please refer to the [kotlin][5] or [java][6] sample.
## Structure
This is a simplified UML class diagram, showing how the classes - that represent the Activity Content types - relate to each other:

![UML class diagram][7]

# Support for Additional Fields
You may choose to add additional fields that are not defined in the [Activity Content Spec][1] (see [Additional Fields][8]).
## Writing Additional Fields to JSON
Builders have methods for adding additional fields. Additional fields can be added to any Activity Content type. Here are 2 examples of how to add additional fields to an ActivityContentNote:
```kotlin
// Example 1
ActivityContent.Builders.note("Note Content")
    .addAdditionalField("backgroundColor", 123)

// Example 2
ActivityContent.Builders.note("Note Content")
    .addAdditionalFields(mapOf(
        "backgroundColor" to 123
    ))
```

In addition, some methods can take additional fields as arguments. Here is an example when adding a hashtag to a note:
```kotlin
ActivityContent.Builders.note("Note Content")
    .addTagAsHashtag("#content", mapOf("extraField" to "with a value"))
```

When the Activity Content objects are converted to JSON, the additional fields will be included in the output JSON.

**Warning: Additional field names must not collide with the existing field names of the Activity Content types. If they do, a ValidationException will be thrown when calling build() on a builder.**

## Reading Additional Fields from JSON

By default, additional field support is turned OFF in order to reduce memory usage. To turn it on, you need to pass in true for the parameter ```supportAdditionalFields``` when parsing JSON:
```kotlin
ActivityContent.parseNote(json, supportAdditionalFields = true)
```

Now, you are able to access additional fields with get methods. Make sure that you call the method with the correct type. If the method with the wrong type is called, null will be returned.
```kotlin
val json = """
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "type": "Note",
        "content": "Note Content",
        "mediaType": "text/plain",
        "field1": "value1",
        "field2": 123,
        "field3": 1.23,
        "field4": true,
        "field5": {
           "value1": "v1",
           "value2": 321
        },
        "field6": [1, 2, 3]
    }
"""
val note = ActivityContent.parseNote(json, supportAdditionalFields = true)
note.getString("field1")
note.getInt("field1") // will return null
note.getInt("field2")
note.getDouble("field3")
note.getBoolean("field4")
note.getObject("field5", ExtraData::class.java)
note.getList("field6", Int::class.java)
```
**Warning: Additional field names must not collide with the existing field names of the Activity Content types. If they do, a ValidationException will be thrown when calling ActivityContent.parseNote() / ActivityContent.parseProfile().**

License
=======
    
        Copyright 2022 Unfinished Labs LLC
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://spec.dsnp.org/ActivityContent/Overview

[3]: https://spec.dsnp.org/ActivityContent/Types/Note
[4]: https://spec.dsnp.org/ActivityContent/Types/Profile
[5]: ./sample/src/main/kotlin/org/dsnp/activitycontent/sample/kotlin/BuilderSample.kt
[6]: ./sample/src/main/java/org/dsnp/activitycontent/sample/java/BuilderSample.java
[7]: class_diagram.svg
[8]: https://spec.dsnp.org/ActivityContent/Overview#additional-fields