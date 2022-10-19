package pce.pulsar.app

interface MessageGenerator {
    suspend fun generate()
}