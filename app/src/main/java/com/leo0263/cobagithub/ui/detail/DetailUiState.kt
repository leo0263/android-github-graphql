package com.leo0263.cobagithub.ui.detail

import com.leo0263.cobagithub.helper.GitHubUserDetail

data class  DetailUiState (
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val userDetail: GitHubUserDetail? = null,
)
