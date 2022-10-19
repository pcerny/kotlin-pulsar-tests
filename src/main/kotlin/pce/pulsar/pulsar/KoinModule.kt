package pce.pulsar.pulsar

import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pce.pulsar.pulsar.consumer.ConsumerFactory
import pce.pulsar.pulsar.producer.ProducerFactory

val pulsarModule = module {
    singleOf(::Client) { createdAtStart() }
    single(createdAtStart = true) {
        ProducerFactory(get(), get(named("producers")))
    }
    single(createdAtStart = true) { ConsumerFactory(get(), get(named("consumers"))) }
}