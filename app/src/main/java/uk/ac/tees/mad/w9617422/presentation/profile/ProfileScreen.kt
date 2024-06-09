package uk.ac.tees.mad.w9617422.presentation.profile

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Login
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import uk.ac.tees.mad.w9617422.R
import uk.ac.tees.mad.w9617422.location.LocationUtils
import uk.ac.tees.mad.w9617422.navUtils.Screen

@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    var showLoader by remember {
        mutableStateOf(false)
    }

    var userName by remember {
        mutableStateOf("")
    }

    var profileImageChanged by remember {
        mutableStateOf(false)
    }

    var profileImageUri by remember {
        mutableStateOf<String?>(null)
    }

//    var profileImageUri by remember {
//        mutableStateOf<Uri?>(null)
//    }

    val firebaseAuth by remember {
        mutableStateOf(Firebase.auth)
    }

    var user by remember {
        mutableStateOf<ProfileData?>(null)
    }

    val currentUser by remember {
        mutableStateOf(firebaseAuth.currentUser)
    }

    val firebaseFireStore by remember {
        mutableStateOf(Firebase.firestore)
    }

    val firebaseStorage by remember {
        mutableStateOf(FirebaseStorage.getInstance())
    }

    var shouldShowMap by remember {
        mutableStateOf(false)
    }

    var isEditable by remember {
        mutableStateOf(false)
    }

    var location by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = Unit) {
        location = LocationUtils().getCurrentLocation(context as Activity)
    }


//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        profileImageUri = uri
//    }

    LaunchedEffect(Unit) {

        currentUser?.uid?.let {
            firebaseFireStore.collection("users").document(it).get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.exists()) {
                    user = task.result.toObject(ProfileData::class.java)
                }
            }
        }
    }

    LaunchedEffect(user) {
        userName = user?.name ?: ""
        profileImageUri = user?.profileImageUrl
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri1 ->
            profileImageUri = uri1?.toString()
            profileImageChanged = true
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

        if (isEditable) {
//
            if (shouldShowMap) {
                Log.d("ProfileScreen", "Location: $location")

            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 24.dp)
                ) {

                    Icon(
                        Icons.Filled.Close,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 24.dp)
                            .clickable {
                                isEditable = false
                            },
                        contentDescription = "Close"
                    )

                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Image(
                            modifier = Modifier
                                .height(200.dp)
                                .width(200.dp)
                                .clickable {
                                    galleryLauncher.launch(
                                        PickVisualMediaRequest(
                                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }
                                .clip(CircleShape),
                            painter = if (profileImageUri != null)
                                rememberAsyncImagePainter(
                                    profileImageUri,
                                    contentScale = ContentScale.Crop
                                )
                            else
                                painterResource(id = R.drawable.ddf0110aa19f445687b737679eec9cb2),
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop
                        )
                    }


                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        value = userName,
                        placeholder = {
                            Text(text = "Enter Username")
                        },
                        singleLine = true,
                        label = {
                            Text(text = "User Name")
                        },
                        onValueChange = {
                            userName = it
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Person,
                                contentDescription = "User Name"
                            )

                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = MaterialTheme.shapes.medium
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 45.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        onClick = {

                            showLoader = true

                            if (profileImageChanged) {
                                firebaseStorage.reference
                                    .child(firebaseAuth.currentUser?.uid ?: "")
                                    .putFile(Uri.parse(profileImageUri))
                                    .addOnCompleteListener { imageTask ->
                                        if (imageTask.isSuccessful) {
                                            firebaseStorage.reference.child(
                                                firebaseAuth.currentUser?.uid ?: ""
                                            )
                                                .downloadUrl.addOnCompleteListener { downTask ->
                                                    if (downTask.isSuccessful) {

                                                        val use = ProfileData(
                                                            userName,
                                                            downTask.result.toString(),
                                                        )
                                                        firebaseFireStore.collection("users")
                                                            .document(
                                                                firebaseAuth.currentUser?.uid
                                                                    ?: ""
                                                            )
                                                            .set(use)
                                                            .addOnCompleteListener { finalTask ->
                                                                showLoader = false

                                                                if (finalTask.isSuccessful) {
                                                                    navController.navigate(
                                                                        Screen.HomeScreen.route
                                                                    ) {
                                                                        popUpTo(Screen.LoginScreen.route) {
                                                                            inclusive = true
                                                                        }
                                                                    }
                                                                    Toast.makeText(
                                                                        context,
                                                                        "Success",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                } else {

                                                                    firebaseAuth.signOut()
                                                                    Toast.makeText(
                                                                        context,
                                                                        "Failed, Try again  here ${finalTask.exception?.message}",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }

                                                            }
                                                    }
                                                }
                                        } else {
                                            showLoader = false

                                            firebaseAuth.signOut()
                                            Toast.makeText(
                                                context,
                                                "Failed, Try again",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    }

                            } else {
                                val use = ProfileData(
                                    userName,
                                    profileImageUri
                                )
                                firebaseFireStore.collection("users")
                                    .document(firebaseAuth.currentUser?.uid ?: "")
                                    .set(use)
                                    .addOnCompleteListener { finalTask ->
                                        showLoader = false

                                        if (finalTask.isSuccessful) {
                                            navController.navigate(
                                                Screen.HomeScreen.route
                                            ) {
                                                popUpTo(Screen.LoginScreen.route) {
                                                    inclusive = true
                                                }
                                            }
                                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT)
                                                .show()
                                        } else {

                                            firebaseAuth.signOut()
                                            Toast.makeText(
                                                context,
                                                "Failed, Try again  here ${finalTask.exception?.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    }
                            }

                        }) {

                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Save",
                                modifier = Modifier.size(30.dp),
                            )
                            Spacer(modifier = Modifier.width(9.dp))
                        }
                        Text(text = "Save")
                    }

                }
            }

        } else {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 24.dp)
            ) {
                IconButton(
                    onClick = { isEditable = true },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(12.dp)
                        .height(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Image(
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape),
                    painter = if (profileImageUri != null)
                        rememberAsyncImagePainter(
                            profileImageUri,
                            contentScale = ContentScale.Crop,
                        )
                    else
                        painterResource(id = R.drawable.ddf0110aa19f445687b737679eec9cb2),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    Icon(
                        Icons.Filled.Email,
                        contentDescription = "Email"
                    )

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = FirebaseAuth.getInstance().currentUser?.email ?: "",
                        fontSize = 24.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    Icon(
                        Icons.Filled.AccountBox,
                        contentDescription = "Account"
                    )

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = userName,
                        fontSize = 24.sp
                    )
                }

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 45.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    onClick = {
                        firebaseAuth.signOut()
                        navController.navigate(Screen.LoginScreen.route) {
                            popUpTo(Screen.ProfileScreen.route) {
                                inclusive = true
                            }
                        }
                    }) {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.Logout,
                            contentDescription = "Logout",
                            modifier = Modifier.size(30.dp),
                        )
                        Spacer(modifier = Modifier.width(9.dp))
                    }
                    Text(text = "Logout")
                }
            }
        }
    }

}

//------------------------------------------------------------------------------

//    Surface(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState())
//        ) {
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
////                IconButton(onClick = {
////                    ScreenRouter.navigateTo(Screen.HomeScreen)
////                }) {
////                    Icon(
////                        Icons.AutoMirrored.Filled.ArrowBack,
////                        contentDescription = ""
////                    )
////                }
//                Text(
//                    text = "Profile",
//                    style = MaterialTheme.typography.headlineMedium,
//                    fontSize = 30.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//
//                //User can add and change, profile picture of own
////                profileImageUri?.let {
////                    if (Build.VERSION.SDK_INT < 28) {
////                        bitmap.value =
////                            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
////                    } else {
////                        val source = ImageDecoder.createSource(context.contentResolver, it)
////                        bitmap.value = ImageDecoder.decodeBitmap(source)
////                    }
////
////                    bitmap.value?.let { btm ->
////                        Image(
////                            bitmap = btm.asImageBitmap(),
////                            contentDescription = "Image",
////                            modifier = Modifier
////                                .size(150.dp)
////                                .clip(CircleShape)
////                                .clickable {
////                                    launcher.launch("image/*")
////                                },
////                            contentScale = ContentScale.Crop
////                        )
////                    }
////                }
////
////                if (profileImageUri == null) {
////                    Image(
////                        painter = painterResource(id = R.drawable.ddf0110aa19f445687b737679eec9cb2),
////                        contentDescription = "Image",
////                        modifier = Modifier
////                            .size(150.dp)
////                            .clip(CircleShape)
////                            .border(
////                                width = 1.dp,
////                                color = MaterialTheme.colorScheme.onSurface,
////                                shape = CircleShape
////                            )
////                            .clickable {
////                                launcher.launch("image/*")
////                            },
////                        contentScale = ContentScale.Crop
////
////                    )
////                }
//                Spacer(modifier = Modifier.height(5.dp))
//
//                Log.d("ProfileScreen", "Profile Name: ${profileViewModel.profileData.value.name}")
//
//                Text(
//                    text = "Alena",
//                    style = MaterialTheme.typography.headlineMedium,
//                    fontWeight = FontWeight.Bold,
//                )
//                Spacer(modifier = Modifier.height(5.dp))
//                Text(
//                    text = location ?: "Loading...",
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.Normal,
//                )
//            }
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth(),
//            ) {
//                ProfileInfoCard(
//                    icon = Icons.Filled.Person,
//                    text = "Edit Profile"
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//
//                ProfileInfoCard(
//                    icon = Icons.Filled.Payment,
//                    text = "Payment Method",
//                    onClick = {
//                    }
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//                ProfileInfoCard(
//                    icon = Icons.Filled.DarkMode,
//                    text = "Switch Theme"
//                )
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                ProfileInfoCard(
//                    icon = Icons.AutoMirrored.Filled.HelpOutline,
//                    text = "Help Center"
//                )
//
//            }
//
//            Spacer(modifier = Modifier.height(50.dp))
//
//            Button(
//                onClick = {
//                    firebaseAuth.signOut()
//                    navController.navigate(Screen.LoginScreen.route) {
//                        popUpTo(Screen.ProfileScreen.route) {
//                            inclusive = true
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
////                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.colorText))
//            ) {
//                Text(text = "Sign Out")
//            }
//        }
//    }
//    SystemBackButtonHandler {
//        ScreenRouter.navigateTo(Screen.ExploreScreen)
//    }
//}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ProfileScreenPreview() {
//    ProfileScreen(
//        userProfile = ProfileData(
//            name = "Alena",
//            profileImage = painterResource(id =R.drawable.profile_pic_2),
//            gender = "F"
//        )
//    )
//}