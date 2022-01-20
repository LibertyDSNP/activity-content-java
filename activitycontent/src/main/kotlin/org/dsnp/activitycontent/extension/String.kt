package org.dsnp.activitycontent.extension

import org.web3j.crypto.Hash.sha3String

/**
 * Creates a hash for a String using keccak256.
 *
 * @return A keccak256 hash of the String.
 */
internal fun String.hash(): String = sha3String(this)