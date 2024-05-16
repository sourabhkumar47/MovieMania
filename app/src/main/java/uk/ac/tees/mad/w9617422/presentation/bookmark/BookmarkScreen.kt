package uk.ac.tees.mad.w9617422.presentation.bookmark

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BookmarkScreen(navController: NavHostController) {
    Text(text = "Bookmark Screen", modifier = Modifier.padding(16.dp))

}