package uk.ac.tees.mad.w9617422.presentation.auth

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Login
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.HowToReg
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import uk.ac.tees.mad.w9617422.R
import uk.ac.tees.mad.w9617422.navUtils.Screen
import uk.ac.tees.mad.w9617422.presentation.lottie.LoadingComponent
import uk.ac.tees.mad.w9617422.presentation.profile.ProfileData
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun SignUpScreen(navController: NavController) {


    val context = LocalContext.current

    var userName by remember {
        mutableStateOf("")
    }

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
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

    val firebaseStorage by remember {
        mutableStateOf(FirebaseStorage.getInstance())
    }

    val uri by remember {
        mutableStateOf<Uri?>(null)
    }


    val firebaseAuth by remember {
        mutableStateOf(Firebase.auth)
    }

    val firebaseFireStore by remember {
        mutableStateOf(Firebase.firestore)
    }

    var showLoader by remember {
        mutableStateOf(false)
    }

//    val cameraLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
//            capturedImageUri = uri!!
//        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri1 ->
            capturedImageUri = uri1 ?: Uri.EMPTY
        }

    if (showLoader) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .width(40.dp)
//                    .height(40.dp)
//            )
            LoadingComponent()
        }

    } else {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp)
        ) {

            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Image(
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                        .clip(CircleShape)
                        .clickable {
                            galleryLauncher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                    contentScale = ContentScale.Crop,
                    painter = if (capturedImageUri.path?.isNotEmpty() == true)
                        rememberAsyncImagePainter(capturedImageUri)
                    else
                        painterResource(id = R.drawable.ddf0110aa19f445687b737679eec9cb2),
                    contentDescription = "Profile Image"
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 3.dp),
                value = userName,
                placeholder = {
                    Text(text = "Enter Username")
                },
                singleLine = true,
                label = {
                    Text(text = "Name")
                },
                onValueChange = {
                    userName = it
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Email"
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
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
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
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
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),

                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                onClick = {
                    if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(context, "Enter Valid Email", Toast.LENGTH_SHORT).show()
                    } else if (password.isEmpty() || password.length < 6) {
                        Toast.makeText(context, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show()
                    } else if (userName.isEmpty()) {
                        Toast.makeText(context, "Enter Valid Username", Toast.LENGTH_SHORT).show()
                    } else {
                        showLoader = true
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { loginTask ->
                                if (loginTask.isSuccessful) {
                                    // Skip Firebase Storage and directly save user data
                                    val use = ProfileData(userName, "https://as1.ftcdn.net/v2/jpg/02/43/12/34/1000_F_243123463_zTooub557xEWABDLk0jJklDyLSGl2jrr.jpg")
                                    firebaseFireStore.collection("users")
                                        .document(firebaseAuth.currentUser?.uid ?: "")
                                        .set(use)
                                        .addOnCompleteListener { finalTask ->
                                            showLoader = false
                                            if (finalTask.isSuccessful) {
                                                navController.navigate(Screen.HomeScreen.route) {
                                                    popUpTo(Screen.LoginScreen.route) { inclusive = true }
                                                }
                                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                            } else {
                                                firebaseAuth.signOut()
                                                Toast.makeText(context, "Failed, Try again", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                } else {
                                    showLoader = false
                                    Toast.makeText(context, "Failed, Try again", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }

                //------------ used storage so commented the code below --------------
//                onClick = {
//                    if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                        Toast.makeText(context, "Enter Valid Email", Toast.LENGTH_SHORT).show()
//                    } else if (password.isEmpty() || password.length < 6) {
//                        Toast.makeText(
//                            context,
//                            "Password should of length more than 6",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else if (userName.isEmpty()) {
//                        Toast.makeText(context, "Enter Valid Username", Toast.LENGTH_SHORT)
//                            .show()
//                    } else if (capturedImageUri == Uri.EMPTY) {
//                        Toast.makeText(
//                            context,
//                            "Please Choose Valid profile Photo",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        showLoader = true
//                        firebaseAuth.createUserWithEmailAndPassword(email, password)
//                            .addOnCompleteListener { loginTask ->
//                                if (loginTask.isSuccessful) {
//                                    firebaseStorage.reference
//                                        .child(firebaseAuth.currentUser?.uid ?: "")
//                                        .putFile(capturedImageUri)
//                                        .addOnCompleteListener { imageTask ->
//                                            if (imageTask.isSuccessful) {
//                                                firebaseStorage.reference.child(
//                                                    firebaseAuth.currentUser?.uid ?: ""
//                                                )
//                                                    .downloadUrl.addOnCompleteListener { downTask ->
//                                                        if (downTask.isSuccessful) {
//
//                                                            val use = ProfileData(
//                                                                userName,
//                                                                downTask.result.toString()
//                                                            )
//                                                            firebaseFireStore.collection("users")
//                                                                .document(
//                                                                    firebaseAuth.currentUser?.uid
//                                                                        ?: ""
//                                                                )
//                                                                .set(use)
//                                                                .addOnCompleteListener { finalTask ->
//                                                                    showLoader = false
//                                                                    if (finalTask.isSuccessful) {
//                                                                        navController.navigate(
//                                                                            Screen.HomeScreen.route
//                                                                        ) {
//                                                                            popUpTo(Screen.LoginScreen.route) {
//                                                                                inclusive = true
//                                                                            }
//                                                                        }
//                                                                        Toast.makeText(
//                                                                            context,
//                                                                            "Success",
//                                                                            Toast.LENGTH_SHORT
//                                                                        ).show()
//                                                                    } else {
//                                                                        firebaseAuth.signOut()
//                                                                        Log.d("TAG", "Error: ${finalTask.exception?.message}")
//                                                                        Toast.makeText(
//                                                                            context,
//                                                                            "Failed, Try again  here ${finalTask.exception?.message}",
//                                                                            Toast.LENGTH_SHORT
//                                                                        ).show()
//                                                                    }
//
//                                                                }
//                                                        }
//                                                    }
//                                            } else {
//                                                showLoader = false
//                                                firebaseAuth.signOut()
//                                                Toast.makeText(
//                                                    context,
//                                                    "Failed, Try again",
//                                                    Toast.LENGTH_SHORT
//                                                ).show()
//                                            }
//
//                                        }
//
//                                } else {
//                                    showLoader = false
//                                    Toast.makeText(
//                                        context,
//                                        "Failed, Try again",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//
//                            }
//                    }
//                }
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.HowToReg,
                        contentDescription = "Register",
                        modifier = Modifier.size(30.dp),
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                }
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 30.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account?",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.width(1.dp))

                    Text(
                        modifier = Modifier
                            .clickable {
                                navController.popBackStack()
                            },
                        text = "Login",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}