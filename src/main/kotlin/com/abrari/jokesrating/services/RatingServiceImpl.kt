package com.abrari.jokesrating.services

import com.abrari.jokesrating.*
import com.abrari.jokesrating.dtos.RatingGUIDLessDTO
import com.abrari.jokesrating.dtos.RatingIDLessDTO
import com.abrari.jokesrating.exceptions.SQLNotFoundException
import com.abrari.jokesrating.models.Rating
import com.abrari.jokesrating.repos.RatingsRepo
import com.abrari.jokesrating.utils.BiDirectionalMapping
import com.abrari.jokesrating.utils.BiDirectionalMappingImpl
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.JsonPatch
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.NullPointerException
import java.sql.SQLIntegrityConstraintViolationException
import java.util.*

@Service
class RatingServiceImpl(
    val ratingsRepo: RatingsRepo,
    val ratingToIDLess: BiDirectionalMapping<Rating, RatingIDLessDTO>,
    val ratingToGUIDLess: BiDirectionalMapping<Rating, RatingGUIDLessDTO>,
    val objectMapper: ObjectMapper,
    val jokeService: JokeService
):RatingService {

    override fun getAllRatings(): Iterable<RatingIDLessDTO> {
        return ratingsRepo
                .findAll()
                .map(ratingToIDLess::mapToRight)
    }

    override fun getAllRatings(page: Int, limit: Int): Map<String, Any> {
        val (content, number, totalElements, totalPages) =  ratingsRepo.findAll(PageRequest.of(page-1, limit)).map(ratingToIDLess::mapToRight)

        return hashMapOf(
            "ratings" to content,
            "currentPage" to number+1,
            "totalItems" to totalElements,
            "totalPages" to totalPages
        )
    }

    override fun getAllRatingsForUser(guid: String): Iterable<RatingIDLessDTO> {
        return ratingsRepo
                .findAllByUserGUID(guid)
                .map(ratingToIDLess::mapToRight)
    }

    override fun getRatingById(id: Int): Rating = ratingsRepo.findById(id).orElseThrow { throw SQLNotFoundException("Rating not found") }

    override fun getRatingByGUID(guid: UUID): RatingIDLessDTO = ratingToIDLess.mapToRight(ratingsRepo.findByGUID(guid).orElseThrow{ throw SQLNotFoundException("Rating not found") })

    // Upsert
    override fun addRating(rating: RatingGUIDLessDTO): RatingIDLessDTO {

        val asRating:Rating = ratingToGUIDLess.mapToLeft(rating)
        val userUUID = rating.userGUID
        val jokeUUID = rating.jokeGUID
        val existing = ratingsRepo.findByUserGUIDAndJokeGUIDComposite(userUUID, jokeUUID);
        if(existing != null) {

            return updateRating(existing.guid, rating)
        }

        rating.jokeGUID?.let { jokeService.getJokeByGUID(it) }
        asRating.guid = UUID.randomUUID() // Shouldn't have to do this but I guess I do

        val createdRating:Rating = ratingsRepo.save(asRating)

        return ratingToIDLess.mapToRight(createdRating)
    }

    override fun deleteRatingByGUID(guid: UUID): RatingIDLessDTO {

        val rating = ratingsRepo.findByGUID(guid).orElseThrow { throw SQLNotFoundException("Rating not found") }
        ratingsRepo.deleteById(rating.id)
        return ratingToIDLess.mapToRight(rating)
    }

    override fun partiallyUpdateRatingByGUID(guid: UUID, patch: JsonPatch) :RatingIDLessDTO {

        val rating:Rating = ratingsRepo.findByGUID(guid).orElseThrow { throw SQLNotFoundException("Rating not found") }
        val (originalID, originalJokeGUID, originalGUID) = rating

        val patched: JsonNode = patch.apply(objectMapper.convertValue(rating, JsonNode::class.java))

        val backToRating = objectMapper.convertValue(patched, Rating::class.java)

        if(backToRating.guid != originalGUID)throw SQLIntegrityConstraintViolationException("Cannot set GUID explicitly")
        if(backToRating.jokeGUID != originalJokeGUID)throw SQLIntegrityConstraintViolationException("Cannot update Joke GUID explicitly")
        if(backToRating.id != originalID)throw SQLIntegrityConstraintViolationException("Cannot set ID explicitly")

        ratingsRepo.save(backToRating)

        return ratingToIDLess.mapToRight(backToRating)
    }

    override fun updateRating(guid: UUID, guidLessDTO: RatingGUIDLessDTO): RatingIDLessDTO {

        val rating:Rating = ratingsRepo.findByGUID(guid).orElseThrow { throw SQLNotFoundException("Rating not found") }
        val mapped = ratingToGUIDLess.mapToLeft(guidLessDTO)

        mapped.id = rating.id
        mapped.guid = rating.guid

        if(mapped.jokeGUID != rating.jokeGUID)throw SQLIntegrityConstraintViolationException("Cannot update Joke GUID explicitly")

        ratingsRepo.save(mapped)

        return ratingToIDLess.mapToRight(mapped)
    }

    @Transactional
    override fun deleteAllFor(guid: UUID) {
        ratingsRepo.deleteAllByJokeGUID(guid)
    }


}