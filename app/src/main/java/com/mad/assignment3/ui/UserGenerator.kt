package com.mad.assignment3.ui

import com.mad.assignment3.utils.Util.generateAlphaNumericString
import com.mad.assignment3.utils.Util.generateAlphabeticString
import com.mad.assignment3.utils.Util.getEmailString
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class UserGenerator {

    fun createUserList(numberOfUsers: Int): List<User> {
        val userGenerator = generateSequence {
            User(
                userId = Random.nextLong(10000000,99999999),
                userName = generateAlphaNumericString(6),
                fullName = generateAlphabeticString(1,20),
                email = getEmailString()
            )
        }
       return userGenerator.take(numberOfUsers).toList()
    }
}