package com.abrari.jokesrating.utils

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BiDirectionalMappingImpl<A,B>:BiDirectionalMapping<A,B> {

    @Autowired
    private lateinit var modelMapper:ModelMapper
    var classOfA: Class<A>? = null
    var classOfB: Class<B>? = null

    override fun mapToLeft(b: B): A = modelMapper.map(b, classOfA)

    override fun mapToRight(a: A): B = modelMapper.map(a, classOfB)
}