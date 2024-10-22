package com.leo0263.cobagithub.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController,
) {
//    val randomUser by remember { mutableStateOf(viewModel.randomUser) }
//    var isLoading by remember { mutableStateOf(true) }
//    var isFavorite by remember { mutableStateOf(false) }

//    LaunchedEffect(randomUser) {
//        isLoading = false
//    }
//
//    LaunchedEffect(randomUser) {
//        randomstate.randomUser?.login?.let { id ->
//            isFavorite = viewModel.checkUser(id) > 0
//        }
//    }
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.getRandomUser()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home") })
        }
    ) { innerPadding ->
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
                    UserPortrait(state) {
                        navigateToDetail(navController, state.randomUser?.login ?: "", state.randomUser?.id ?: "", state.randomUser?.avatarUrl.toString())
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Row {
                        Button(onClick = { viewModel.getRandomUser() }) {
                            Text("Random!")
                        }
//                        Spacer(modifier = Modifier.width(16.dp))
//                        Button(onClick = { navigateToDetail(navController, state.randomUser?.login ?: "", state.randomUser?.id ?: "", state.randomUser?.avatarUrl.toString()) }) {
//                            Text("Details")
//                        }
//                        Spacer(modifier = Modifier.width(16.dp))
//                        Button(onClick = {
//                            viewModel.addToFavorite(state.randomUser?.id ?: "", state.randomUser?.login?: "", state.randomUser?.avatarUrl.toString())
//                            isFavorite = !isFavorite
//                            viewModel.getRandomUser()
//                        }) {
//                            Text(if (isFavorite) "Remove Favorite" else "Add Favorite")
//                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserPortrait(state: HomeUiState, onClick: () -> Unit = {}) {
    Column (
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
        Text(state.randomUser?.name ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(state.randomUser?.login ?: "", fontSize = 16.sp)
        Text(state.randomUser?.company ?: "", fontSize = 14.sp)
        Text(state.randomUser?.location ?: "", fontSize = 14.sp)
    }
}

fun navigateToDetail(navController: NavHostController, login: String, id: String, avatarUrl: String) {
    navController.navigate("detail/$login/$id/$avatarUrl")
}



//@Composable
//fun viewModel(repository: UserRepository = UserRepository(LocalContext.current.applicationContext as Application)): HomeViewModel {
//    val factory = com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory.getInstance(LocalContext.current.applicationContext as Application, repository)
//    return viewModel(factory = factory)
//}
