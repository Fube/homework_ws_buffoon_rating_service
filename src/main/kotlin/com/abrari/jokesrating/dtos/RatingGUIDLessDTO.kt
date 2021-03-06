package com.abrari.jokesrating.dtos
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.NoArgsConstructor
import java.util.*

@NoArgsConstructor
data class RatingGUIDLessDTO(
    @JsonProperty(value = "jokeGUID")
    var jokeGUID: UUID?,
    @JsonProperty(value = "userGUID")
    var userGUID:String?,
    @JsonProperty(value = "opinion")
    var opinion:Boolean?,
){
    constructor(): this(null, null, null)
}