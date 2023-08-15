package com.mad.assignmentFive.screens.postDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mad.assignmentFive.network.retrofit.eventWrapper.ResponseState
import com.mad.assignmentFive.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel@Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _deletePostState = MutableStateFlow<ResponseState<Boolean>>(ResponseState.Initial)
    val deletePostState: StateFlow<ResponseState<Boolean>>
        get() = _deletePostState

    fun deletePost(postId: Int, userId: Int) = viewModelScope.launch {
        _deletePostState.value = ResponseState.Loading
        userRepository.deletePost(postId, userId,
            onSuccess = {
                _deletePostState.value = ResponseState.Success(true)
            }, onFailure = { errorMsg ->
                _deletePostState.value = ResponseState.Failure(errorMessage = errorMsg)
            }
        )
    }

}