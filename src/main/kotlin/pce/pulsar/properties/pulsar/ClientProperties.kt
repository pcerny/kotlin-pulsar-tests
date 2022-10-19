package pce.pulsar.properties.pulsar

import pce.pulsar.properties.PropertiesReader

class ClientProperties(reader: PropertiesReader) {
    /**
     *  Note that pulsar client also support reloadable [org.apache.pulsar.client.api.ServiceUrlProvider] interface
     *  to dynamically provide a service URL.
     *  It can be coma separated value in case of non-kubernetes deployment
     */
    val serviceUrl: String = reader.getProperty("serviceUrl", "pulsar://localhost:6650")

    val adminUrl: String = reader.getProperty("adminUrl","http://localhost:8080")

    val operationTimeoutMs: Int = reader.getProperty("operationTimeoutMs","30000").toInt()

    val statsIntervalSeconds: Long = reader.getProperty("statsIntervalSeconds","60").toLong()

    /**
     * Thread pool used to manage the TCP connections with brokers.
     * If you're producing/consuming across many topics, you'll most likely be
     * interacting with multiple brokers and thus have multiple TCP connections opened.
     * Increasing the ioThreads count might remove the "single thread bottleneck",
     * though it would only be effective if such bottleneck is indeed present
     * (most of the time it will not be the case...).
     * You can check the CPU utilization in your consumer process,
     * across all threads, to see if there's any thread approaching 100% (of a single CPU core).
     */
    val ioThreads: Int = reader.getProperty("operationTimeoutMs","1").toInt()

    /**
     * Thread pool size when you are using the message listener in the consumer.
     * Typically this is the thread-pool used by application to process the messages
     * (unless it hops to a different thread). It might make sense to increase
     * the threads count here if the app processing is reaching the 1 CPU core limit.
     * In case that consumer message listeners are used than it make sense to set 1 thread per consumer.
     * In case that consumer(s) is/are defined in config and this property not specified it
     * will be set to # of consumers.
     */
    val listenerThreads: Int = reader.getProperty("listenerThreads","1").toInt()

    val connectionsPerBroker: Int = reader.getProperty("connectionsPerBroker","1").toInt()

    val useTcpNoDelay: Boolean = reader.getProperty("useTcpNoDelay","true").toBooleanStrict()

    val tlsTrustCertsFilePath: String = reader.getProperty("tlsTrustCertsFilePath","")

    val tlsAllowInsecureConnection: Boolean = reader.getProperty("tlsAllowInsecureConnection","false").toBooleanStrict()

    val tlsHostnameVerificationEnable: Boolean = reader.getProperty("tlsHostnameVerificationEnable","false").toBooleanStrict()

    val concurrentLookupRequest: Int = reader.getProperty("concurrentLookupRequest","5000").toInt()

    val maxLookupRequest: Int = reader.getProperty("maxLookupRequest","50000").toInt()

    val maxNumberOfRejectedRequestPerConnection: Int = reader.getProperty("maxNumberOfRejectedRequestPerConnection","50").toInt()

    val keepAliveIntervalSeconds: Int = reader.getProperty("keepAliveIntervalSeconds","30").toInt()

    val connectionTimeoutMs: Int = reader.getProperty("connectionTimeoutMs","10000").toInt()

    val startingBackoffIntervalMs: Long = reader.getProperty("startingBackoffIntervalMs","100").toLong()

    val maxBackoffIntervalMs: Long = reader.getProperty("maxBackoffIntervalMs","30000").toLong()
}