package com.quokkaman.ratinbbar.data

import com.quokkaman.ratinbbar.error.UnSupportStepSizeException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class RatingBarStateTest {

    @Test
    fun calculateDiscreteValueTest() {
        val ratingBarState = RatingBarState(
            maxRating = 3,
            stepSize = 0.5f,
            initialRating = 1.5f
        )
        val inputList = listOf(1.8f, 2.0f, 2.009f, 2.10000001f)
        val expectedList = listOf(1.5f, 2.0f, 2.0f, 2.0f)

        (inputList zip expectedList).forEach { pair ->
            println("input: ${pair.first}")
            ratingBarState.updateRating(pair.first)
            assertEquals(pair.second, ratingBarState.rating.value, 0.0001f)
        }
    }

    @Test
    fun unSupportStepSizeTest() {
        assertThrows(UnSupportStepSizeException::class.java) {
            RatingBarState(
                maxRating = 3,
                stepSize = 0.3f,
                initialRating = 1.5f
            )
        }
    }
}