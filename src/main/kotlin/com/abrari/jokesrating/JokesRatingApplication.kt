package com.abrari.jokesrating

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JokesRatingApplication

enum class EnvVars(val value:String){

    JOKE_SERVICE_BASE_URL(dotenv().get("JOKE_SERVICE_BASE_URL") ?: "localhost:8080")
}

fun main(args: Array<String>) {
    runApplication<JokesRatingApplication>(*args)
}
