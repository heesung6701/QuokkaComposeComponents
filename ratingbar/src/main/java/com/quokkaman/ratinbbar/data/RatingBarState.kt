package com.quokkaman.ratinbbar.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.quokkaman.ratinbbar.error.UnSupportStepSizeException

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