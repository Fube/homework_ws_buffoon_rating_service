package com.abrari.jokesrating

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JokesRatingApplication

val env: Dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load()

enum class EnvVars(val value:String){

    JOKE_SERVICE_BASE_URL(env["JOKE_SERVICE_BASE_URL"] ?: "localhost:8080")
}

fun main(args: Array<String>) {
    runApplication<JokesRatingApplication>(*args)
}
