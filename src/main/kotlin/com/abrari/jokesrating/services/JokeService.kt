package com.abrari.jokesrating.services

import com.abrari.jokesrating.models.Joke
import java.sql.SQLException
import java.util.*

/**
 * Why is there only a read here?
 * I put everything I need, not everything I CAN
 */
interface JokeService {

    @Throws(SQLException::class)fun getJokeByGUID(guid:UUID):Joke
}