package org.dsnp.activitycontent.exception

/**
 * This exception is thrown when validation of an [Activity Content](https://spec.dsnp.org/ActivityContent/Overview) objects fails.
 * This can happen while converting JSON into objects, or while creating Activity Content objects.
 *
 * @constructor
 * Creates an instance of ValidationException
 *
 * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
 * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
 */
class ValidationException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)