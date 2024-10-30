package com.leo0263.cobagithub

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.leo0263.cobagithub.helper.UserRepository
import com.leo0263.cobagithub.ui.bottomnav.BottomNavItem
import com.leo0263.cobagithub.ui.bottomnav.BottomNavigationBarView
import com.leo0263.cobagithub.ui.detail.DetailScreen
import com.leo0263.cobagithub.ui.detail.DetailViewModel
import com.leo0263.cobagithub.ui.home.HomeScreen
import com.leo0263.cobagithub.ui.home.HomeViewModel
import com.leo0263.cobagithub.ui.search.SearchScreen
import com.leo0263.cobagithub.ui.search.SearchViewModel
import com.leo0263.cobagithub.ui.theme.CobaGithubTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            // bottom navigation item initialization
            val homeTab = BottomNavItem(title = "Home", normalIcon = Icons.Default.Home, selectedIcon = Icons.Filled.Home)
            val searchTab = BottomNavItem(title = "Search", normalIcon = Icons.Default.Search, selectedIcon = Icons.Filled.Search)
            val favoritesTab = BottomNavItem(title = "Favorites", normalIcon = Icons.Default.Favorite, selectedIcon = Icons.Filled.Favorite)
            val bottomNavItems = listOf(homeTab, searchTab, favoritesTab)
            val navController = rememberNavController()

            CobaGithubTheme {
                val userRepository = UserRepository(this.application)
                val homeViewModel = HomeViewModel(userRepository)
                val detailViewModel = DetailViewModel(userRepository)
                val searchViewModel = SearchViewModel(userRepository)

                Scaffold(
                    bottomBar = { BottomNavigationBarView(bottomNavItems, navController) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = homeTab.title, Modifier.padding(innerPadding)) {
                        composable(homeTab.title) { HomeScreen(homeViewModel, navController) }
                        composable(searchTab.title) { SearchScreen(searchViewModel, navController) } // TODO: create search view
                        composable(favoritesTab.title) { Text(favoritesTab.title) } // TODO: create favorite view
                        composable(
                            route = "detail/{login}",
                            arguments = listOf(
                                navArgument("login") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val login = backStackEntry.arguments?.getString("login") ?: ""
                            DetailScreen(detailViewModel, login)
                        }
                    }
                }
            }
        }
    }
}
