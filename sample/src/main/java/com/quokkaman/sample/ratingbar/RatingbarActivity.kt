package com.quokkaman.sample.ratingbar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import com.quokkaman.ratingbar.data.RatingBarState
import com.quokkaman.ratingbar.ui.RatingBar
import com.quokkaman.sample.R

class RatingbarActivity : AppCompatActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratingbar)

        val ratingBarState = RatingBarState(
            maxRating = 5,
            initialRating = 2.0f,
            stepSize = 0.5f,
            isIndicator = true,
            onRatingChange = {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            }
        )
        findViewById<ComposeView>(R.id.compose_view).setContent {
            RatingBar(ratingBarState = ratingBarState)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            ratingBarState.rating.value = 5.0f
            ratingBarState.isIndicator = false
        }, 5000L)
    }
}