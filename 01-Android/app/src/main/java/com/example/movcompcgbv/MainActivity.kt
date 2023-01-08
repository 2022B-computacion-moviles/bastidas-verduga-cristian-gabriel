package com.example.movcompcgbv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonACicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonACicloVida.setOnClickListener {
            iraActividad(ACicloVida::class.java)
        }

        val botonListView = findViewById<Button>(R.id.btn_ir_ciclo_vida)
        botonListView.setOnClickListener {
            iraActividad(BListView::class.java)
        }
    }

    fun iraActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}