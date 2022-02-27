package com.quokkaman.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quokkaman.sample.ratingbar.RatingbarActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Intent(applicationContext, RatingbarActivity::class.java).apply {
            startActivity(this)
        }
    }
}