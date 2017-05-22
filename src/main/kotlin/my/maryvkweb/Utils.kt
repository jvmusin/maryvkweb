package my.maryvkweb

import java.io.InputStream
import java.nio.charset.Charset
import java.util.logging.Logger

inline fun <reified T> getLogger(): Logger = Logger.getLogger(T::class.java.name)
fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
    return this.bufferedReader(charset).use { it.readText() }
}