package com.abrari.jokesrating.models

import com.abrari.jokesrating.converters.IntegerToBooleanConverter
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.NoArgsConstructor
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@NoArgsConstructor
@Entity
@Table(name = "ratings")
data class Rating(
    @PrimaryKeyJoinColumn
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,
    @Column(name = "joke_guid", nullable = false)
    var jokeGUID:UUID,
    @GeneratedValue(generator = "uuid2") @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
    var guid:UUID = UUID.randomUUID(),
    @Column(name = "user_guid", nullable = false)
    var userGUID:String?,
    @Convert(converter = IntegerToBooleanConverter::class)
    @Column(name = "opinion", nullable = true)
    var opinion:Boolean?
)