package com.abrari.jokesrating.services

import com.abrari.jokesrating.models.Joke
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface JokeServiceCall {

    @GET("api/jokes/{guid}")
    fun getJokeByGUID(
        @Path("guid") guid:UUID,
    ):Call<Joke>
}