package com.example.movcompcgbv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import java.util.Date

class JFirebaseFirestore : AppCompatActivity() {

    var query: Query? = null
    val arreglo: ArrayList<JCitiesDto> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jfirebase_firestore)

        val listView = findViewById<ListView>(R.id.lv_firestore)
        val adaptador: ArrayAdapter<JCitiesDto> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, arreglo)

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonDatosPrueba = findViewById<Button>(R.id.btn_fs_datos_prueba)
        botonDatosPrueba.setOnClickListener {
            crearDatosPrueba()
        }

        val botonFirebaseCrear = findViewById<Button>(R.id.btn_fs_crear)
        botonFirebaseCrear.setOnClickListener {
            crearDatosEjemplo()
        }
    }

    fun crearDatosEjemplo() {
        val db = FirebaseFirestore.getInstance()
        val referenciaEjemploEstudiante =
            db.collection("ejemplo").document("1234567890").collection("estudiante")
        val identificador = Date().time.toString()
        val datosEstudiante = hashMapOf(
            "nombre" to "Juan",
            "graduado" to false,
            "promedio" to 4.5,
            "direccion" to hashMapOf(
                "direccion" to "Calle 1",
                "numero" to 123,
            ),
            "materias" to listOf(
                "web",
                "movil",
            ),
        )

        referenciaEjemploEstudiante.document(identificador).set(datosEstudiante).addOnCompleteListener() {
            if (it.isSuccessful) {
                println("Se guardo correctamente")
            } else {
                println("No se guardo correctamente")
            }
        }

        referenciaEjemploEstudiante.document(identificador).get().addOnCompleteListener() {}.addOnFailureListener() {
            println("No se pudo obtener el documento")
        }.addOnSuccessListener() {
            println("Se obtuvo el documento")
            println(it.get("nombre"))
            println(it.get("graduado"))
            println(it.get("promedio"))
            println(it.get("direccion"))
            println(it.get("materias"))
        }

    }

    fun crearDatosPrueba() {
        val db = FirebaseFirestore.getInstance()
        val cities = db.collection("cities")
        cities.add(
            JCitiesDto(
                "Los Angeles",
                "CA",
                "USA",
                false,
                3900000,
                listOf("west_coast", "socal")
            )
        )
        cities.add(
            JCitiesDto(
                "San Francisco",
                "CA",
                "USA",
                false,
                550000,
                listOf("west_coast", "norcal")
            )
        )
        cities.add(JCitiesDto("Tokyo", null, "Japan", true, 9000000, listOf("kanto", "honshu")))
        cities.add(
            JCitiesDto(
                "Beijing",
                null,
                "China",
                true,
                21500000,
                listOf("jingjinji", "hebei")
            )
        )
    }

    fun limpiarArreglo() {
        arreglo.clear()
    }

    fun anadirAArregloCiudad(
        arregloNuevo: ArrayList<JCitiesDto>,
        ciudad: QueryDocumentSnapshot,
        adaptador: ArrayAdapter<JCitiesDto>
    ) {
        ciudad.id
        val nuevaCiudad = JCitiesDto(
            ciudad.get("name") as String,
            ciudad.get("state") as String,
            ciudad.get("country") as String,
            ciudad.get("capital") as Boolean,
            ciudad.get("population") as Long,
            ciudad.get("regions") as List<String>
        )

        arregloNuevo.add(nuevaCiudad)
        adaptador.notifyDataSetChanged()
    }


}