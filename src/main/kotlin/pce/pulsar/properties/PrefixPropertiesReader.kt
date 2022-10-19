package pce.pulsar.properties

class PrefixPropertiesReader(val prefix: String, private val properties: PropertiesReader) {

    fun getProperty(key: String): String? = properties.getProperty(prefixedKey(key))

    fun getProperty(key: String, defaultValue: String): String = properties.getProperty(prefixedKey(key), defaultValue)

    fun getMandatoryProperty(key: String): String = properties.getMandatoryProperty(prefixedKey(key))

    fun subPrefixReader(subPrefix: String): PrefixPropertiesReader =
        PrefixPropertiesReader(prefixedKey(subPrefix), properties)

    private fun prefixedKey(key: String) = "$prefix.$key"
}