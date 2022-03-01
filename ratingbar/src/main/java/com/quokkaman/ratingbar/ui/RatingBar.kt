package com.quokkaman.ratingbar.ui

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quokkaman.ratingbar.calculateRating
import com.quokkaman.ratingbar.data.RatingBarState
import kotlin.math.floor

const val LOG_TAG = "RatingBar"

@ExperimentalComposeUiApi
@Composable
fun RatingBar(ratingBarState: RatingBarState) {
    var width by remember { mutableStateOf(0) }
    val rating by ratingBarState.rating

    Row(modifier = Modifier
        .onSizeChanged {
            width = it.width
        }
        .pointerInput(
            Unit
        ) {
            detectHorizontalDragGestures(
                onDragEnd = {
                    if (ratingBarState.isIndicator) {
                        return@detectHorizontalDragGestures
                    }
                    ratingBarState.onRatingChange(ratingBarState.rating.value)
                },
                onDragCancel = {
                    if (ratingBarState.isIndicator) {
                        return@detectHorizontalDragGestures
                    }
                },
                onDragStart = {
                    if (ratingBarState.isIndicator) {
                        return@detectHorizontalDragGestures
                    }
                },
                onHorizontalDrag = { change, _ ->
                    if (ratingBarState.isIndicator) {
                        return@detectHorizontalDragGestures
                    }
                    change.consumeAllChanges()
                    val newRating = calculateRating(change.position.x, width, ratingBarState.maxRating)
                    ratingBarState.updateRating(newRating)
                }
            )
        }
        .pointerInteropFilter {
            if (ratingBarState.isIndicator) {
                return@pointerInteropFilter true
            }
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    val newRating = calculateRating(it.x, width, ratingBarState.maxRating)
                    ratingBarState.updateRating(newRating)
                }
            }
            true
        }
    ) {
        RatingStars(rating, ratingBarState.maxRating)
    }
}

@Composable
fun RatingStars(rating: Float, maxRating: Int) {
    Row {
        for (i in 1..maxRating) {
            val floatingPoint = rating - (i - 1)
            val ratingEach = when {
                i <= floor(rating) -> {
                    1.0f
                }
                0 < floatingPoint && floatingPoint < 1 -> {
                    floatingPoint
                }
                else -> {
                    0.0f
                }
            }
            Box(
                modifier = Modifier
                    .size(24.dp)
            ) {
                RatingStar(
                    rating = ratingEach
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun RatingBarPreview() {
    MaterialTheme {
        Box {
            Surface(color = Color.LightGray) {
            }
            Column {
                Box(modifier = Modifier.height(24.dp)) {
                    RatingBar(ratingBarState = RatingBarState(
                        maxRating = 5,
                        stepSize = 1.0f,
                        initialRating = 3.0f,
                        onRatingChange = {
                            Log.d(LOG_TAG, "First RatingBar ratio :$it")
                        }
                    ))
                }
                Box(modifier = Modifier.height(24.dp)) {
                    RatingBar(ratingBarState = RatingBarState(
                        maxRating = 3,
                        stepSize = 0.25f,
                        initialRating = 2.25f,
                        onRatingChange = {
                            Log.d(LOG_TAG, "Second RatingBar ratio :$it")
                        }
                    ))
                }
                Box(modifier = Modifier.height(24.dp)) {
                    RatingBar(ratingBarState = RatingBarState(
                        maxRating = 10,
                        stepSize = 0.5f,
                        initialRating = 2.5f,
                        isIndicator = true,
                        onRatingChange = {
                            Log.d(LOG_TAG, "Third RatingBar ratio :$it")
                        }
                    ))
                }
            }
        }
    }
}