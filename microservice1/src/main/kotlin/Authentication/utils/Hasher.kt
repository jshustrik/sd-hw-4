package Authentication.utils

import java.security.MessageDigest

fun hashPassword(password: String): String {
    val algorithm = "SHA-256"
    val digest = MessageDigest.getInstance(algorithm)
    val hash = digest.digest(password.toByteArray())

    return bytesToHex(hash)
}

private fun bytesToHex(bytes: ByteArray): String {
    val hexChars = CharArray(bytes.size * 2)
    for (i in bytes.indices) {
        val v = bytes[i].toInt() and 0xFF
        hexChars[i * 2] = "0123456789abcdef"[v ushr 4]
        hexChars[i * 2 + 1] = "0123456789abcdef"[v and 0x0F]
    }
    return String(hexChars)
}