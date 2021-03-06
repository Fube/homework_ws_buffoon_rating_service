package com.abrari.jokesrating.controllers

import com.abrari.jokesrating.dtos.RatingGUIDLessDTO
import com.abrari.jokesrating.dtos.RatingIDLessDTO
import com.abrari.jokesrating.services.RatingService
import com.github.fge.jsonpatch.JsonPatch
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/api/ratings")
@RestController
class RatingsController(
    val ratingService: RatingService
) {


    @GetMapping
    private fun getAllRatings(
        @RequestParam(required = false, defaultValue = "1") page:Int,
        @RequestParam(required = false, defaultValue = "10") limit:Int,
    ) = ResponseEntity.ok(ratingService.getAllRatings(page, limit))

    @GetMapping("/{guid}")
    private fun getRating(
        @PathVariable guid:UUID
    ) = ResponseEntity.ok(ratingService.getRatingByGUID(guid))

    @GetMapping("/user/{guid}")
    private fun getRating(
            @PathVariable guid:String
    ) = ResponseEntity.ok(ratingService.getAllRatingsForUser(guid))

    @PostMapping
    private fun addRating(
        @RequestBody rating:RatingGUIDLessDTO
    ) = ResponseEntity.ok(ratingService.addRating(rating))

    @PutMapping("/{guid}")
    private fun updateRating(
        @PathVariable guid:UUID,
        @RequestBody rating:RatingGUIDLessDTO
    ) = ResponseEntity.ok(ratingService.updateRating(guid, rating))

    @PatchMapping("/{guid}")
    private fun partiallyUpdateRating(
        @PathVariable guid:UUID,
        @RequestBody patch:JsonPatch,
    ) = ResponseEntity.ok(ratingService.partiallyUpdateRatingByGUID(guid, patch))

    @DeleteMapping("/{guid}")
    private fun deleteRating(
        @PathVariable guid: UUID
    ) = ResponseEntity.ok(ratingService.deleteRatingByGUID(guid))
}