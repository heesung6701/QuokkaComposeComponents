package com.quokkaman.ratinbbar.ui

import android.util.Log
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quokkaman.ratinbbar.data.RatingBarState
import kotlin.math.floor

const val LOG_TAG = "RatingBar"

@Composable
fun RatingBar(ratingBarState: RatingBarState) {
    var width by remember { mutableStateOf(0) }

    Row(modifier = Modifier
        .onSizeChanged {
            width = it.width
        }
        .pointerInput(
            Unit
        ) {
            detectHorizontalDragGestures(
                onDragEnd = {
                    ratingBarState.onRatingChange(ratingBarState.rating.value)
                },
                onDragCancel = {
                },
                onDragStart = {
                },
                onHorizontalDrag = { change, _ ->
                    change.consumeAllChanges()
                    val ratio = (change.position.x / width).coerceIn(0f, 1.0f)
                    val progress = ratio * ratingBarState.maxRating
                    ratingBarState.updateRating(progress)
                }
            )
        }) {
        RatingStars(ratingBarState)
    }
}

@Composable
fun RatingStars(ratingBarState: RatingBarState) {
    val progress by ratingBarState.rating
    Row {
        for (i in 1..ratingBarState.maxRating) {
            val floatingPoint = progress - (i - 1)
            val rating = when {
                i <= floor(progress) -> {
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
                    rating = rating
                )
            }
        }
    }
}

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
                        stepSize = 0.3f,
                        initialRating = 1.8f,
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
                        onRatingChange = {
                            Log.d(LOG_TAG, "Third RatingBar ratio :$it")
                        }
                    ))
                }
            }
        }
    }
}