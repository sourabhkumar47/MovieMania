package uk.ac.tees.mad.w9617422.navUtils

sealed class Screen(val route:String) {
    data object HomeScreen : Screen("home_screen")
    data object ProfileScreen : Screen("profile_screen")
//    data object DetailsScreen : Screen("details_screen")
    data object LoginScreen : Screen("login_screen")
    data object RegisterScreen : Screen("register_screen")
    data object SplashScreen : Screen("splash_screen")

}