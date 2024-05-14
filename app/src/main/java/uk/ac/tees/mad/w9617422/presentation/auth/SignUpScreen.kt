package uk.ac.tees.mad.w9617422.presentation.auth

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.w9617422.R
import uk.ac.tees.mad.w9617422.navUtils.Screen
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun SignUpScreen(navController: NavController) {


    val context = LocalContext.current

    var userName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val firebaseAuth by remember {
        mutableStateOf(Firebase.auth)
    }

    var showLoader by remember {
        mutableStateOf(false)
    }

    if (showLoader) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
            )
        }

    } else {

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp)
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = userName,
                placeholder = {
                    Text(text = "Enter Username")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person"
                    )
                },
                singleLine = true,
                label = {
                    Text(text = "Name")
                },
                onValueChange = {
                    userName = it
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = email,
                placeholder = {
                    Text(text = "Enter Email")
                },
                singleLine = true,

                label = {
                    Text(text = "Email")
                },
                onValueChange = {
                    email = it
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email"
                    )
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = password,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = "Password"
                    )
                },
                trailingIcon = {

                    val iconImage = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    val description = if (passwordVisible) {
                        stringResource(id = R.string.hide_password)
                    } else {
                        stringResource(id = R.string.show_password)
                    }

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = iconImage, contentDescription = description)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                placeholder = {
                    Text(text = "Enter Password")
                },
                label = {
                    Text(text = "Password")
                },
                onValueChange = {
                    password = it
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {

                    if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(context, "Enter Valid Email", Toast.LENGTH_SHORT).show()
                    } else if (password.isEmpty() || password.length < 6) {
                        Toast.makeText(
                            context,
                            "Password should of length more than 6",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (userName.isEmpty()) {
                        Toast.makeText(context, "Enter Valid Username", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        showLoader = true
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "User Created", Toast.LENGTH_SHORT)
                                        .show()
                                    navController.navigate(Screen.HomeScreen.route)
                                } else {
                                    Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                showLoader = false
                            }
                    }
                }) {
                Text(text = "Register")
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                text = "Login",
                textAlign = TextAlign.Center
            )

        }
    }
}