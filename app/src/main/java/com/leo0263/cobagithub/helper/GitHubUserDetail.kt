package com.leo0263.cobagithub.helper

import com.leo0263.cobagithub.UserDetailQuery

data class GitHubUserDetail(
    val id: String = "",
    val avatarUrl: String = "",
    val name: String = "",
    val login: String = "",
    val bio: String = "",
    val company: String = "",
    val location: String = "",
    val starredRepositories: Int = 0,
    val followers: Int = 0,
    val following: Int = 0,
    val repositoriesCount: Int = 0,
    val repositories: UserDetailQuery.Repositories? = null
)
