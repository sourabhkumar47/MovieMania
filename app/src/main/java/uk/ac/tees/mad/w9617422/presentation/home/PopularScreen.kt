package uk.ac.tees.mad.w9617422.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.tees.mad.w9617422.moviesList.presentation.MovieListState
import uk.ac.tees.mad.w9617422.moviesList.presentation.MovieListUiEvent
import uk.ac.tees.mad.w9617422.moviesList.utils.Category
import uk.ac.tees.mad.w9617422.presentation.compo.MovieItem

@Composable
fun PopularMoviesScreen(
    movieListState: MovieListState,
    navController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit
) {

    if (movieListState.popularMovieList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column {
            Text(
                text = "Popular Movies",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 2.dp)
            ) {
                items(movieListState.popularMovieList.size) { index ->
                    MovieItem(
                        movie = movieListState.popularMovieList[index],
                        navHostController = navController
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (index >= movieListState.popularMovieList.size - 1 && !movieListState.isLoading) {
                        onEvent(MovieListUiEvent.Paginate(Category.POPULAR))
                    }

                }
            }
        }
    }

}