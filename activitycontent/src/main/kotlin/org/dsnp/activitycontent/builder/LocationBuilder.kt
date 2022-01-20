package org.dsnp.activitycontent.builder

import org.dsnp.activitycontent.exception.ValidationException
import org.dsnp.activitycontent.model.ActivityContentLocation
import org.dsnp.activitycontent.model.ConcreteActivityContentLocation

/**
 * Builder for creating [ActivityContentLocation] instances.
 * See [DSNP Spec](https://spec.dsnp.org/ActivityContent/Associated/Attachments#location) for details.
 *
 * @property name The name of the location
 */
class LocationBuilder(
    private val name: String
) {
    private var accuracy: Float? = null
    private var altitude: Float? = null
    private var latitude: Float? = null
    private var longitude: Float? = null
    private var radius: Float? = null
    private var units: ActivityContentLocation.Unit? = null
    private var additionalFields = mutableMapOf<String, Any>()

    /**
     * Set the accuracy of the coordinates as a percentage. (e.g. "94.0" means "94.0% accurate").
     *
     * @param accuracy The accuracy of the coordinates as a percentage. (e.g. "94.0" means "94.0% accurate").
     * @return The [LocationBuilder] object to be used for chaining method calls.
     */
    fun withAccuracy(accuracy: Float): LocationBuilder {
        this.accuracy = accuracy
        return this
    }

    /**
     * Set altitude of the location.
     *
     * @param altitude The altitude of the location.
     * @return The [LocationBuilder] object to be used for chaining method calls.
     */
    fun withAltitude(altitude: Float): LocationBuilder {
        this.altitude = altitude
        return this
    }

    /**
     * Set latitude of the location.
     *
     * @param latitude The latitude of the location.
     * @return The [LocationBuilder] object to be used for chaining method calls.
     */
    fun withLatitude(latitude: Float): LocationBuilder {
        this.latitude = latitude
        return this
    }

    /**
     * Set longitude of the location.
     *
     * @param longitude The longitude of the location.
     * @return The [LocationBuilder] object to be used for chaining method calls.
     */
    fun withLongitude(longitude: Float): LocationBuilder {
        this.longitude = longitude
        return this
    }

    /**
     * Set radius of the location.
     *
     * @param radius The radius of the location.
     * @return The [LocationBuilder] object to be used for chaining method calls.
     */
    fun withRadius(radius: Float): LocationBuilder {
        this.radius = radius
        return this
    }

    /**
     * Set the units for [radius] and [altitude].
     *
     * @param units The units for [radius] and [altitude].
     * @return The [LocationBuilder] object to be used for chaining method calls.
     */
    fun withUnits(units: ActivityContentLocation.Unit): LocationBuilder {
        this.units = units
        return this
    }

    /**
     * Adds an additional field that will be added to the [ActivityContentLocation] object when it is converted to JSON.
     *
     * @param name The name of the field that will be used when converting it to JSON.
     * @param value The value of the field that will be used when converting it to JSON. Values can be of type [String], [Int], [Double], [Boolean], [List], or a custom type. If it's a custom type, make sure Gson can convert it to JSON.
     * @return The [LocationBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalField(name: String, value: Any): LocationBuilder {
        this.additionalFields[name] = value
        return this
    }

    /**
     * Adds additional fields that will be added to the [ActivityContentLocation] object when it is converted to JSON.
     *
     * @param additionalFields A map that maps field names to field values for additional fields. Those additional fields will be used when converting the [ActivityContentLocation] object to JSON.
     * @return The [LocationBuilder] object to be used for chaining method calls.
     */
    fun addAdditionalFields(additionalFields: Map<String, Any>): LocationBuilder {
        additionalFields.forEach { (field, value) ->
            this.additionalFields[field] = value
        }
        return this
    }

    /**
     * Validates and builds the [ActivityContentLocation] instance using the configured values.
     *
     * @return The configured [ActivityContentLocation] instance.
     */
    @Throws(ValidationException::class)
    fun build(): ActivityContentLocation {
        val location = ConcreteActivityContentLocation(
            name,
            accuracy,
            altitude,
            latitude,
            longitude,
            radius,
            units,
            additionalFields = additionalFields
        )
        location.validate()
        return location
    }
}