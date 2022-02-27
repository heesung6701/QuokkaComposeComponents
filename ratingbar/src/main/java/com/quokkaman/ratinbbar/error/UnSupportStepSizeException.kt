package com.quokkaman.ratinbbar.error

class UnSupportStepSizeException(stepSize: Float): IllegalArgumentException("The reciprocal of stepSize($stepSize) must be an integer.")