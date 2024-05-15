package uk.ac.tees.mad.w9617422.presentation.profile

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.tees.mad.w9617422.R
import uk.ac.tees.mad.w9617422.location.LocationUtils

@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    var profileImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var location by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = Unit) {
        location = LocationUtils().getCurrentLocation(context as Activity)
    }

    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
//                    ScreenRouter.navigateTo(Screen.HomeScreen)
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //User can add and change, profile picture of own
                profileImageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value =
                            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }

                    bitmap.value?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .clickable {
                                    launcher.launch("image/*")
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                if (profileImageUri == null) {
                    Image(
                        painter = painterResource(id = R.drawable.ddf0110aa19f445687b737679eec9cb2),
                        contentDescription = "Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = CircleShape
                            )
                            .clickable {
                                launcher.launch("image/*")
                            },
                        contentScale = ContentScale.Crop

                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
//                Text(
//                    text = userProfile.name,
//                    style = MaterialTheme.typography.headlineMedium,
//                    fontWeight = FontWeight.Bold,
//                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                ProfileInfoCard(
                    icon = Icons.Filled.Person,
                    text = "Edit Profile"
                )
                Spacer(modifier = Modifier.height(10.dp))

                ProfileInfoCard(
                    icon = Icons.Filled.Payment,
                    text = "Payment Method",
                    onClick = {
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                ProfileInfoCard(
                    icon = Icons.Filled.DarkMode,
                    text = "Switch Theme"
                )

                Spacer(modifier = Modifier.height(10.dp))

                ProfileInfoCard(
                    icon = Icons.AutoMirrored.Filled.HelpOutline,
                    text = "Help Center"
                )

            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    //TODO: LOGOUT
                    navController.navigate("login")
                },
                modifier = Modifier.fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.colorText))
            ) {
                Text(text = "Sign Out")
            }
        }
    }
//    SystemBackButtonHandler {
//        ScreenRouter.navigateTo(Screen.ExploreScreen)
//    }
}

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