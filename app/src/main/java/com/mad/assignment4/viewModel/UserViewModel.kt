package com.mad.assignment4.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mad.assignment4.network.retrofit.eventWrapper.ResponseState
import com.mad.assignment4.network.retrofit.model.post.PostItem
import com.mad.assignment4.network.retrofit.model.user.User
import com.mad.assignment4.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel()  {

    val posts = userRepository.getPosts().cachedIn(viewModelScope)

    private val _userState =
        MutableStateFlow<ResponseState<User>>(ResponseState.Initial)
    val userState: StateFlow<ResponseState<User>>
        get() = _userState

    suspend fun getUser() = viewModelScope.launch {
        _userState.value = ResponseState.Loading
       userRepository.getUser(
            onSuccess = {user ->
                _userState.value = ResponseState.Success(user)
            },
            onFailure = { errorMsg, statusCode ->
                _userState.value = ResponseState.Failure(errorMsg)
            }
        )
    }

    private val _postDetailsState = MutableStateFlow<ResponseState<PostItem>>(ResponseState.Initial)
    val postDetailsState: StateFlow<ResponseState<PostItem>>
        get() = _postDetailsState


    suspend fun getPostDetails(postId: Long) = viewModelScope.launch {
        _postDetailsState.value = ResponseState.Loading

         userRepository.getPostDetails(
            postId = postId,
            onSuccess = { post  ->
                _postDetailsState.value = ResponseState.Success(post)
            } ,
            onFailure = { errorMsg, statusCode ->
                _postDetailsState.value = ResponseState.Failure(errorMsg)
            }
        )
    }
}