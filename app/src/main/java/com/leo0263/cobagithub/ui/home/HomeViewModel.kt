package com.leo0263.cobagithub.ui.home

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

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

//    fun addToFavorite(id: String, username: String, avatarUrl: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            userRepository.addToFavorite(id, username, avatarUrl)
//        }
//    }

//    suspend fun checkUser(id: String): Int {
//        return userRepository.checkUser(id)
//    }

    fun getRandomUser() {
        isLoading()
        Log.d("dangdut", "getRandomUser()!")
        val query = 'a'.toString() // TODO: update this random query logic
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //TODO: val favoriteUserIds = userRepository.getAllFavoriteUserIds()
                val favoriteUserIds: List<String> = emptyList()
                val response = userRepository.getSearchUser(query, null)
                if (response.data != null) {
                    val fetchedUsers = response.data?.search?.edges?.mapNotNull { it?.node?.onUser } ?: emptyList()
                    val filteredUsers = fetchedUsers.filterNot { fetchedUser ->
                        favoriteUserIds.any { favoriteUserId ->
                            fetchedUser.id == favoriteUserId.toString()
                        }
                    }
                    if (filteredUsers.isNotEmpty()) {
                        Log.d("dangdut", "$query (${filteredUsers.count()})")
                        val randomUser = filteredUsers.random()
                        refreshUserDetail(randomUser.login)
                    } else {
                        Log.d("dangdut", "all fetched users is already on favorite list, re-fetch!")
                        getRandomUser()
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

    private suspend fun refreshUserDetail(username: String) {
        try {
            val response = userRepository.getUserDetail(username)
            if (response.data != null) {
                val randomUser = (response.data?.repositoryOwner?.onUser)
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isError = false,
                        randomUser = GitHubUser(
                            id = randomUser?.id ?: "",
                            name = randomUser?.name ?: "",
                            avatarUrl = randomUser?.avatarUrl.toString(),
                            login = randomUser?.login ?: "",
                            bio = randomUser?.bio ?: "",
                            company = randomUser?.company ?: "",
                            followers = randomUser?.followers?.totalCount ?: 0,
                            following = randomUser?.following?.totalCount ?: 0,
                            starredRepositories = randomUser?.starredRepositories?.totalCount ?: 0,
                        )
                    )
                }

                Log.d("dangdut", "randomUser.postValue: ${response.data?.repositoryOwner?.onUser}")
            } else {
                Log.e("API Error", response.errors.toString())
                isError()
            }
        } catch (e: Exception) {
            Log.e("API Failure", "Error: ${e.message}")
            isError()
        }
    }

    private fun isLoading() {
        _state.update { HomeUiState(isLoading = true, isError = false) }
    }

    private fun isError() {
        _state.update { HomeUiState(isLoading = false, isError = true) }
    }
}
