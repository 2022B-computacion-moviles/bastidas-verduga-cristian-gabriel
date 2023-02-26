package com.example.googletasks

import MenuElement
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class DialogActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_rv)

        val bundle = intent.extras
        val listaElementos = bundle?.getSerializable("listaElementos") as ArrayList<MenuElement>

        val recyclerView = findViewById<RecyclerView>(R.id.rv_dialog)
        inicializarRecyclerView(listaElementos, recyclerView)

    }

    private fun inicializarRecyclerView(
        listaElementos: ArrayList<MenuElement>, recyclerView: RecyclerView
    ) {
        val adaptador = MenuAdapter(this, listaElementos, recyclerView)
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }
}
