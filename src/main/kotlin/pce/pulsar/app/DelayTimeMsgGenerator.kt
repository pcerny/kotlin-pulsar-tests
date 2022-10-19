package pce.pulsar.app

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import pce.pulsar.pulsar.producer.PulsarProducer
import kotlin.random.Random

class DelayTimeMsgGenerator(private val name: String, private val producer: PulsarProducer) : MessageGenerator {
    private val logger = KotlinLogging.logger {}

    override suspend fun generate() =
        withContext(CoroutineName("DelayTimeGen-$name") + Dispatchers.Default) {
            var index = 0
            while (true) {
                delay(1000)
                val delayMsg = Random.nextInt(3000, 20000)
                logger.info { "name=$name Sending message index=$index delay=$delayMsg" }
                producer.send("$index-${delayMsg}")
                index += 1
            }
        }
}
