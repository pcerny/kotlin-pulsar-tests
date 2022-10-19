package pce.pulsar.app

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.koin.core.component.KoinComponent

class PulsarProducerUserGenerators : KoinComponent {
    private val logger = KotlinLogging.logger {}

    fun execute() = runBlocking(CoroutineName("MainExecutor")) {
        val msgGenerators: List<MessageGenerator> = getKoin().getAll()
        logger.info{ "Found ${msgGenerators.size} generators" }
        msgGenerators.forEach { generator ->
            logger.info{ "Executing generator $generator"}
            generator.generate()
        }
    }
}