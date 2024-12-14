package com.lotusinc.transportmanagmentsystem

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.lotusinc.transportmanagmentsystem.login.LoginPage
import com.lotusinc.transportmanagmentsystem.login.LoginViewModel
import com.lotusinc.transportmanagmentsystem.ui.theme.TransportManagmentSystemTheme

class MainActivity : ComponentActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        enableEdgeToEdge()
        setContent {
            TransportManagmentSystemTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(
                        modifier = Modifier.padding(innerPadding),
                        firebaseAuth = firebaseAuth,
                        localContext = this
                    )
                }
            }
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    firebaseAuth: FirebaseAuth,
    localContext: Context
) {
    var isUserLoggedIn by remember { mutableStateOf(firebaseAuth.currentUser != null) }
    var userType by remember { mutableStateOf("")}
    val loginViewModel: LoginViewModel = viewModel()
    if (isUserLoggedIn) {
        loginViewModel.fetchUserType(firebaseAuth.currentUser?.uid.orEmpty()) {
            userType = it.orEmpty()
            isUserLoggedIn = true
        }
        LandingPage(userType)

    } else {
        LoginPage(localContext) { email, message ->
            isUserLoggedIn = true
            userType = message
        }
    }
}

@Composable
fun LandingPage(name: String, modifier: Modifier = Modifier) {
    when(name) {
        "COORDINATOR" -> {
            Column {
                Text(
                    text = "Hello there! Welcome to the Coordinator Portal",
                    modifier = modifier.padding(24.dp)
                )
                Text(
                    text = "This is the Coordinator Portal",
                    modifier = modifier.padding(24.dp)
                )
            }
        }

        "STUDENT" -> {
            Column {
                Text(
                    text = "Hello there! Welcome to the Student Portal",
                    modifier = modifier.padding(24.dp)
                )
                Text(
                    text = "This is the Student Portal",
                    modifier = modifier.padding(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TransportManagmentSystemTheme {
        LandingPage("Android")
    }
}