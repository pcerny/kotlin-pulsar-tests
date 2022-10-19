package pce.pulsar.properties.pulsar

import org.koin.core.qualifier.named
import org.koin.dsl.module
import pce.pulsar.properties.PropertiesReader

val pulsarPropertiesModule = module {
    single(named("consumers")) { PropertiesReader("consumers.properties") }
    single(named("producers")) { PropertiesReader("producers.properties") }
    single { ClientProperties(PropertiesReader("pulsarClient.properties")) }
}