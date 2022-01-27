package org.dsnp.activitycontent.extension

import org.bouncycastle.jcajce.provider.digest.Keccak
import org.bouncycastle.util.encoders.Hex

/**
 * Creates a hash for a String using keccak256.
 *
 * @return A keccak256 hash of the String.
 */
internal fun String.hash(): String {
    return "0x" + String(Hex.encode(Keccak.Digest256().digest(this.toByteArray())))
}