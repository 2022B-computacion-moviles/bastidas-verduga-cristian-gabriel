package com.example.crudfirebase

import Estudiante
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

class EstudianteView : AppCompatActivity() {

    var idSeleccionado = ""
    var estudiantesQ = ArrayList<Estudiante>()

    val contenidoIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val resultado = data.getStringExtra("resultado")
                    if (resultado == "OK") {
                        loadEstudiantes()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante)
        // Change title
        title = "Estudiantes"

        //FirebaseHelper().dropTables()
        // ESTUDIANTES
        val btnCrearEstudiante = findViewById<Button>(R.id.btn_crear_estudiante)


        btnCrearEstudiante.setOnClickListener {
            abrirActividadParametros(CrearEditarEstudiante::class.java, "crear", "")
        }

        loadEstudiantes()

        // MATERIAS
        val btnMaterias = findViewById<Button>(R.id.btn_materias)
        btnMaterias.setOnClickListener {
            abrirActividadParametros(MateriaView::class.java, modo = "crud", id = "")
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_estudiante, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idSeleccionado = estudiantesQ[info.position].id
        Log.i("firebase-firestore", "ID seleccionado: ${idSeleccionado}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_estudiante_editar -> {
                abrirActividadParametros(
                    CrearEditarEstudiante::class.java, "editar", idSeleccionado
                )
                true
            }
            R.id.menu_estudiante_eliminar -> {
                FirebaseHelper().eliminarEstudiante(idSeleccionado)
                loadEstudiantes()
                true
            }
            R.id.menu_estudiante_ver -> {
                abrirActividadParametros(
                    MateriaView::class.java, "ver", idSeleccionado
                )
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun loadEstudiantes() {
        val listView = findViewById<ListView>(R.id.lv_estudiante)
        FirebaseHelper().readEstudiantes { estudiantes ->
            val adaptador = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, estudiantes
            )
            listView.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listView)
            listView.setOnItemClickListener { _, _, position, _ ->
                abrirActividadParametros(
                    CrearEditarEstudiante::class.java, "ver", estudiantes[position].id
                )
            }
            estudiantesQ = estudiantes
        }
    }

    fun abrirActividadParametros(clase: Class<*>, modo: String, id: String) {
        val intentExplicito = Intent(
            this, clase
        )
        intentExplicito.putExtra("modo", modo)
        intentExplicito.putExtra("id", id)
        contenidoIntent.launch(intentExplicito)
    }
}