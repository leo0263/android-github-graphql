package com.leo0263.cobagithub.ui.search

import com.leo0263.cobagithub.helper.GithubUserProfile

data class SearchUiState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = true,
    val isError: Boolean = false,
    val users: List<GithubUserProfile> = emptyList(),
    val hasNextPage: Boolean = false,
)
