package com.example.googletasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listaElementos = arrayListOf<TaskElement>()
        listaElementos.add(TaskElement("1", "Tarea 1", false))
        listaElementos.add(TaskElement("2", "Tarea 2", false))
        listaElementos.add(TaskElement("3", "Tarea 3", false))

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        inicializarRecyclerView(listaElementos, recyclerView)
    }

    private fun inicializarRecyclerView(
        listaElementos: ArrayList<TaskElement>,
        recyclerView: RecyclerView
    ) {
        val adaptador = TaskAdapter(this, listaElementos, recyclerView)
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

}