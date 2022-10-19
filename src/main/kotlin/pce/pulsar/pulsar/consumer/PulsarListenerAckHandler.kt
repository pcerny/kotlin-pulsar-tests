package pce.pulsar.pulsar.consumer

import kotlinx.coroutines.future.await
import org.apache.pulsar.client.api.Consumer
import org.apache.pulsar.client.api.Message

interface PulsarListenerAckHandler<T> {
    suspend operator fun invoke(msg: Message<T>, consumer: Consumer<T>) {
        consumer.acknowledgeAsync(msg).await()
    }
}