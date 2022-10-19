package pce.pulsar.pulsar.consumer

import mu.KLoggable
import org.apache.pulsar.client.api.Consumer
import org.apache.pulsar.client.api.Message

class DefaultExceptionHandler(
    private val consumerIdentification: String
) : PulsarListenerExceptionHandler<String>, KLoggable {
    override val logger = logger()

    override suspend fun invoke(exception: Throwable, msg: Message<String>, consumer: Consumer<String>) {
        logger.info { "Message processing failed msg=$msg exc=$exception" }
        super.invoke(exception, msg, consumer)
    }

    override fun toString(): String {
        return "$consumerIdentification-defaultPulsarExceptionHandler"
    }
}
