package com.example.crudfirebase

import Estudiante
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

public class CrearEditarEstudiante : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_estudiante)

        val modo = intent.getStringExtra("modo")
        val id = intent.getStringExtra("id").toString()

        val btnGuardar = findViewById<Button>(R.id.btn_estudiante_guardar)
        val etEstudianteId = findViewById<EditText>(R.id.et_estudiante_id)
        val etEstudianteNombre = findViewById<EditText>(R.id.et_estudiante_nombre)
        val etEstudianteApellido = findViewById<EditText>(R.id.et_estudiante_apellido)
        val etEstudianteFechaNacimiento = findViewById<EditText>(R.id.et_estudiante_fecha)
        val etEstudianteDireccion = findViewById<EditText>(R.id.et_estudiante_direccion)
        val etEstudianteCostoCredito = findViewById<EditText>(R.id.et_estudiante_costo)
        val cbEstudianteBeca = findViewById<CheckBox>(R.id.cb_estudiante_beca)

        val components = listOf(
            etEstudianteNombre,
            etEstudianteApellido,
            etEstudianteFechaNacimiento,
            etEstudianteDireccion,
            etEstudianteCostoCredito,
            cbEstudianteBeca
        )

        val intentDevolverParametros = Intent()
        when (modo) {
            "crear" -> {
                title = "Crear estudiante"
                for (component in components) {
                    component.isEnabled = true
                }
                btnGuardar.visibility = Button.VISIBLE
                btnGuardar.setOnClickListener {
                    FirebaseHelper().crearEstudiante(
                        Estudiante(
                            "",
                            etEstudianteNombre.text.toString(),
                            etEstudianteApellido.text.toString(),
                            SimpleDateFormat("dd/MM/yyyy").parse(etEstudianteFechaNacimiento.text.toString()),
                            etEstudianteDireccion.text.toString(),
                            etEstudianteCostoCredito.text.toString().toDouble(),
                            ArrayList(),
                            cbEstudianteBeca.isChecked
                        )
                    )
                    intentDevolverParametros.putExtra("resultado", "OK")
                    setResult(RESULT_OK, intentDevolverParametros)
                    finish()
                }
            }
            "editar" -> {
                title = "Editar estudiante"
                btnGuardar.visibility = Button.VISIBLE
                loadEstudiante(id)
                btnGuardar.setOnClickListener {
                    FirebaseHelper().updateEstudiante(
                        Estudiante(
                            etEstudianteId.text.toString(),
                            etEstudianteNombre.text.toString(),
                            etEstudianteApellido.text.toString(),
                            SimpleDateFormat("dd/MM/yyyy").parse(etEstudianteFechaNacimiento.text.toString()),
                            etEstudianteDireccion.text.toString(),
                            etEstudianteCostoCredito.text.toString().toDouble(),
                            ArrayList(),
                            cbEstudianteBeca.isChecked
                        )
                    )
                    intentDevolverParametros.putExtra("resultado", "OK")
                    setResult(RESULT_OK, intentDevolverParametros)
                    finish()
                }
            }
            "ver" -> {
                title = "Ver estudiante"
                for (component in components) {
                    component.isEnabled = false
                }
                btnGuardar.visibility = Button.INVISIBLE
                loadEstudiante(id)
            }
        }

    }

    fun loadEstudiante(id: String) {
        val etEstudianteId = findViewById<EditText>(R.id.et_estudiante_id)
        val etEstudianteNombre = findViewById<EditText>(R.id.et_estudiante_nombre)
        val etEstudianteApellido = findViewById<EditText>(R.id.et_estudiante_apellido)
        val etEstudianteFechaNacimiento = findViewById<EditText>(R.id.et_estudiante_fecha)
        val etEstudianteDireccion = findViewById<EditText>(R.id.et_estudiante_direccion)
        val etEstudianteCostoCredito = findViewById<EditText>(R.id.et_estudiante_costo)
        val cbEstudianteBeca = findViewById<CheckBox>(R.id.cb_estudiante_beca)

        FirebaseHelper().readEstudiante(id) { estudiante ->
            etEstudianteId.setText(id)
            etEstudianteNombre.setText(estudiante.nombre)
            etEstudianteApellido.setText(estudiante.apellido)
            etEstudianteFechaNacimiento.setText(SimpleDateFormat("dd/MM/yyyy").format(estudiante.fechaNacimiento))
            etEstudianteDireccion.setText(estudiante.direccion)
            etEstudianteCostoCredito.setText(estudiante.costoCredito.toString())
            cbEstudianteBeca.isChecked = estudiante.beca
        }


    }
}