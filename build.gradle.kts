
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.2.2"
}

group = "com.nishantpardamwar"
version = "0.0.1"

application {
    mainClass = "com.nishantpardamwar.ApplicationKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("io.modelcontextprotocol:kotlin-sdk:0.5.0")

    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
