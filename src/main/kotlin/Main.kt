import mu.KotlinLogging
import org.koin.core.context.startKoin
import pce.pulsar.app.*
import pce.pulsar.properties.pulsar.pulsarPropertiesModule
import pce.pulsar.pulsar.consumer.handlersModule
import pce.pulsar.pulsar.pulsarModule

fun main(args: Array<String>) {
    val logger = KotlinLogging.logger {}

    logger.info("Hello World!")

    startKoin {
        modules(handlersModule, pulsarPropertiesModule, appConsumerHandlersModule, pulsarModule, appProducerModule)
    }

    logger.info("Executing generators")

    val userGenerators = PulsarProducerUserGenerators()
    userGenerators.execute()
    logger.info("Finished")
}