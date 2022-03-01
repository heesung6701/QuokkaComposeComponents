package com.quokkaman.sample.ratingbar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import com.quokkaman.ratingbar.data.RatingBarState
import com.quokkaman.ratingbar.ui.RatingBar
import com.quokkaman.sample.R
import com.quokkaman.sample.common.*

class RatingbarActivity : AppCompatActivity() {

    // TODO : RatingBarState를 State 서버 클래스로 바꾸기
    private val ratingBarState = RatingBarState(
        maxRating = 5,
        initialRating = 2.0f,
        stepSize = 0.5f,
        isIndicator = true,
        onRatingChange = {
            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
        }
    )

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratingbar)

        findViewById<ComposeView>(R.id.compose_view).setContent {
            RatingBar(ratingBarState = ratingBarState)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            ratingBarState.rating.value = 5.0f
            ratingBarState.isIndicator = false
        }, 5000L)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_parameter, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_set_parameter -> {
                ParameterDialogHelper.openDialog(this, listOf(
                    object: SeekBarParameter(name = "maxRating", initValue = 5f, minValue = 0f, maxValue = 20f, stepSize = 1f) {
                        override fun onValueChange(newValue: Float) {
                        }
                    },
                    object: StringParameter(name = "stepSize", initValue = "1") {
                        override fun onValueChange(newValue: String) {
                            val stepSize = newValue.toFloatOrNull() ?: return
                        }
                    },
                    object: BooleanParameter(name = "isIndicator", initValue = false) {
                        override fun onValueChange(newValue: Boolean) {
                            ratingBarState.isIndicator = newValue
                        }
                    }
                )) { }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}