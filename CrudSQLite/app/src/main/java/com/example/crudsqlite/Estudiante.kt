import java.util.*

/**
 * Clase que representa un estudiante con los siguientes atributos:
 *
 * @param id identificador único del estudiante
 * @param nombre nombre del estudiante
 * @param apellido apellido del estudiante
 * @param fechaNacimiento fecha de nacimiento del estudiante
 * @param direccion dirección del estudiante
 * @param costoCredito costo por crédito para el estudiante
 * @param materias materias que el estudiante está matriculado
 * @param beca si el estudiante tiene beca o no
 */
class Estudiante(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: Date,
    var direccion: String,
    var costoCredito: Double,
    var materias: ArrayList<Materia>,
    val beca: Boolean
) {
    /**
     * Constructor secundario que permite crear una instancia de Estudiante
     * a partir de una lista de strings, donde cada elemento representa un atributo.
     *
     * @param data lista de strings que contiene los valores de los atributos del estudiante
     */
    constructor(data: List<String>) : this(
        data[0].toInt(),
        data[1],
        data[2],
        DATE_FORMAT.parse(data[3]),
        data[4],
        data[5].toDouble(),
        ArrayList(),
        data[7] == "true"
    ) {
        /**
         * Se realiza un split a los ids de las materias y se buscan en el DAO para añadirlas al array de materias
         */
        data[6].split("-").forEach { idMateria ->
            if (idMateria != "") {
                val ma = DAO.readMateria(idMateria.toInt());
                if (ma.id != -1) {
                    materias.add(ma);
                }
            }
        }
    }

    /**
     * Sobrescribe el método toString() para devolver una representación en forma de string
     * de los atributos del estudiante en el siguiente formato: "id,nombre,apellido,fechaNacimiento,direccion,costoCredito,materias,beca"
     *
     * @return string que representa los atributos del estudiante
     */
    override fun toString(): String {
        var idMaterias = "";
        materias.forEach { materia ->
            idMaterias += materia.id.toString() + "-"
        }
        return "$id,$nombre,$apellido,${DATE_FORMAT.format(this.fechaNacimiento)},$direccion,$costoCredito,$idMaterias,$beca"
    }

    /**
     * Método que devuelve una representación personalizada del estudiante
     * con la lista de nombres de materias en lugar de los ids
     *
     * @return string que representa los atributos del estudiante de manera personalizada
     */
    fun customToString(): String {
        var nombreMaterias = "Inscrito en: ";
        materias.forEach { materia ->
            nombreMaterias += materia.nombre + ", ";
        }
        return "$id\t$nombre\t$apellido\t${DATE_FORMAT.format(this.fechaNacimiento)}\t$direccion\t$costoCredito\t$beca\n\t$nombreMaterias"
    }
}