class Estudiante(
    val id: Int,
    val nombre: Sring,
    val apellido: String,
    val fechaNacimiento: Date,
    var direccion: String,
    var materias: List<Materia>,
    var costoCredito: Double,
    val beca: Boolean
) {
    /**
     * Ingresa una nueva materia al estudiante
     * @param materia Materia a ingresar
     * @return Unit
     */
    fun ingresarMateria(materia: Materia): Unit {
        materias.add(materia)
    }

    /**
     * Elimina una materia del estudiante
     * @param materia Materia a eliminar
     * @return Unit
     */
    fun eliminarMateria(materia: Materia): Unit {
        materias.remove(materia)
    }

    /**
     * Calcula el costo total de las materias del estudiante
     * @return Double
     */
    fun calcularCostoCarrera(): Double {
        var costoCarrera = 0.0
        materias.forEach { materia ->
            costoCarrera += materia.costo
        }
        return costoCarrera * costoCredito
    }

    /**
     * Obtiene la edad del estudiante
     * @return Int
     */
    fun getEdad(): Int {
        val fechaActual = Date()
        val edad = fechaActual.year - fechaNacimiento.year
        return edad
    }

    /**
     * Retorna un string con la información del estudiante
     * @return String
     */
    override fun toString(): String {
        val numero_materias: Int = materias.size;
        return "$id\t$nombre\t$apellido\t$fechaNacimiento\t$direccion\t$costoCredito\t$numero_materias\t$beca"
    }
}