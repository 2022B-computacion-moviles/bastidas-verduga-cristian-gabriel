package com.example.crudfirebase

import Estudiante
import Materia
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FirebaseHelper {

    fun crearEstudiante(estudiante: Estudiante) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Estudiante").add(estudiante)
    }

    fun readEstudiante(
        id: String, callback: (Estudiante) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        var estudiante = Estudiante("", "", "", Date(), "", 0.0, ArrayList(), false)
        db.collection("Estudiante").document(id).get().addOnSuccessListener { document ->
            if (document != null) {
                var materias = ArrayList<Materia>()
                estudiante = Estudiante(
                    document.id,
                    document.data!!["nombre"].toString(),
                    document.data!!["apellido"].toString(),
                    SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(
                        document.getTimestamp("fechaNacimiento")!!?.toDate().toString()
                    ),
                    document.data!!["direccion"].toString(),
                    document.data!!["costoCredito"].toString().toDouble(),
                    materias,
                    document.data!!["beca"]?.toString().toBoolean(),
                )
                callback(estudiante)
            }
        }
    }

    fun readEstudiantes(callback: (ArrayList<Estudiante>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val estudiantes = ArrayList<Estudiante>()
        db.collection("Estudiante").get().addOnSuccessListener { result ->
            for (document in result) {
                var materias = ArrayList<Materia>()
                val estudiante = Estudiante(
                    document.id,
                    document.data["nombre"].toString(),
                    document.data["apellido"].toString(),
                    SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(
                        document.getTimestamp("fechaNacimiento")!!?.toDate().toString()
                    ),
                    document.data["direccion"].toString(),
                    document.data["costoCredito"].toString().toDouble(),
                    materias,
                    document.data["beca"].toString().toBoolean(),
                )
                estudiantes.add(estudiante)
            }
            callback(estudiantes)
        }
    }

    fun updateEstudiante(estudiante: Estudiante) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Estudiante").document(estudiante.id).set(estudiante)
    }

    fun eliminarEstudiante(id: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Estudiante").document(id).delete()
        db.collection("Inscripcion").whereEqualTo("idEstudiante", id).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    db.collection("Inscripcion").document(document.id).delete()
                }
            }
    }

    fun readInscripcionEstudiante(id: String, callback: (ArrayList<String>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val materiasIds = ArrayList<String>()
        db.collection("Inscripcion").whereEqualTo("idEstudiante", id).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    materiasIds.add(document.data["idMateria"].toString())
                }
                callback(materiasIds)
            }
    }

    fun crearMateria(materia: Materia) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Materia").add(materia)
    }

    fun readMateria(id: String, callback: (Materia) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        var materia = Materia("", "", "", "", 0, 0.0)
        db.collection("Materia").document(id).get().addOnSuccessListener { document ->
            if (document != null) {
                materia = Materia(
                    document.id,
                    document.data!!["nombre"].toString(),
                    document.data!!["codigo"].toString(),
                    document.data!!["aula"].toString(),
                    document.data!!["creditos"].toString().toInt(),
                    document.data!!["costoCredito"].toString().toDouble()
                )
                callback(materia)
            }
        }
    }

    fun readMaterias(callback: (ArrayList<Materia>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val materias = ArrayList<Materia>()
        db.collection("Materia").get().addOnSuccessListener { result ->
            for (document in result) {
                val materia = Materia(
                    document.id,
                    document.data["nombre"].toString(),
                    document.data["codigo"].toString(),
                    document.data["aula"].toString(),
                    document.data["creditos"].toString().toInt(),
                    document.data["costoCredito"].toString().toDouble()
                )
                materias.add(materia)
            }
            callback(materias)
        }
    }

    fun updateMateria(materia: Materia) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Materia").document(materia.id.toString()).set(materia)
    }

    fun eliminarMateria(id: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Materia").document(id.toString()).delete()
        db.collection("Inscripcion").whereEqualTo("idMateria", id).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    db.collection("Inscripcion").document(document.id).delete()
                }
            }
    }

    fun crearInscripcion(idEstudiante: String, idMateria: String) {
        val db = FirebaseFirestore.getInstance()
        val inscripcion = hashMapOf(
            "idEstudiante" to idEstudiante, "idMateria" to idMateria
        )
        db.collection("Inscripcion").add(inscripcion)
    }

    fun existInsripcion(idEstudiante: String, idMateria: String, callback: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        var exist = false
        db.collection("Inscripcion").whereEqualTo("idEstudiante", idEstudiante)
            .whereEqualTo("idMateria", idMateria).get().addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        exist = true
                    }
                    callback(exist)
                }
            }
    }
}