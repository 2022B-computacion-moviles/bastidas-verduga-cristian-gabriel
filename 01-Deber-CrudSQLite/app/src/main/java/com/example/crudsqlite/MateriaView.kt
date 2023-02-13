package com.example.crudsqlite

import Materia
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MateriaView : AppCompatActivity() {

    var materias = ArrayList<Materia>()
    var idSelecionado = 0
    var modo = "crud"
    val contenidoIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val resultado = data.getStringExtra("resultado")
                    if (resultado == "OK") {
                        loadMaterias()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materia)

        modo = intent.getStringExtra("modo").toString()
        var id = intent.getIntExtra("id", -1)

        var btnCrearMateria = findViewById<Button>(R.id.btn_crear_materia)

        when (modo) {
            "crud" -> {
                title = "Materias"
                btnCrearMateria.setOnClickListener {
                    abrirActividadParametros(CrearEditarMateria::class.java, "crear", 0)
                }
                loadMaterias()
            }
            "ver" -> {
                var nombreEstudiante = DatabaseHelper(this).readEstudiante(id).nombre
                title = "Materias de $nombreEstudiante"
                btnCrearMateria.text = "Inscribir"

                val etIdInscribir = findViewById<EditText>(R.id.et_id_inscribir)
                etIdInscribir.visibility = View.VISIBLE

                btnCrearMateria.setOnClickListener {
                    val idMateria = etIdInscribir.text.toString().toInt()

                    if (DatabaseHelper(this).existMateriaId(idMateria) && !DatabaseHelper(this).existInsripcion(
                            id,
                            idMateria
                        )
                    ) {
                        DatabaseHelper(this).crearInscripcion(id, idMateria)
                    } else {
                        Toast.makeText(
                            this,
                            "No existe la materia / Estudiante ya inscrito en esa materia",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    loadMateriasEstudiante(id)
                }
                loadMateriasEstudiante(id)
            }
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_materia, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idSelecionado = materias[info.position].id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_materia_editar -> {
                abrirActividadParametros(
                    CrearEditarMateria::class.java, "editar", idSelecionado
                )
                if (modo == "crud") {
                    loadMaterias()
                } else {
                    loadMateriasEstudiante(idSelecionado)
                }
                true
            }
            R.id.menu_materia_eliminar -> {
                DatabaseHelper(this).eliminarMateria(idSelecionado)
                if (modo == "crud") {
                    loadMaterias()
                } else {
                    loadMateriasEstudiante(idSelecionado)
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun loadMateriasEstudiante(id: Int) {
        val listView = findViewById<ListView>(R.id.lv_materia)
        materias = DatabaseHelper(this).readInscripcionEstudiante(id)
        val adaptador = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, materias
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        listView.setOnItemClickListener { parent, view, position, id ->
            abrirActividadParametros(
                CrearEditarMateria::class.java, "ver", materias[position].id
            )
        }
    }

    fun loadMaterias() {
        val listView = findViewById<ListView>(R.id.lv_materia)
        materias = DatabaseHelper(this).readMaterias()
        val adaptador = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, materias
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        listView.setOnItemClickListener { parent, view, position, id ->
            abrirActividadParametros(
                CrearEditarMateria::class.java, "ver", materias[position].id
            )
        }
    }

    fun abrirActividadParametros(clase: Class<*>, modo: String, id: Int) {
        val intentExplicito = Intent(
            this, clase
        )
        intentExplicito.putExtra("modo", modo)
        intentExplicito.putExtra("id", id)
        contenidoIntent.launch(intentExplicito)
    }
}