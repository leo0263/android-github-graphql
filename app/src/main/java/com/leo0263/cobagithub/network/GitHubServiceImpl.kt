package com.leo0263.cobagithub.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import com.leo0263.cobagithub.SearchUsersQuery
import com.leo0263.cobagithub.UserDetailQuery

class GitHubServiceImpl(private val apolloClient: ApolloClient) : GitHubService {

    override suspend fun fetchUsers(
        query: String,
        endCursor: String?
    ): ApolloResponse<SearchUsersQuery.Data> {
        return apolloClient.query(SearchUsersQuery(query, Optional.present(endCursor)))
            .execute()
    }

    override suspend fun fetchUserDetail(username: String): ApolloResponse<UserDetailQuery.Data> {
        return apolloClient.query(UserDetailQuery(username)).execute()
    }
}
