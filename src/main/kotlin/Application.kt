package dev.heinkel

import dev.heinkel.model.UserRepository
import io.ktor.server.application.*
import io.ktor.server.plugins.di.dependencies

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()

    val userRepository: UserRepository by dependencies
    configureRouting(userRepository)
}
