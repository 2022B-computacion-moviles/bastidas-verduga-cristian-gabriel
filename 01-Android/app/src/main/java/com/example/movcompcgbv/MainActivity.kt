package com.example.movcompcgbv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    val contenidoIntentExplicito =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    Log.i("intent-explicito", "Nombre: ${data?.getStringExtra("nombre")}")
                }
            }
        }

    val contenidoIntentImplicito =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data != null) {
                    if (result.data!!.data != null) {
                        val uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri,
                            null,
                            null,
                            null,
                            null,
                            null
                        )
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        Log.i("intent-implicito", "Telefono: $telefono")
                    }
                }
            }
        }

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

        val botonIntentImplicito = findViewById<Button>(R.id.btn_intent_implicito)
        botonIntentImplicito.setOnClickListener {
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            contenidoIntentImplicito.launch(intentConRespuesta)
        }

        val botonIntentExplicito = findViewById<Button>(R.id.btn_intent)
        botonIntentExplicito.setOnClickListener {
            abrirActividadConParametros(CIntentExplicitoParametros::class.java)
        }
    }

    fun abrirActividadConParametros(clase: Class<*>) {
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("nombre", "Cristian")
        intentExplicito.putExtra("apellido", "Bastidas")
        intentExplicito.putExtra("edad", 100)
        contenidoIntentExplicito.launch(intentExplicito)
    }

    fun iraActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}