import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "pce.pulsar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val pulsarVersion= "2.10.1"

dependencies {
    implementation("org.apache.pulsar:pulsar-client:${pulsarVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.3.8")
    implementation("io.insert-koin:koin-core:3.2.2")
    implementation("io.github.microutils:kotlin-logging:2.1.23")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}