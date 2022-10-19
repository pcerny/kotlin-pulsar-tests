package pce.pulsar.pulsar.producer

import org.apache.pulsar.client.api.*
import pce.pulsar.properties.pulsar.ProducerProperties

class PulsarProducer(
    pulsarProperties: ProducerProperties,
    pulsarClient: PulsarClient,
) : AutoCloseable {
    private val producer: Producer<String> by lazy {
        pulsarClient.newProducer(Schema.STRING)
            .topic(pulsarProperties.topic)
            .create()
    }

    fun send(key: String, message: String): MessageId? =
        constructMessage(message).key(key).send()

    fun send(message: String): MessageId? = constructMessage(message).send()

    private fun constructMessage(message: String): TypedMessageBuilder<String> =
        producer.newMessage().let { builder ->
            builder.value(message)
        }

    override fun close() = producer.close()
}