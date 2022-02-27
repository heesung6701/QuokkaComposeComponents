package com.quokkaman.ratingbar.ui

import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quokkaman.ratingbar.FractionalRectangleShape

@Composable
fun RatingStar(
    @FloatRange(from = 0.0, to = 1.0) rating: Float = 1.0f,
    @DrawableRes activateDrawableRes: Int = R.drawable.ic_star_active,
    @DrawableRes inActivateDrawableRes: Int = R.drawable.ic_star_inactive
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (rating == 1.0f) {
            ActivateStarImage(
                drawableRes = activateDrawableRes,
                fraction = 1.0f
            )
        } else if (rating > 0.0f) {
            InActivateStarImage(
                drawableRes = inActivateDrawableRes,
                fraction = 1.0f
            )
            ActivateStarImage(
                drawableRes = activateDrawableRes,
                fraction = rating
            )
        }
        else {
            InActivateStarImage(
                drawableRes = inActivateDrawableRes,
                fraction = 1.0f
            )
        }
    }
}

@Composable
fun ActivateStarImage(
    @DrawableRes drawableRes: Int = R.drawable.ic_star_active,
    fraction: Float = 1.0f
) {
    RatingStarImage(
        painter = painterResource(id = drawableRes),
        fraction
    )
}

@Composable
fun InActivateStarImage(
    @DrawableRes drawableRes: Int = R.drawable.ic_star_inactive,
    fraction: Float = 1.0f
) {
    RatingStarImage(
        painter = painterResource(id = drawableRes),
        fraction
    )
}

@Composable
fun RatingStarImage(
    painter: Painter = painterResource(id = R.drawable.ic_star_active),
    fraction: Float = 1.0f
) {
    Image(
        painter = painter,
        modifier = Modifier
            .fillMaxSize()
            .clip(FractionalRectangleShape(0f, fraction)),
        contentDescription = null
    )
}

@Preview
@Composable
fun RatingStarPreview() {
    MaterialTheme {
        Column {
            listOf(0.3f, 0.5f, 0.8f, 1.0f).forEach { fraction ->
                Box(modifier = Modifier.size(24.dp)) {
                    RatingStar(
                        rating = fraction
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RatingStarImagePreview() {
    MaterialTheme {
        Column {
            listOf(0.3f, 0.5f, 0.8f, 1.0f).forEach { fraction ->
                Box(modifier = Modifier.size(24.dp)) {
                    RatingStarImage(
                        fraction = fraction
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ActivateRatingStarPreview() {
    MaterialTheme {
        Column {
            for (fraction in (0..10 step 2).map { it.toFloat() / 10 }) {
                Box(modifier = Modifier.size(24.dp)) {
                    ActivateStarImage(fraction = fraction)
                }
            }
        }
    }
}

@Preview
@Composable
fun InActiveStarImagePreview() {
    MaterialTheme {
        Column {
            for (fraction in (0..10 step 2).map { it.toFloat() / 10 }) {
                Box(modifier = Modifier.size(24.dp)) {
                    InActivateStarImage(fraction = fraction)
                }
            }
        }
    }
}