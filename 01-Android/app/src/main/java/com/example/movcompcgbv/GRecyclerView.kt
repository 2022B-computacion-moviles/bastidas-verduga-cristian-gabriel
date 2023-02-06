package com.example.movcompcgbv

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class GRecyclerView : AppCompatActivity(){
    var totalLikes = 0
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

    }

    fun inicializarRecyclerView(
        lista: ArrayList<BEntrenador>,
        recyclerView: RecyclerView
    ){

    }

    fun aumentarTotalLikes(){
        totalLikes = totalLikes +1
        val totalLikesTextView = findViewById<TextView>(R.id.tv_total_likes)
        totalLikesTextView.text = totalLikes.toString()
    }
}