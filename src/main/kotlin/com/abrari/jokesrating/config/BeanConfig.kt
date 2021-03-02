package com.abrari.jokesrating.config

import com.abrari.jokesrating.EnvVars
import com.abrari.jokesrating.dtos.RatingGUIDLessDTO
import com.abrari.jokesrating.dtos.RatingIDLessDTO
import com.abrari.jokesrating.models.Rating
import com.abrari.jokesrating.services.JokeServiceCall
import com.abrari.jokesrating.utils.BiDirectionalMapping
import com.abrari.jokesrating.utils.BiDirectionalMappingImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.cdimascio.dotenv.dotenv
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
class BeanConfig {

    @Bean
    fun getModelMapper():ModelMapper = ModelMapper()

    @Bean
    fun getObjectMapper():ObjectMapper = ObjectMapper()

    @Bean
    fun getRatingIDLessDTOMapping():BiDirectionalMapping<Rating, RatingIDLessDTO> {

        val temp = BiDirectionalMappingImpl<Rating, RatingIDLessDTO>()
        temp.classOfA = Rating::class.java
        temp.classOfB = RatingIDLessDTO::class.java

        return temp;
    }

    @Bean
    fun getRatingGUIDLessDTOMapping():BiDirectionalMapping<Rating, RatingGUIDLessDTO> {

        val temp = BiDirectionalMappingImpl<Rating, RatingGUIDLessDTO>()
        temp.classOfA = Rating::class.java
        temp.classOfB =RatingGUIDLessDTO::class.java

        return temp;
    }

    @Bean
    fun getJokeServiceCall():JokeServiceCall {

        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl(EnvVars.JOKE_SERVICE_BASE_URL.value)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        return retrofit.create(JokeServiceCall::class.java)
    }
}