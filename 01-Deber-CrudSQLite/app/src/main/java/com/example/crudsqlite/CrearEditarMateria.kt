package com.example.crudsqlite

import Materia
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CrearEditarMateria : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_materia)

        val modo = intent.getStringExtra("modo")
        val id = intent.getIntExtra("id", -1)

        val btnGuardar = findViewById<Button>(R.id.btn_materia_guardar)

        val etMateriaId = findViewById<EditText>(R.id.et_materia_id)
        val etMateriaNombre = findViewById<EditText>(R.id.et_materia_nombre)
        val etMateriaCodigo = findViewById<EditText>(R.id.et_materia_codigo)
        val etMateriaAula = findViewById<EditText>(R.id.et_materia_aula)
        val etMateriaCreditos = findViewById<EditText>(R.id.et_materia_credito)
        val etMateriaCosto = findViewById<EditText>(R.id.et_materia_costo)

        val components = listOf(
            etMateriaNombre,
            etMateriaCodigo,
            etMateriaAula,
            etMateriaCreditos,
            etMateriaCosto
        )

        val intentDevolverParametros = Intent()
        when (modo) {
            "crear" -> {
                title = "Crear materia"
                for (component in components) {
                    component.isEnabled = true
                }
                btnGuardar.visibility = Button.VISIBLE
                btnGuardar.setOnClickListener {
                    DatabaseHelper(this).crearMateria(
                        Materia(
                            0,
                            etMateriaNombre.text.toString(),
                            etMateriaCodigo.text.toString(),
                            etMateriaAula.text.toString(),
                            etMateriaCreditos.text.toString().toInt(),
                            etMateriaCosto.text.toString().toDouble()
                        )
                    )
                    intentDevolverParametros.putExtra("resultado", "OK")
                    setResult(RESULT_OK, intentDevolverParametros)
                    finish()
                }
            }
            "editar" -> {
                title = "Editar materia"
                for (component in components) {
                    component.isEnabled = true
                }
                btnGuardar.visibility = Button.VISIBLE
                btnGuardar.setOnClickListener {
                    DatabaseHelper(this).updateMateria(
                        Materia(
                            id,
                            etMateriaNombre.text.toString(),
                            etMateriaCodigo.text.toString(),
                            etMateriaAula.text.toString(),
                            etMateriaCreditos.text.toString().toInt(),
                            etMateriaCosto.text.toString().toDouble()
                        )
                    )
                    intentDevolverParametros.putExtra("resultado", "OK")
                    setResult(RESULT_OK, intentDevolverParametros)
                    finish()
                }
                val materia = DatabaseHelper(this).readMateria(id)
                etMateriaId.setText(materia.id.toString())
                etMateriaNombre.setText(materia.nombre)
                etMateriaCodigo.setText(materia.codigo)
                etMateriaAula.setText(materia.aula)
                etMateriaCreditos.setText(materia.creditos.toString())
                etMateriaCosto.setText(materia.costoCredito.toString())
            }
            "ver" -> {
                title = "Ver materia"
                for (component in components) {
                    component.isEnabled = false
                }
                btnGuardar.visibility = Button.GONE
                val materia = DatabaseHelper(this).readMateria(id)
                etMateriaId.setText(materia.id.toString())
                etMateriaNombre.setText(materia.nombre)
                etMateriaCodigo.setText(materia.codigo)
                etMateriaAula.setText(materia.aula)
                etMateriaCreditos.setText(materia.creditos.toString())
                etMateriaCosto.setText(materia.costoCredito.toString())
            }
        }
    }
}