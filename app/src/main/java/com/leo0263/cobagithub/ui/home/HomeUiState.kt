package com.leo0263.cobagithub.ui.home

import com.leo0263.cobagithub.helper.GitHubUserDetail

data class HomeUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val randomUser: GitHubUserDetail? = null,
)
