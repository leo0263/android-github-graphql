package com.leo0263.cobagithub.network

import com.apollographql.apollo.api.ApolloResponse
import com.leo0263.cobagithub.SearchUsersQuery


interface GitHubService {
    suspend fun fetchUsers(query: String, endCursor: String?): ApolloResponse<SearchUsersQuery.Data>
}
