package com.leo0263.cobagithub.ui.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.leo0263.cobagithub.helper.GithubUserProfile

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Search GitHub username") },
            singleLine = true
        )
        if (state.isLoading && state.users.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        if (state.isEmpty) {
            Text(
                "No results found",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        if (state.isError) {
            Text(
                "Error loading data",
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        LazyColumn {
            items(state.users.size) { index ->
                val user = state.users[index]
                UserListItem(
                    user,
                    onClick = { navigateToDetail(navController, user.login) }
                )
            }

            if (state.hasNextPage) {
                item {
                    LaunchedEffect(Unit) { viewModel.fetchNextPage() }
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

@Composable
fun UserListItem(user: GithubUserProfile, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = user.login, fontWeight = FontWeight.Bold)
            Text(text = user.name)
            Text(text = user.bio, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

fun navigateToDetail(navController: NavHostController, login: String) {
    Log.d("dangdut", "navigate -> (${login})!")
    navController.navigate("detail/$login")
}
