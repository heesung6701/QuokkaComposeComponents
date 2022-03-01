package com.quokkaman.ratingbar

import com.quokkaman.ratingbar.RatingBarMathUtils.constrainedMap

fun calculateRating(x: Float, width: Int, maxRating: Int): Float {
    return constrainedMap(0f, maxRating.toFloat(), 0f, width.toFloat(), x)
}
