package com.leo0263.cobagithub.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.leo0263.cobagithub.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

object ApolloClientInstance {
    private val githubToken: String = BuildConfig.GITHUB_TOKEN

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val builder: Request.Builder =
                original.newBuilder().method(original.method, original.body)
            builder.header("Authorization", "bearer $githubToken")
            chain.proceed(builder.build())
        }
        .build()

    fun getInstance(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }
}
