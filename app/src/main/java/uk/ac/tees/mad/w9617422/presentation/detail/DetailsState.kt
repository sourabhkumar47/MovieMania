package uk.ac.tees.mad.w9617422.presentation.detail

import uk.ac.tees.mad.w9617422.moviesList.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)