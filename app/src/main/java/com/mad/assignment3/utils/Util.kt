package com.mad.assignment3.utils

import kotlin.random.Random

object Util {

    fun generateAlphaNumericString(length: Int): String {
        val alphaNumericString = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length).map {
            alphaNumericString[Random.nextInt(alphaNumericString.size)]
        }.joinToString("")
    }

    fun generateAlphabeticString(minLength: Int, maxLength: Int): String {
        val alphabeticString = ('a'..'z') + ('A'..'Z')
        val length = Random.nextInt(minLength, maxLength + 1)
        return (1..length)
            .map {
                alphabeticString[Random.nextInt(alphabeticString.size)]
            }.joinToString("")

    }

    fun getEmailString(): String {
        val username = generateAlphaNumericString(8)
        return "$username@gmail.com"
    }
}