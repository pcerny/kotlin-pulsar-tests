package pce.pulsar.pulsar.consumer

class DefaultAckHandler(
    private val consumerIdentification: String
) : PulsarListenerAckHandler<String> {
    override fun toString(): String {
        return "$consumerIdentification-defaultPulsarAckHandler"
    }
}
