package com.leo0263.cobagithub.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.leo0263.cobagithub.UserDetailQuery

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
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                state.let { state ->
                    UserDetail(state)
                    UserRepositoriesList(state)
                }
            }
        }
    }
}

@Composable
fun UserDetail(state: DetailUiState) {
    val followers: String = state.userDetail.followers.toString()
    val following: String = state.userDetail.following.toString()

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        Column (
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Row {
                AsyncImage(
                    model = state.userDetail.avatarUrl,
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        state.userDetail.login,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(state.userDetail.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Gray
                    )
                    Text(
                        "$followers followers | $following following",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "${state.userDetail.starredRepositories} total stars",
                        style = MaterialTheme.typography.bodySmall
                    )
                    if (state.userDetail.company != "") {
                        Row {
                            Icon(Icons.Default.Home, "company", Modifier.size(16.dp))
                            Text(
                                state.userDetail.company,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    if (state.userDetail.location != "") {
                        Row {
                            Icon(Icons.Default.LocationOn, "location", Modifier.size(16.dp))
                            Text(
                                state.userDetail.location,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            if (state.userDetail.bio != "") {
                Spacer(modifier = Modifier.height(4.dp))
                Text(state.userDetail.bio, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun UserRepositoriesList(state: DetailUiState) {
    val nodes = state.userDetail.repositories?.nodes ?: emptyList()

    if (nodes.isNotEmpty()) {
        Box {
            LazyColumn (
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxSize()
            ) {
                items(nodes.size) { position ->
                    nodes[position]?.let { RepositoryItem(it) }
                }
            }
        }
    }
}

@Composable
fun RepositoryItem(node: UserDetailQuery.Node) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                node.url.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
                    context.startActivity(intent)
                }
            }
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = node.name,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.Star, "stars", Modifier.size(16.dp))
            Text(
                text = "${node.stargazers.totalCount}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        node.primaryLanguage?.let { language ->
            Text(
                text = "Language: ${language.name}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        node.description?.let { description ->
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
