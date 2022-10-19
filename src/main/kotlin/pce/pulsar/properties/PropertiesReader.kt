package pce.pulsar.properties

import java.util.*
import kotlin.streams.toList

open class PropertiesReader(fileName: String) {
    private val properties = Properties()

    init {
        val file = this::class.java.classLoader.getResourceAsStream(fileName)
        file?.use {
            properties.load(file)
        } ?: throw Exception("Unable to open property file $fileName")
    }

    fun getProperty(key: String): String? = properties.getProperty(key)

    fun getProperty(key: String, defaultValue: String): String = properties.getProperty(key, defaultValue)

    fun getMandatoryProperty(key: String): String =
        getProperty(key) ?: throw MissingKeyException("Mandatory property $key is not defined")

    fun uniquePrefixes(): List<String> =
        properties.keys.stream()
            .map { key -> key.toString().split(".", limit = 2) }
            .filter { splitKey -> splitKey.size > 1 }
            .map { splitKey -> splitKey.first() }
            .distinct()
            .toList()
}

class MissingKeyException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
