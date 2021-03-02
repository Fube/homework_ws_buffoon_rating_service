package com.abrari.jokesrating.utils

interface BiDirectionalMapping<A, B> {

    fun mapToLeft(b:B):A
    fun mapToRight(a:A):B
}