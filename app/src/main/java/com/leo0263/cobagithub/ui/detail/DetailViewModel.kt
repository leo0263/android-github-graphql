package com.leo0263.cobagithub.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leo0263.cobagithub.helper.GitHubUser
import com.leo0263.cobagithub.helper.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    fun getUserDetail(userLogin: String) {
        isLoading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("dangdut", "getUserDetail()!")
                val response = userRepository.getUserDetail(userLogin)
                if (response.data != null) {
                    val user = (response.data?.repositoryOwner?.onUser)
                    Log.d("dangdut", "user = $user")
                    _state.update {
                        DetailUiState(
                            isLoading = false,
                            isError = false,
                            userDetail = GitHubUser(
                                id = user?.id ?: "",
                                name = user?.name ?: "",
                                avatarUrl = user?.avatarUrl.toString(),
                                login = user?.login ?: "",
                                bio = user?.bio ?: "",
                                company = user?.company ?: "",
                                followers = user?.followers?.totalCount ?: 0,
                                following = user?.following?.totalCount ?: 0,
                                starredRepositories = user?.starredRepositories?.totalCount ?: 0,
                            )
                        )
                    }
                } else {
                    Log.e("API Error", response.errors.toString())
                    isError()
                }
            } catch (e: Exception) {
                Log.e("API Failure", "Error: ${e.message}")
                isError()
            }
        }
    }

    private fun isLoading() {
        _state.update { DetailUiState(isLoading = true, isError = false) }
    }

    private fun isError() {
        _state.update { DetailUiState(isLoading = false, isError = true) }
    }
}
