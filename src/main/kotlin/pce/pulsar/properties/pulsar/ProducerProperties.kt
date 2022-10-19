package pce.pulsar.properties.pulsar

import org.apache.pulsar.client.api.SubscriptionType
import pce.pulsar.properties.PrefixPropertiesReader

class ProducerProperties(reader: PrefixPropertiesReader) {

    val topic: String = reader.getMandatoryProperty("topic")

    val subscriptionType: SubscriptionType = SubscriptionType.valueOf(
        reader.getProperty("subscriptionType", "Shared")
    )
}