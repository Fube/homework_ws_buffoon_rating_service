package com.abrari.jokesrating.converters

import javax.persistence.AttributeConverter

class IntegerToBooleanConverter:AttributeConverter<Boolean?, Int> {

    override fun convertToDatabaseColumn(attribute: Boolean?): Int {

        if(attribute == null) {
            return 0
        }
        else if(attribute) {
            return 1
        }
        return -1
    }

    override fun convertToEntityAttribute(dbData: Int?): Boolean? {

        if(dbData == 0) {
            return null
        }
        return dbData == 1
    }
}