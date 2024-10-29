package com.leo0263.cobagithub.ui.detail

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage

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
                    .padding(16.dp)
                    .fillMaxSize(),
            ) {
                state.let { state ->
                    UserDetail(state)
                    // UserRepositories(state)
                }
            }

        }
    }
}

@Composable
fun UserDetail(state: DetailUiState) {
    Column {
        Row {
            AsyncImage(
                model = state.userDetail?.avatarUrl,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(state.userDetail?.login ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(state.userDetail?.name ?: "", fontSize = 16.sp)
                Text(state.userDetail?.followers.toString(), fontSize = 14.sp)
                Text(state.userDetail?.following.toString(), fontSize = 14.sp)
                Text(state.userDetail?.company ?: "", fontSize = 14.sp)
                Text(state.userDetail?.location ?: "", fontSize = 14.sp)
            }
        }
        if (state.userDetail?.bio != null && state.userDetail.bio != "") {
            Spacer(modifier = Modifier.height(8.dp))
            Text(state.userDetail.bio, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(color = Color.Gray, thickness = 2.dp)
    }
}
