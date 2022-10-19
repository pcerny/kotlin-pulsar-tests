package pce.pulsar.pulsar.producer

import mu.KotlinLogging
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pce.pulsar.properties.PrefixPropertiesReader
import pce.pulsar.properties.PropertiesReader
import pce.pulsar.properties.pulsar.ProducerProperties
import pce.pulsar.pulsar.Client

class ProducerFactory(private val pulsarClient: Client, private val producersProperties: PropertiesReader) {
    private val logger = KotlinLogging.logger {}

    init {
        createProducers()
    }

    private fun createProducers() {
        val producersModule = module {
            producersProperties.uniquePrefixes().forEach { prefix ->
                logger.info { "Registering producer prefix=$prefix" }
                single(named(prefix)) { createProducer(prefix) }
            }
        }
        loadKoinModules(producersModule)
    }

    private fun createProducer(prefix: String) = PulsarProducer(
        ProducerProperties(PrefixPropertiesReader(prefix, producersProperties)),
        pulsarClient.pulsarClient
    )
}