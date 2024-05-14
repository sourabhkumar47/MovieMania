package uk.ac.tees.mad.w9617422.navUtils

sealed class Screen(val route:String) {
    data object HomeScreen : Screen("home_screen")
    data object ProfileScreen : Screen("profile_screen")
    data object HomeDetailsScreen : Screen("home_details_screen")
    data object LoginScreen : Screen("login_screen")
    data object RegisterScreen : Screen("register_screen")
    data object SplashScreen : Screen("splash_screen")


    fun withArgs(vararg args:String) :String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}