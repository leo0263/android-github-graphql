package com.leo0263.cobagithub.helper

data class GitHubUser(
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
)
