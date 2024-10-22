package com.leo0263.cobagithub

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leo0263.cobagithub.network.ApolloClientInstance
import com.leo0263.cobagithub.network.GitHubServiceImpl
import com.leo0263.cobagithub.ui.theme.CobaGithubTheme
import com.leo0263.cobagithub.ui.bottomnav.BottomNavItem
import com.leo0263.cobagithub.ui.bottomnav.BottomNavigationBarView
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apolloClient = ApolloClientInstance.getInstance()
        val githubService = GitHubServiceImpl(apolloClient)

        // debug: try the graphQL connection
        lifecycleScope.launch {
            try {
                val response = githubService.fetchUsers("leo0263", null)
                val listOfUsers =
                    response.data?.search?.edges?.mapNotNull { it?.node?.onUser } ?: emptyList()
                val fetchedName = listOfUsers.getOrNull(0)?.name ?: "empty"
                Log.d("dangdut", fetchedName)
            } catch (e: Exception) {
                Log.e("Exception", "Error fetching users: ${e.message}")
            }
        }

        enableEdgeToEdge()
        setContent {
            // bottom navigation item initialization
            val homeTab = BottomNavItem(title = "Home", normalIcon = Icons.Default.Home, selectedIcon = Icons.Filled.Home)
            val searchTab = BottomNavItem(title = "Search", normalIcon = Icons.Default.Search, selectedIcon = Icons.Filled.Search)
            val favoritesTab = BottomNavItem(title = "Favorites", normalIcon = Icons.Default.Favorite, selectedIcon = Icons.Filled.Favorite)
            val bottomNavItems = listOf(homeTab, searchTab, favoritesTab)
            val navController = rememberNavController()

            CobaGithubTheme {
                Scaffold(
                    bottomBar = { BottomNavigationBarView(bottomNavItems, navController) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = homeTab.title, Modifier.padding(innerPadding)) {
                        composable(homeTab.title) {
                            Text(homeTab.title)
                        }
                        composable(searchTab.title) {
                            Text(searchTab.title)
                        }
                        composable(favoritesTab.title) {
                            Text(favoritesTab.title)
                        }
                    }
                }
            }
        }
    }
}
