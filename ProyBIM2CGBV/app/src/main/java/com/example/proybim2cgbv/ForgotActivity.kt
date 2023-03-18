package com.example.proybim2cgbv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        val btnBack =
            findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.forgot_app_bar)
        btnBack.setOnClickListener {
            finish()
        }
    }
}