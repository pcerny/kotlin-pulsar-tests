package pce.pulsar.app

import org.koin.core.qualifier.named
import org.koin.dsl.module
import pce.pulsar.pulsar.consumer.PulsarListenerMessageHandler

val appProducerModule = module {
//    single<MessageGenerator>(named("delayGen1")) {
//        DelayTimeMsgGenerator("delayGen1", get(named("test-shared")))
//    }

    single<MessageGenerator>(named("delayGenExclusive")) {
        DelayTimeMsgGenerator("delayGenExclusive", get(named("test-exclusive")))
    }
}

val appConsumerHandlersModule = module {
//    singleOf(::SharedWaitMessageHandler) {
//        named("sharedWaitMessageHandler")
//        bind<PulsarListenerMessageHandler<String>>()
//    }
    single<PulsarListenerMessageHandler<String>>(named("sharedWaitMessageHandler")) {
        SharedWaitMessageHandler()
    }
}
