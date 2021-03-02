package com.abrari.jokesrating.services

import com.abrari.jokesrating.exceptions.SQLNotFoundException
import com.abrari.jokesrating.models.Joke
import org.springframework.stereotype.Service
import java.util.*

@Service
class JokeServiceImpl(
    val jokeServiceCall:JokeServiceCall
):JokeService {

    override fun getJokeByGUID(guid: UUID):Joke {

        val response = jokeServiceCall.getJokeByGUID(guid).execute()

        if(response.code() == 404){
            throw SQLNotFoundException("Joke with guid $guid not found")
        }
        return response.body()!!
    }
}