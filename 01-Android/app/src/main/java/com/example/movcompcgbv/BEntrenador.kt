package com.example.movcompcgbv

class BEntrenador(
    var nombre: String,
    var descripcion: String,
) {
    override fun toString(): String {
        return "$nombre - $descripcion"
    }
}