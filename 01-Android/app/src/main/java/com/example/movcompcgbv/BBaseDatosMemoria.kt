package com.example.movcompcgbv

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador.add(
                BEntrenador("Crixodia", "crixodia@uwu.com")
            )

            arregloBEntrenador.add(
                BEntrenador("John Doe", "doe@uwu.com")
            )

            arregloBEntrenador.add(
                BEntrenador("Allison", "allison@uwu.com")
            )
        }
    }
}