package com.abrari.jokesrating
import org.springframework.data.domain.Page

operator fun <T> Page<T>.component1(): List<T> = content
operator fun <T> Page<T>.component2(): Int = number
operator fun <T> Page<T>.component3(): Long = totalElements
operator fun <T> Page<T>.component4(): Int = totalPages
