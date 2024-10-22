package com.leo0263.cobagithub.repository

import com.apollographql.apollo.api.ApolloResponse
import com.leo0263.cobagithub.SearchUsersQuery
import com.leo0263.cobagithub.network.GitHubService

class GithubRepository(private val gitHubService: GitHubService) {
    suspend fun fetchUsers(
        query: String,
        endCursor: String?
    ): ApolloResponse<SearchUsersQuery.Data> {
        return gitHubService.fetchUsers(query, endCursor)
    }
}
