package com.quokkaman.ratingbar

class UnSupportStepSizeException(stepSize: Float): IllegalArgumentException("The reciprocal of stepSize($stepSize) must be an integer.")
class UnSupportInitialRatingException(initialRating: Float, stepSize: Float): IllegalArgumentException("initialRating($initialRating) must be divided by stepSize($stepSize")