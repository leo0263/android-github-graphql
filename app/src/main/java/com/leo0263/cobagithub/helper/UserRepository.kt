package com.leo0263.cobagithub.helper

import android.app.Application
import com.apollographql.apollo.api.ApolloResponse
import com.leo0263.cobagithub.SearchUsersQuery
import com.leo0263.cobagithub.UserDetailQuery
import com.leo0263.cobagithub.UserProfileQuery
import com.leo0263.cobagithub.network.ApolloClientInstance
import com.leo0263.cobagithub.network.GitHubServiceImpl

class UserRepository(application: Application) {
    private val apolloClient = ApolloClientInstance.getInstance()
    private val githubService = GitHubServiceImpl(apolloClient)
    //private val favoriteUserDao: FavoriteUserDao

    init {
        //val db = UserDatabase.getDatabase(application)!!
        //favoriteUserDao = db.favoriteUserDao()
    }

    suspend fun getSearchUser(query: String, endCursor: String?): ApolloResponse<SearchUsersQuery.Data> {
        return githubService.fetchUsers(query, endCursor)
    }

    suspend fun getUserDetail(username: String): ApolloResponse<UserDetailQuery.Data> {
        return githubService.fetchUserDetail(username)
    }

    suspend fun getUserProfile(username:String): ApolloResponse<UserProfileQuery.Data> {
        return githubService.fetchUserProfile(username)
    }

//    suspend fun checkUser(id: String): Int {
//        return favoriteUserDao.checkUser(id)
//    }
//
//    fun getFavoriteUser(): LiveData<List<FavoriteUser>> {
//        return favoriteUserDao.getFavoriteUser()
//    }
//
//    suspend fun addToFavorite(id: String, username: String, avatarUrl: String) {
//        val favoriteUser = FavoriteUser(id, username, avatarUrl)
//        return favoriteUserDao.addToFavorite(favoriteUser)
//    }
//
//    suspend fun removeFromFavorite(id: Int) {
//        favoriteUserDao.removeFromFavorite(id)
//    }
//
//    suspend fun getAllFavoriteUserIds(): List<String> {
//        return favoriteUserDao.getAllFavoriteUserIds()
//    }
}
