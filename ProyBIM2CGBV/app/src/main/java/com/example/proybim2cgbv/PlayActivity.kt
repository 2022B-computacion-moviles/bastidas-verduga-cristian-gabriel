package com.example.proybim2cgbv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val title = findViewById<TextView>(R.id.play_title)
        val content = findViewById<TextView>(R.id.play_content)

        val llPlay = findViewById<LinearLayout>(R.id.ll_play)
        llPlay.setOnClickListener {
            if (content.visibility == TextView.VISIBLE) {
                content.visibility = TextView.INVISIBLE
            } else {
                content.visibility = TextView.VISIBLE
            }
        }

        val btnStop = findViewById<FloatingActionButton>(R.id.play_fab_stop)
        btnStop.setOnClickListener {
            finish()
        }
    }
}