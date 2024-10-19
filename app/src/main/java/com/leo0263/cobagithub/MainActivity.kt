package com.leo0263.cobagithub

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.leo0263.cobagithub.network.ApolloClientInstance
import com.leo0263.cobagithub.network.GitHubServiceImpl
import com.leo0263.cobagithub.ui.theme.CobaGithubTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
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
            CobaGithubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CobaGithubTheme {
        Greeting("Android")
    }
}
