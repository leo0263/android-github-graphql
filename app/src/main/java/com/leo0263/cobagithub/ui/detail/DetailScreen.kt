package com.leo0263.cobagithub.ui.detail

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.leo0263.cobagithub.ui.home.HomeViewModel

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = viewModel(),
    userLogin: String,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserDetail(userLogin = userLogin)
    }

    Scaffold { innerPadding ->
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            state.let { state ->
                // UserDetail(state)
                // UserRepositories(state)
            }
        }
    }
}
