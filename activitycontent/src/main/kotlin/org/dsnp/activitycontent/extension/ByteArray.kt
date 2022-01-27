package org.dsnp.activitycontent.extension

import org.bouncycastle.jcajce.provider.digest.Keccak
import org.bouncycastle.util.encoders.Hex


/**
 * Creates a hash for a ByteArray using keccak256.
 *
 * @return A keccak256 hash of the ByteArray.
 */
internal fun ByteArray.hash(): String {
    return "0x" + String(Hex.encode(Keccak.Digest256().digest(this)))
}