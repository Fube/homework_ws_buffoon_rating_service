package com.abrari.jokesrating.services

import com.abrari.jokesrating.dtos.RatingGUIDLessDTO
import com.abrari.jokesrating.dtos.RatingIDLessDTO
import com.abrari.jokesrating.models.Rating
import com.github.fge.jsonpatch.JsonPatch
import com.github.fge.jsonpatch.JsonPatchException
import java.sql.SQLException
import java.util.*
import kotlin.jvm.Throws

interface RatingService {

    fun getAllRatings():Iterable<RatingIDLessDTO>
    fun getAllRatings(page:Int, limit:Int):Map<String, Any>
    @Throws(SQLException::class) fun getRatingById(id:Int):Rating
    @Throws(SQLException::class) fun getRatingByGUID(guid:UUID):RatingIDLessDTO
    @Throws(SQLException::class) fun addRating(rating:RatingGUIDLessDTO):RatingIDLessDTO
    @Throws(SQLException::class) fun deleteRatingByGUID(guid:UUID):RatingIDLessDTO
    @Throws(SQLException::class, JsonPatchException::class) fun partiallyUpdateRatingByGUID(guid:UUID, patch: JsonPatch):RatingIDLessDTO
    @Throws(SQLException::class) fun updateRating(guid: UUID, guidLessDTO: RatingGUIDLessDTO):RatingIDLessDTO
}