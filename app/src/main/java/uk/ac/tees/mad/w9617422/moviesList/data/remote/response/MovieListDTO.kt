package uk.ac.tees.mad.w9617422.moviesList.data.remote.response

data class MovieListDTO(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)