package pce.pulsar.pulsar.consumer

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.apache.pulsar.client.api.Consumer
import org.apache.pulsar.client.api.Message
import org.apache.pulsar.client.api.MessageListener

class PulsarListener<T>(
    private val ackHandler: PulsarListenerAckHandler<T>,
    private val exceptionHandler: PulsarListenerExceptionHandler<T>,
    private val handlers: Collection<PulsarListenerMessageHandler<T>>,
) : MessageListener<T> {

    private val logger = KotlinLogging.logger {}

    init {
        logger.info { "PulsarListener initialized with ${handlers.size} message handlers: ${handlers.joinToString()}" }
    }

    override fun received(consumer: Consumer<T>, message: Message<T>?) {
        if (message != null) {
            GlobalScope.launch(
                CoroutineName("pulsar-msg-handler")
            ) {
                handleMessage(consumer, message)
            }
        } else {
            logger.warn { "pulsarListener MSG is NULL" }
        }
    }

    /**
     * Apply [PulsarListenerMessageHandler] to process message. Depending on result -
     * [PulsarListenerAckHandler] in case of success or [PulsarListenerExceptionHandler]
     * in case of exception during processing.
     */
    private suspend inline fun handleMessage(consumer: Consumer<T>, message: Message<T>) {
        try {
            handlers.forEach { handler ->
                handler.processMessage(message)
            }
            ackHandler(message, consumer)
        } catch (ignore: Exception) {
            // according to Consumer implementations msg or consumer cannot be null
            exceptionHandler(ignore, message, consumer)
        }
    }
}
