package com.example.proybim2cgbv

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class PlayActivity : AppCompatActivity() {
    private var cards: ArrayList<Card> = ArrayList()
    private var currentIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val title = findViewById<TextView>(R.id.play_title)
        val content = findViewById<TextView>(R.id.play_content)

        var bundle = intent.extras
        var titles = bundle?.getString("titles")
        var contents = bundle?.getString("contents")

        if (titles != null && contents != null) {
            var titlesArray = titles.split("<|>")
            var contentsArray = contents.split("<|>")
            for (i in 0 until titlesArray.size - 1) {
                cards.add(Card("", titlesArray[i], contentsArray[i]))
            }
            cards.shuffle()
            currentIndex = 0
            nextCard(title, content)
        }

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

        val btnNext = findViewById<FloatingActionButton>(R.id.play_fab_next)
        btnNext.setOnClickListener {
            nextCard(title, content)
        }

        val btnPrev = findViewById<FloatingActionButton>(R.id.play_fab_back)
        btnPrev.setOnClickListener {
            prevCard(title, content)
        }
    }

    private fun nextCard(title: TextView, content: TextView) {
        currentIndex++
        if (currentIndex >= cards.size) {
            currentIndex = 0
        }
        title.text = cards[currentIndex].title
        content.text = cards[currentIndex].content
        content.visibility = TextView.INVISIBLE
    }

    private fun prevCard(title: TextView, content: TextView) {
        currentIndex--
        if (currentIndex < 0) {
            currentIndex = cards.size - 1
        }
        title.text = cards[currentIndex].title
        content.text = cards[currentIndex].content
        content.visibility = TextView.INVISIBLE
    }
}