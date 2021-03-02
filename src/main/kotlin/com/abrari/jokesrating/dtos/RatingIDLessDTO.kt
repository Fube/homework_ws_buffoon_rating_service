package com.abrari.jokesrating.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.NoArgsConstructor
import java.util.*

@NoArgsConstructor
data class RatingIDLessDTO(
    @JsonProperty(value = "jokeGUID")
    var jokeGUID: UUID,
    @JsonProperty(value = "guid")
    var guid: UUID = UUID.randomUUID(),
    @JsonProperty(value = "opinion")
    var opinion:Boolean
)