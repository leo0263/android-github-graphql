package com.leo0263.cobagithub.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leo0263.cobagithub.helper.GithubUserProfile
import com.leo0263.cobagithub.helper.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state

    private val _searchQueryFlow = MutableStateFlow("")
    var searchQuery by mutableStateOf("")
        private set

    private var endCursor: String? = null
    private var hasNextPage = false

    init {
        viewModelScope.launch {
            _searchQueryFlow
                .debounce(500)
                .collect {
                    fetchUsers()
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        _searchQueryFlow.value = query
    }

    private fun fetchUsers(after: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading()
            try {
                Log.d("dangdut", "fetchUsers -> (${searchQuery}, ${after})!")
                val response = userRepository.getSearchUser(query = searchQuery, endCursor = after)

                endCursor = response.data?.search?.pageInfo?.endCursor
                hasNextPage = response.data?.search?.pageInfo?.hasNextPage ?: false
                val fetchedUsers =
                    response.data?.search?.edges?.mapNotNull { it?.node?.onUser } ?: emptyList()
                Log.d("dangdut", "result -> (${response})!")
                Log.d("dangdut", "listOfUsers -> (${fetchedUsers})!")

                if (fetchedUsers.isEmpty()) {
                    isEmpty()
                } else {
                    _state.update { prevState ->
                        val newUsers = fetchedUsers.map {
                            GithubUserProfile(
                                id = it.id,
                                name = it.name ?: "",
                                avatarUrl = it.avatarUrl.toString(),
                                login = it.login,
                                bio = it.bio ?: "",
                            )
                        }
                        SearchUiState(
                            isLoading = false,
                            isEmpty = false,
                            isError = false,
                            users = prevState.users + newUsers,
                            hasNextPage = hasNextPage
                        )
                    }
                }
            } catch (e: Exception) {
                isError()
            }
        }
    }

    fun fetchNextPage() {
        if (hasNextPage) fetchUsers(endCursor)
    }

    private fun isLoading() {
        _state.update { prevState ->
            prevState.copy(
                isLoading = true,
                isEmpty = false,
                isError = false
            )
        }
    }

    private fun isEmpty() {
        _state.update { SearchUiState(isLoading = false, isEmpty = true) }
    }

    private fun isError() {
        _state.update { SearchUiState(isLoading = false, isEmpty = false, isError = true) }
    }
}
