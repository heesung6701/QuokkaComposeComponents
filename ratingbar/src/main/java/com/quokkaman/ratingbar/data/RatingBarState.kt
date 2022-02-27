package com.quokkaman.ratinbbar.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.quokkaman.ratingbar.UnSupportInitialRatingException
import com.quokkaman.ratingbar.UnSupportStepSizeException

data class RatingBarState(
    val initialRating: Float = 0f,
    val maxRating: Int = 5,
    val stepSize: Float = 1f,
    val onRatingChange: (Float) -> Unit = {}
) {
    val rating: MutableState<Float> = mutableStateOf(initialRating)
    private val maxProgress: Int = (maxRating / stepSize).toInt()
    private val invertStepSize: Int = (1f / stepSize).toInt()

    init {
        if (invertStepSize * stepSize != 1.0f) {
            throw UnSupportStepSizeException(stepSize)
        }

        if ((initialRating / stepSize).toInt().toFloat() != initialRating / stepSize) {
            throw UnSupportInitialRatingException(initialRating, stepSize)
        }
    }

    fun updateRating(x: Float) {
        val progress = (x * invertStepSize).toInt().coerceIn(0, maxProgress)
        val newRating = progress * stepSize
        if (rating.value == newRating) {
            return
        }
        rating.value = newRating
    }
}