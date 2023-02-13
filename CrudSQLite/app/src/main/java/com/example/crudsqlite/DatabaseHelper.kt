package com.example.crudsqlite

import Estudiante
import Materia
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat

class DatabaseHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "estudiantes.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS Estudiante (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, fechaNacimiento TEXT, direccion TEXT, costoCredito REAL,  beca TEXT)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS Materia (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, codigo TEXT, aula TEXT, creditos INTEGER, costoCredito REAL)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS Inscripcion (id INTEGER PRIMARY KEY AUTOINCREMENT, idEstudiante INTEGER, idMateria INTEGER, FOREIGN KEY(idEstudiante) REFERENCES Estudiante(id), FOREIGN KEY(idMateria) REFERENCES Materia(id))")
    }

    fun dropTables() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS Estudiante")
        db.execSQL("DROP TABLE IF EXISTS Materia")
        db.execSQL("DROP TABLE IF EXISTS Inscripcion")
        onCreate(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Estudiante")
        onCreate(db)
    }

    fun crearEstudiante(estudiante: Estudiante) {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO Estudiante (nombre, apellido, fechaNacimiento, direccion, costoCredito, beca) VALUES ('${estudiante.nombre}', '${estudiante.apellido}', '${estudiante.fechaNacimiento}', '${estudiante.direccion}', ${estudiante.costoCredito}, '${estudiante.beca}')")
    }

    fun readEstudiante(id: Int): Estudiante {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Estudiante WHERE id = $id", null)
        cursor.moveToFirst()
        val materias =
            if (cursor.count > 0) readInscripcionEstudiante(cursor.getInt(0)) else ArrayList()
        val estudiante = Estudiante(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(cursor.getString(3)),
            cursor.getString(4),
            cursor.getDouble(5),
            materias,
            cursor.getString(6).toBoolean(),
        )
        cursor.close()
        db.close()
        return estudiante
    }

    fun readEstudiantes(): ArrayList<Estudiante> {
        val estudiantes = ArrayList<Estudiante>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Estudiante", null)
        if (cursor.moveToFirst()) {
            do {
                val materias = readInscripcionEstudiante(cursor.getInt(0))
                val estudiante = Estudiante(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(cursor.getString(3)),
                    cursor.getString(4),
                    cursor.getDouble(5),
                    materias,
                    cursor.getString(6).toBoolean()
                )
                estudiantes.add(estudiante)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return estudiantes
    }

    fun updateEstudiante(estudiante: Estudiante) {
        val db = this.writableDatabase
        db.execSQL("UPDATE Estudiante SET nombre = '${estudiante.nombre}', apellido = '${estudiante.apellido}', fechaNacimiento = '${estudiante.fechaNacimiento}', direccion = '${estudiante.direccion}', costoCredito = ${estudiante.costoCredito}, beca = '${estudiante.beca}' WHERE id = ${estudiante.id}")
    }

    fun eliminarEstudiante(id: Int) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM Inscripcion WHERE idEstudiante = $id")
        db.execSQL("DELETE FROM Estudiante WHERE id = $id")
    }

    fun readInscripcionEstudiante(idEstudiante: Int): ArrayList<Materia> {
        val materias = ArrayList<Materia>()
        val db = this.readableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM Inscripcion WHERE idEstudiante = $idEstudiante", null)
        if (cursor.moveToFirst()) {
            do {
                val materia = readMateria(cursor.getInt(2))
                materias.add(materia)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return materias
    }

    fun crearMateria(materia: Materia) {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO Materia (nombre, codigo, aula, creditos, costoCredito) VALUES ('${materia.nombre}', '${materia.codigo}', '${materia.aula}', ${materia.creditos}, ${materia.costoCredito})")
    }

    fun existMateriaId(id: Int): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Materia WHERE id = $id", null)
        if (cursor.moveToFirst()) {
            return true
        }
        cursor.close()
        db.close()
        return false
    }


    fun existInsripcion(idEstudiante: Int, idMateria: Int): Boolean {
        val db = this.readableDatabase
        val cursor =
            db.rawQuery(
                "SELECT * FROM Inscripcion WHERE idEstudiante = $idEstudiante AND idMateria = $idMateria",
                null
            )
        if (cursor.moveToFirst()) {
            return true
        }
        cursor.close()
        db.close()
        return false
    }

    fun readMateria(id: Int): Materia {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Materia WHERE id = $id", null)
        if (cursor.moveToFirst()) {
            do {
                val materia = Materia(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2) ?: "",
                    cursor.getString(3) ?: "",
                    cursor.getInt(4),
                    cursor.getDouble(5)
                )
                return materia
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return Materia(-1, "", "", "", -1, -1.0)
    }

    fun readMaterias(): ArrayList<Materia> {
        val materias = ArrayList<Materia>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Materia", null)
        if (cursor.moveToFirst()) {
            do {
                var x = cursor.getString(1);
                val materia = Materia(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2) ?: "",
                    cursor.getString(3) ?: "",
                    cursor.getInt(4),
                    cursor.getDouble(5)
                )
                materias.add(materia)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return materias
    }

    fun updateMateria(materia: Materia) {
        val db = this.writableDatabase
        db.execSQL("UPDATE Materia SET nombre = '${materia.nombre}', codigo = '${materia.codigo}', aula = '${materia.aula}', creditos = ${materia.creditos}, costoCredito = ${materia.costoCredito} WHERE id = ${materia.id}")
    }

    fun eliminarMateria(id: Int) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM Inscripcion WHERE idMateria = $id")
        db.execSQL("DELETE FROM Materia WHERE id = $id")
    }

    fun crearInscripcion(idEstudiante: Int, idMateria: Int) {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO Inscripcion (idEstudiante, idMateria) VALUES ($idEstudiante, $idMateria)")
    }
}