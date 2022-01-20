package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.extension.hash
import org.dsnp.activitycontent.model.ALGORITHM_HASH
import org.dsnp.activitycontent.model.ActivityContentHash
import org.dsnp.activitycontent.model.ConcreteActivityContentHash

/**
 * Builder for creating [ActivityContentHash] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Hash) for details.
 *
 * @property value A hash value that will be saved in the [ActivityContentHash]. IMPORTANT: This parameter takes a hash as a value, NOT content that will be hashed by the [HashBuilder].
 * @property algorithm The name of the algorithm that was used to generate the hash [value].
 */
class HashBuilder(
    private val value: String,
    private val algorithm: String
) {
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Creates and returns a [HashBuilder] that allows you to build [ActivityContentHash] instances.
     *
     * @param content The content will be automatically hashed by the [HashBuilder] using the appropriate hash algorithm (keccak256).
     * @return A [HashBuilder] instance.
     */
    constructor(content: String) : this(content.hash(), ALGORITHM_HASH)

    /**
     * Creates and returns a [HashBuilder] that allows you to build [ActivityContentHash] instances.
     *
     * @param content The content will be automatically hashed by the [HashBuilder] using the appropriate hash algorithm (keccak256).
     * @return A [HashBuilder] instance.
     */
    constructor(content: ByteArray) : this(content.hash(), ALGORITHM_HASH)

    /**
     * Adds an additional field that will be added to the [ActivityContentHash] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [HashBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): HashBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentHash] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentHash] object to JSON.
     * @return The [HashBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): HashBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Validates and builds the [ActivityContentHash] instance using the configured values.
     *
     * @return The configured [ActivityContentHash] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentHash {
        val hash = ConcreteActivityContentHash(value, algorithm, additionalFields = additionalFields)
        hash.validate()
        return hash
    }
}