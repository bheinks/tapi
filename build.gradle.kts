import java.util.Properties

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.postgresql)
        classpath(libs.flyway.gradle.plugin)
        classpath(libs.flyway.database.postgresql)
    }
}

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.jooq.codegen.gradle)
    alias(libs.plugins.flyway)
}

group = "dev.heinkel"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.h2)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.jooq)
    implementation(libs.postgresql)
    implementation(libs.flyway.database.postgresql)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}

flyway {
    val props = Properties()
    File(".env").inputStream().use { props.load(it) }

    val postgresHost: String = props.getProperty("POSTGRES_HOST")
    val postgresDatabase: String = props.getProperty("POSTGRES_DB")
    val postgresUser: String = props.getProperty("POSTGRES_USER")
    val postgresPassword: String = props.getProperty("POSTGRES_PASSWORD")

    url = "jdbc:postgresql://$postgresHost:5432/$postgresDatabase?user=$postgresUser&password=$postgresPassword"
}

jooq {
    configuration {
        jdbc {
            url = flyway.url
        }
    }
}

tasks.compileKotlin { dependsOn(tasks.jooqCodegen) }
tasks.jooqCodegen { dependsOn(tasks.flywayMigrate) }