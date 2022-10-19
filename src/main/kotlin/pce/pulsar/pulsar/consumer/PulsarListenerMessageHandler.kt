package pce.pulsar.pulsar.consumer

import org.apache.pulsar.client.api.Message

interface PulsarListenerMessageHandler<T> {
    suspend fun processMessage(msg: Message<T>)
}