package my.maryvkweb

import java.util.logging.Logger

inline fun <reified T> getLogger() = Logger.getLogger(T::class.java.name)
inline fun <T1> Iterable<T1>.forEach(action: (T1) -> Any?): Unit {
    for (element in this) action(element)
}