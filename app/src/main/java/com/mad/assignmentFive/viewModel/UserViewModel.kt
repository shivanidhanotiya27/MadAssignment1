package com.mad.assignmentFive.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mad.assignmentFive.network.model.post.Post
import com.mad.assignmentFive.network.model.user.User
import com.mad.assignmentFive.network.model.userPost.UPost
import com.mad.assignmentFive.network.retrofit.eventWrapper.ResponseState
import com.mad.assignmentFive.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val posts = userRepository.getPosts().cachedIn(viewModelScope)
    fun getUserPosts(userId: Int): Flow<PagingData<Post>> {
        return userRepository.getUserPosts(userId).cachedIn(viewModelScope)
    }


    val localUserStatus = userRepository.getUserStatus()

    private val _userState = MutableStateFlow<ResponseState<User>>(ResponseState.Initial)
    val userState: StateFlow<ResponseState<User>>
        get() = _userState

    fun getUser() = viewModelScope.launch {
        _userState.value = ResponseState.Loading
        userRepository.getUser(
            onSuccess = { user ->
                _userState.value = ResponseState.Success(user)
            },
            onFailure = { errorMsg ->
                _userState.value = ResponseState.Failure(errorMsg)
            }
        )
    }

    fun addUserToDB(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUserToDB(user)
        }
    }

    fun getUserFromDB(): Flow<User> {
        return userRepository.getUserFromDB()
    }

    private val _postState = MutableStateFlow<ResponseState<UPost>>(ResponseState.Initial)
    val postState: StateFlow<ResponseState<UPost>>
        get() = _postState

    fun getPostByPostId(postId: Int) = viewModelScope.launch {
        _postState.value = ResponseState.Loading

        userRepository.getPostByPostId(postId = postId,
            onSuccess = { post ->
                _postState.value = ResponseState.Success(post)
            },
            onFailure = { errorMsg ->
                _postState.value = ResponseState.Failure(errorMessage = errorMsg)
            }
        )
    }
}