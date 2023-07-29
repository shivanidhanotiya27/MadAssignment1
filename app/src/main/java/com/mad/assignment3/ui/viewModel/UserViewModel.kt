package com.mad.assignment3.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mad.assignment3.ui.User
import com.mad.assignment3.ui.UserGenerator
import com.mad.assignment3.ui.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userGenerator: UserGenerator
) : ViewModel() {

    val userStatus = userRepository.getUserStatus()

    fun addUsers(numberOfUsers: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUsers(userGenerator.createUserList(numberOfUsers))
        }
    }

    fun addUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUsers(userGenerator.createUserList(1))
        }
    }

    fun getUserById(userId: Long): Flow<User> {
        return userRepository.getUser(userId)
    }

    fun getUserList(): Flow<List<User>> {
        return userRepository.getUserList()
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteUser(user)
        }
    }
}