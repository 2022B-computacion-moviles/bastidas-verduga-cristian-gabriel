package com.example.movcompcgbv

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BListView : AppCompatActivity() {
    val arreglo: ArrayList<BEntrenador> = BBaseDatosMemoria.arregloBEntrenador
    var idItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonListView.setOnClickListener {
            anadirEntrenador(adaptador)
        }

        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                // Hacer algo
                return true
            }
            R.id.mi_eliminar -> {
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton(
            "Simon"
        ) { dialog,
            which ->
        }

        builder.setNegativeButton(
            "Nel",
            null
        )

        val opciones = resources.getStringArray(
            R.array.string_array_opciones_dialogo
        )

        val seleccionPrevia = booleanArrayOf(
            true, // Lunes SI
            false, // Martes No
            false // Miercoles No
        )
        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia
        ) { _,
            which,
            _ ->
            "Dio clic en el item $which"
        }
    }


    fun anadirEntrenador(adaptador: ArrayAdapter<BEntrenador>) {
        arreglo.add(BEntrenador("Senku", "Ishigami"))
        adaptador.notifyDataSetChanged()
    }
}