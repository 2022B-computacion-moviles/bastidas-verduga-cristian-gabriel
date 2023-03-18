package com.example.proybim2cgbv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBack =
            findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.register_app_bar)
        btnBack.setOnClickListener {
            finish()
        }
    }
}