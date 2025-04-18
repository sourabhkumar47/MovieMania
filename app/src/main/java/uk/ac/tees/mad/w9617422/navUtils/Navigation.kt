package uk.ac.tees.mad.w9617422.navUtils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uk.ac.tees.mad.w9617422.presentation.splash.SplashScreen
import uk.ac.tees.mad.w9617422.presentation.auth.LoginScreen
import uk.ac.tees.mad.w9617422.presentation.auth.SignUpScreen
import uk.ac.tees.mad.w9617422.presentation.detail.DetailsScreen
import uk.ac.tees.mad.w9617422.presentation.home.HomeScreen
import uk.ac.tees.mad.w9617422.presentation.profile.ProfileScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(
//            route = Screen.DetailsScreen.route + "{movieId}",
            route = "${Screen.Details.route}/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                },
            )
        ) {
            DetailsScreen()
        }

        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController)
        }
        composable(Screen.RegisterScreen.route) {
            SignUpScreen(navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
    }
}