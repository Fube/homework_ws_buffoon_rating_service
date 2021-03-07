package com.abrari.jokesrating.repos

import com.abrari.jokesrating.dtos.RatingIDLessDTO
import com.abrari.jokesrating.models.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RatingsRepo:JpaRepository<Rating, Int> {

    @Query("from Rating where guid=(:guid)")
    fun findByGUID(@Param(value = "guid") guid: UUID): Optional<Rating>

    @Modifying
    @Query("delete from Rating where jokeGUID=(:guid)")
    fun deleteAllByJokeGUID(@Param(value = "guid") guid: UUID)

    @Query("from Rating where userGUID=(:guid)")
    fun findAllByUserGUID(@Param(value = "guid") guid: String): List<Rating>

    @Query("from Rating where userGUID=(:user) and jokeGUID=(:joke)")
    fun findByUserGUIDAndJokeGUIDComposite(
            @Param(value = "user") user: String?,
            @Param(value = "joke") joke: UUID?
    ): Rating?
}