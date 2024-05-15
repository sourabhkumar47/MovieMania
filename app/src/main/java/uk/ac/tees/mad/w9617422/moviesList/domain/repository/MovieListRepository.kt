package uk.ac.tees.mad.w9617422.moviesList.domain.repository


import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.w9617422.moviesList.domain.model.Movie
import uk.ac.tees.mad.w9617422.moviesList.utils.Resource

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}