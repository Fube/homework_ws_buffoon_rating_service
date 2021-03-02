package com.abrari.jokesrating.models

import java.util.*


data class Joke(
    var guid: UUID? = null,
    var setup: String? = null,
    var punchline: String? = null,
    var category: Category? = null,
)