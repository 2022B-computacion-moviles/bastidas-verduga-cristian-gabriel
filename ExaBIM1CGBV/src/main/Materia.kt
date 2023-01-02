class Materia(
    val id: Int,
    val nombre: String,
    val codigo: String,
    val costo: Double,
    val aula: String,
    val horario: Map<String, ArrayList<String>>,
) {
    /**
     * Retorna un string con la información de la materia
     * @return String
     */
    override fun toString(): String {
        return "$id\t$nombre\t$codigo\t$costo\t$aula\t" + horario.joinToString()
    }
}