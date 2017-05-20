package my.maryvkweb

import java.util.logging.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LoggerDelegate(val clazz: Class<*>) : ReadOnlyProperty<Any?, Logger> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Logger = Logger.getLogger(clazz.name)
}