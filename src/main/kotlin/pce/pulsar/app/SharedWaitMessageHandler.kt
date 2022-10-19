package pce.pulsar.app

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import mu.KotlinLogging
import org.apache.pulsar.client.api.Message
import pce.pulsar.pulsar.consumer.PulsarListenerMessageHandler
import kotlin.coroutines.coroutineContext

class SharedWaitMessageHandler : PulsarListenerMessageHandler<String> {
    private val logger = KotlinLogging.logger {}

    override suspend fun processMessage(msg: Message<String>) {
        val msgItems = msg.value.split("-", limit=2)
        val crName = coroutineContext[CoroutineName]
        if (msgItems.size != 2)
            throw Exception("Wrong message format msg=${msg.value}")

        val index = msgItems[0]
        val delayStr = msgItems[1]
        logger.info { "crName=$crName Received message delay=$delayStr index=$index redelivery=${msg.redeliveryCount} action=start" }
        val delayMs = try {
            delayStr.toLong()
        } catch (e: Exception) {
            10000
        }
        logger.info { "crName=$crName Processing message delay=$delayStr index=$index redelivery=${msg.redeliveryCount}" }
        delay(delayMs)
        logger.info { "crName=$crName delay=$delayStr index=$index redelivery=${msg.redeliveryCount} action=finished" }
    }
}
