package pce.pulsar.pulsar.consumer

import org.koin.core.qualifier.named
import org.koin.dsl.module

val handlersModule = module {
    factory<PulsarListenerAckHandler<String>>(named("defaultAckHandler")) {
        params -> DefaultAckHandler(params.get())
    }
    factory<PulsarListenerExceptionHandler<String>>(named("defaultExcHandler")) {
        params -> DefaultExceptionHandler(params.get())
    }
    factory<PulsarListenerMessageHandler<String>>(named("defaultLogHandler")) {
        params -> DefaultLoggingHandler(params.get())
    }
}