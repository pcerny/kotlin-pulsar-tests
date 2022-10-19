package pce.pulsar.properties.pulsar

import org.apache.pulsar.client.api.SubscriptionType
import pce.pulsar.properties.PrefixPropertiesReader

class ConsumerProperties(reader: PrefixPropertiesReader) {
    val consumerName = reader.prefix

    val topic: String = reader.getProperty("topic", "default-topic")

    val subscriptionName: String = reader.getProperty("subscriptionName", "default-subscription")

    val subscriptionType: SubscriptionType = SubscriptionType.valueOf(reader.getProperty("subscriptionType", "Shared"))

    val negativeAckRedeliveryDelaySeconds: Long = reader.getProperty("negativeAckRedeliveryDelaySeconds", "60").toLong()

    val ackTimeoutMs: Long = reader.getProperty("ackTimeoutMs", "0").toLong()

    // Default is equal to pulsar consumer default value
    val receiverQueueSize: Int = reader.getProperty("receiverQueueSize", "1000").toInt()

    val ackHandlerName: String = reader.getProperty("ackHandlerName", "defaultAckHandler")
    val exceptionHandlerName: String = reader.getProperty("exceptionHandlerName", "defaultExcHandler")
    val loggingHandlerName: String = reader.getProperty("loggingHandlerName", "defaultLogHandler")
    val messageHandlerName: String = reader.getMandatoryProperty("messageHandlerName")
//    val listenerName: String? = getProperty("listenerName")

    /**
     * Dead letter enables to consume new msgs in case that cannot be consumed by standard consumer.
     * Beware that dead letters (currently) works only in [SubscriptionType.Shared] mode.
     * In case that this settings will be not set the deadLetter will not be enabled at all.
     */
    val deadLetter: DeadLetter = DeadLetter(reader.subPrefixReader("deadLetter"))

    class DeadLetter(subReader: PrefixPropertiesReader) {

        val enabled: Boolean = subReader.getProperty("enabled", "false").toBooleanStrict()

        /**
         * Defining how many retries (not acknowledged by the original consumer)
         * is necessary to route it to dead letter topic.
         */
        val maxRedeliverCount: Int = subReader.getProperty("maxRedeliverCount", "2").toInt()

        /**
         * Name of the topic which will be used for messages which was unable to deliver to original topic.
         */
        val topic: String = subReader.getProperty("topic", "dead-default-topic")
    }
}
