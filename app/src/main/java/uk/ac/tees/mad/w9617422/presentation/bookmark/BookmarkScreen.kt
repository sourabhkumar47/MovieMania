package uk.ac.tees.mad.w9617422.presentation.bookmark

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.w9617422.moviesList.domain.model.Movie
import uk.ac.tees.mad.w9617422.moviesList.domain.model.MovieObject
import uk.ac.tees.mad.w9617422.moviesList.presentation.MovieListState
import uk.ac.tees.mad.w9617422.presentation.compo.MovieItem

@Composable
fun BookmarkScreen(navController: NavHostController, movieListState: MovieListState) {
    Text(text = "Bookmark Screen", modifier = Modifier.padding(16.dp))

    val context = LocalContext.current

    var movies by remember {
        mutableStateOf<List<Movie>?>(emptyList())
    }

    LaunchedEffect(Unit) {
        Log.d(
            "movieListState", movieListState.upcomingMovieList.toString()
        )

        Log.d("BookmarkScreen", "LaunchedEffect")

        val allMovies = (movieListState.upcomingMovieList + movieListState.popularMovieList)

        val prefSet = Preferences.getPref(context)?.getStringSet("bookmarks", emptySet())

        Log.d("BookmarkScreen", "All Movies: ${allMovies}")
        Log.d("BookmarkScreen", "Bookmark IDs: $prefSet")

        movies = allMovies?.filter {
            prefSet?.contains(it.id.toString()) == true
        }
        Log.d("BookmarkScreen", "Movies: $movies")
    }

    if (movies.isNullOrEmpty()) {

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "No Bookmarks",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }


        return
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 2.dp)
        ) {
            items(movies?.size ?: 0) { index ->
                movies?.get(index)?.let { movie ->
                    Log.d("BookmarkScreen", "Movie: $movie")
                    MovieItem(movie = movie, navHostController = navController)
                }
            }
        }
    }

}