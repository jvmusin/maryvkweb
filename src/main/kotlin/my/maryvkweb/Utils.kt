package my.maryvkweb

import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import java.io.InputStream
import java.nio.charset.Charset
import java.util.logging.Logger

inline fun <reified T> getLogger(): Logger = Logger.getLogger(T::class.java.name)
inline fun <reified T> CacheManager.getCache(): Cache = getCache(T::class.java.name)
operator fun Cache.set(key: String, value: Any?) = put(key, value)

fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String
        = this.bufferedReader(charset).use { it.readText() }