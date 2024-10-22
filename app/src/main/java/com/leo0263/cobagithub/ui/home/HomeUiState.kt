package com.leo0263.cobagithub.ui.home

import com.leo0263.cobagithub.helper.GitHubUser

data class HomeUiState (
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val randomUser: GitHubUser? = null,
)
