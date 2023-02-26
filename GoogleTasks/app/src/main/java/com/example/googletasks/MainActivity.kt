package com.example.googletasks

import MenuElement
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    val contenidoIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val resultado = data.getStringExtra("resultado")
                    if (resultado == "OK") {
                        // PASS
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listaElementos = arrayListOf<TaskElement>()
        listaElementos.add(TaskElement("1", "Tarea 1", false))
        listaElementos.add(TaskElement("2", "Tarea 2", false))
        listaElementos.add(TaskElement("3", "Tarea 3", false))

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        inicializarRecyclerView(listaElementos, recyclerView)

        val btnSort = findViewById<Button>(R.id.btn_sort)
        btnSort.setOnClickListener {
            val listaElementos = arrayListOf<MenuElement>()
            listaElementos.add(MenuElement("Elemento 1", 1))
            listaElementos.add(MenuElement("Elemento 2", 2))
            listaElementos.add(MenuElement("Elemento 3", 0))

            abrirActividadParametros(DialogActivity::class.java, listaElementos)
        }

        val btnMenu = findViewById<Button>(R.id.btn_menu)
        btnMenu.setOnClickListener {
            val listaElementos = arrayListOf<MenuElement>()
            listaElementos.add(MenuElement("Elemento 1", 4))
            listaElementos.add(MenuElement("Elemento 2", 3))
            listaElementos.add(MenuElement("Elemento 3", 2))

            abrirActividadParametros(DialogActivity::class.java, listaElementos)
        }

        val fabBottomBar = findViewById<FloatingActionButton>(R.id.fab_bottom_bar)
        fabBottomBar.setOnClickListener {
            listaElementos.add(
                TaskElement(
                    "${listaElementos.size + 1}",
                    "Tarea ${listaElementos.size + 1}",
                    false
                )
            )
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    fun abrirActividadParametros(clase: Class<*>, listaElementos: ArrayList<MenuElement>) {
        val intentExplicito = Intent(
            this, clase
        )
        val bundle = Bundle()
        bundle.putSerializable("listaElementos", listaElementos)
        intentExplicito.putExtras(bundle)
        startActivity(intentExplicito)
    }

    private fun inicializarRecyclerView(
        listaElementos: ArrayList<TaskElement>, recyclerView: RecyclerView
    ) {
        val adaptador = TaskAdapter(this, listaElementos, recyclerView)
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

}