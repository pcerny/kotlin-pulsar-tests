package pce.pulsar.pulsar.consumer

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.apache.pulsar.client.api.*
import org.apache.pulsar.client.api.ConsumerBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pce.pulsar.properties.PrefixPropertiesReader
import pce.pulsar.properties.PropertiesReader
import pce.pulsar.properties.pulsar.ConsumerProperties
import pce.pulsar.pulsar.Client
import java.net.InetAddress
import java.util.concurrent.TimeUnit

class ConsumerFactory(
    private val pulsarClient: Client,
    private val consumersProperties: PropertiesReader
) : KoinComponent {
    private val logger = KotlinLogging.logger {}

    init {
        runBlocking(CoroutineName("consumersInit")) {
            createConsumers()
        }
    }

    private suspend fun createConsumers() {
        val consumers = consumersProperties.uniquePrefixes().map { prefix ->
            logger.info { "Creating consumer prefix=$prefix"}
            prefix to createConsumer(prefix)
        }

        val consumersModule = module {
            consumers.forEach { (prefix, consumer) ->
                logger.info { "Registering consumer prefix=$prefix consumer=$consumer"}
                single(named(prefix)) { consumer }
            }
        }

        loadKoinModules(consumersModule)
    }

    suspend fun createConsumer(prefix: String): Consumer<String> =
        createConsumer(ConsumerProperties(PrefixPropertiesReader(prefix, consumersProperties)))

    suspend fun createConsumer(properties: ConsumerProperties): Consumer<String> {
        return pulsarClient.pulsarClient.newConsumer(Schema.STRING)
            // subscription name is (by default) specific by application type
            .subscriptionName(properties.subscriptionName)
            .subscriptionType(properties.subscriptionType)
            .consumerName("${properties.consumerName}-${InetAddress.getLocalHost().hostName}")
            .messageListener(prepareMessageListener(properties))
//            .intercept(TracingConsumerInterceptor())
            .topic(properties.topic)
            .negativeAckRedeliveryDelay(properties.negativeAckRedeliveryDelaySeconds, TimeUnit.SECONDS)
            .receiverQueueSize(properties.receiverQueueSize)
            .apply { deadLetterBuilder(this@apply, properties) }
            .ackTimeout(properties.ackTimeoutMs, TimeUnit.MILLISECONDS)
            .subscribeAsync().await()
    }

    private fun prepareMessageListener(
        properties: ConsumerProperties,
    ): MessageListener<String> {
        val consumerIdParam = parametersOf(properties.consumerName)
        val messageHandlers = listOf(
            get<PulsarListenerMessageHandler<String>>(named(properties.loggingHandlerName)) { consumerIdParam },
            get(named(properties.messageHandlerName))
        )

        return PulsarListener(
            get(named(properties.ackHandlerName)) { consumerIdParam },
            get(named(properties.exceptionHandlerName)) { consumerIdParam },
            messageHandlers
        )
    }

    private fun deadLetterBuilder(builder: ConsumerBuilder<String>, properties: ConsumerProperties) {
        properties.takeIf { it.deadLetter.enabled && it.subscriptionType == SubscriptionType.Shared }?.let {
            val deadLetter = it.deadLetter
            builder.deadLetterPolicy(
                DeadLetterPolicy.builder()
                    .maxRedeliverCount(deadLetter.maxRedeliverCount)
                    .deadLetterTopic(deadLetter.topic)
                    .build()
            )
        }
    }
}

