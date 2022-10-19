package pce.pulsar.pulsar

import org.apache.pulsar.client.api.PulsarClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import pce.pulsar.properties.pulsar.ClientProperties
import java.util.concurrent.TimeUnit

class Client(private val pulsarProperties: ClientProperties) {
    private fun buildPulsarClient(): PulsarClient =
        PulsarClient.builder()
            .ioThreads(pulsarProperties.ioThreads)
            .serviceUrl(pulsarProperties.serviceUrl)
            .listenerThreads(pulsarProperties.listenerThreads)
            .allowTlsInsecureConnection(pulsarProperties.tlsAllowInsecureConnection)
            .tlsTrustCertsFilePath(pulsarProperties.tlsTrustCertsFilePath)
            .connectionTimeout(pulsarProperties.connectionTimeoutMs, TimeUnit.MILLISECONDS)
            .connectionsPerBroker(pulsarProperties.connectionsPerBroker)
            .keepAliveInterval(pulsarProperties.keepAliveIntervalSeconds, TimeUnit.SECONDS)
            .startingBackoffInterval(pulsarProperties.startingBackoffIntervalMs, TimeUnit.MILLISECONDS)
            .maxBackoffInterval(pulsarProperties.maxBackoffIntervalMs, TimeUnit.MILLISECONDS)
            .maxLookupRequests(pulsarProperties.maxLookupRequest)
            .operationTimeout(pulsarProperties.operationTimeoutMs, TimeUnit.MILLISECONDS)
            .statsInterval(pulsarProperties.statsIntervalSeconds, TimeUnit.SECONDS)
            .maxConcurrentLookupRequests(pulsarProperties.concurrentLookupRequest)
            .enableTlsHostnameVerification(pulsarProperties.tlsHostnameVerificationEnable)
            .enableTcpNoDelay(pulsarProperties.useTcpNoDelay)
            .maxNumberOfRejectedRequestPerConnection(pulsarProperties.maxNumberOfRejectedRequestPerConnection)
            .build()

    val pulsarClient = buildPulsarClient()
}