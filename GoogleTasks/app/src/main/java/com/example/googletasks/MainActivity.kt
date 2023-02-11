package com.example.googletasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    //val bottomAppBar: BottomAppBar = findViewById(R.id.bottom_app_bar)
    //val fabBottomBar: FloatingActionButton = findViewById(R.id.fab_bottom_bar)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}