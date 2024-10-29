package com.leo0263.cobagithub.ui.detail

import com.leo0263.cobagithub.helper.GitHubUser

data class  DetailUiState (
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val userDetail: GitHubUser? = null,
)
