package pce.pulsar.pulsar.consumer

import org.apache.pulsar.client.api.Consumer
import org.apache.pulsar.client.api.Message

interface PulsarListenerExceptionHandler<T> {
    suspend operator fun invoke(exception: Throwable, msg: Message<T>, consumer: Consumer<T>) {
        consumer.negativeAcknowledge(msg)
    }
}