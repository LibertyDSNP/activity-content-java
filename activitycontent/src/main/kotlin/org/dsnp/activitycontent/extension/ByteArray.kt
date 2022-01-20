package org.dsnp.activitycontent.extension

import org.web3j.crypto.Hash.sha3
import org.web3j.utils.Numeric

/**
 * Creates a hash for a ByteArray using keccak256.
 *
 * @return A keccak256 hash of the ByteArray.
 */
internal fun ByteArray.hash(): String = Numeric.toHexString(sha3(this))