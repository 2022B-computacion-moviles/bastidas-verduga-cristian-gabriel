package com.example.movcompcgbv

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador.add(
                BEntrenador(2,"Crixodia", "crixodia@uwu.com")
            )

            arregloBEntrenador.add(
                BEntrenador(3,"John Doe", "doe@uwu.com")
            )

            arregloBEntrenador.add(
                BEntrenador(4,"Allison", "allison@uwu.com")
            )
        }
    }
}