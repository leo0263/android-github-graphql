package com.leo0263.cobagithub.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRandomUser()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.let { state ->
                    UserPortrait(state, onClick = {
                        navigateToDetail(navController, state.randomUser?.login ?: "")
                    })
                    Spacer(modifier = Modifier.height(32.dp))
                    Row {
                        Button(onClick = { viewModel.getRandomUser() }) {
                            Text("Random!")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserPortrait(state: HomeUiState, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = state.randomUser?.avatarUrl,
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(state.randomUser?.login ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(state.randomUser?.name ?: "", fontSize = 16.sp)
        Text(state.randomUser?.company ?: "", fontSize = 14.sp)
        Text(state.randomUser?.location ?: "", fontSize = 14.sp)
    }
}

fun navigateToDetail(navController: NavHostController, login: String) {
    Log.d("dangdut", "navigate -> (${login})!")
    navController.navigate("detail/$login")
}
