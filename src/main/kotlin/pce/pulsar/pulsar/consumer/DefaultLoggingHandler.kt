package pce.pulsar.pulsar.consumer

import mu.KLoggable
import org.apache.pulsar.client.api.Message

class DefaultLoggingHandler(
    private val consumerIdentification: String
) : PulsarListenerMessageHandler<String>, KLoggable {
    override val logger = logger()

    override suspend fun processMessage(msg: Message<String>) {
        logger.info { "Message arrived msg:$msg" }
    }

    override fun toString(): String {
        return "$consumerIdentification-defaultPulsarMessageLoggingHandler"
    }
}
