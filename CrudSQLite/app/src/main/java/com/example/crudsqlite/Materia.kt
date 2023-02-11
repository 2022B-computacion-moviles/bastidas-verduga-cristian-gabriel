/**
 * Clase que representa una materia con los siguientes atributos:
 *
 * @param id identificador único de la materia
 * @param nombre nombre de la materia
 * @param codigo código de la materia
 * @param aula aula en la que se imparte la materia
 * @param creditos número de créditos de la materia
 * @param costoCredito costo por crédito de la materia
 */
class Materia(
    val id: Int,
    val nombre: String,
    val codigo: String,
    val aula: String,
    val creditos: Int,
    val costoCredito: Double,
) {
    /**
     * Constructor secundario que permite crear una instancia de Materia
     * a partir de una lista de strings, donde cada elemento representa un atributo.
     *
     * @param data lista de strings que contiene los valores de los atributos de la materia
     */
    constructor(data: List<String>) : this(
        data[0].toInt(),
        data[1],
        data[2],
        data[3],
        data[4].toInt(),
        data[5].toDouble(),
    )

    /**
     * Sobrescribe el método toString() para devolver una representación en forma de string
     * de los atributos de la materia en el siguiente formato: "id,nombre,codigo,aula,creditos,costoCredito"
     *
     * @return string que representa los atributos de la materia
     */
    override fun toString(): String {
        return "$id,$nombre,$codigo,$aula,$creditos,$costoCredito";
    }
}
