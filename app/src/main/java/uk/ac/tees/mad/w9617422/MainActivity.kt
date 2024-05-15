 package uk.ac.tees.mad.w9617422

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.w9617422.moviesList.presentation.MovieListViewModel
import uk.ac.tees.mad.w9617422.navUtils.Navigation
import uk.ac.tees.mad.w9617422.ui.theme.WatchwaveTheme

 @AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            WatchwaveTheme(darkTheme = false) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier =Modifier.padding(innerPadding)) {
                        Navigation(navController)
//                        val movieListViewModel = hiltViewModel<MovieListViewModel>()
                    }
                }
            }
        }
    }
}