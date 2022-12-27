package com.example.movcgbv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val boton_ciclo_vida = findViewById<Button>(R.id.boton_ciclo_vida)
            boton_ciclo_vida.setOnClickListener {
            irActividad(basic_activity::class.java)
        }
    }

    fun irActividad(clase : Class<*>){
        val intent = Intent(this, clase::class.java)
        startActivity(intent)
    }
}