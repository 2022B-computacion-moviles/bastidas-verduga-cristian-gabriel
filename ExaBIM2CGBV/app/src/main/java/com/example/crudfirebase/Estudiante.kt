import java.text.SimpleDateFormat
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
    val id: String,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: Date,
    var direccion: String,
    var costoCredito: Double,
    var materias: ArrayList<Materia>,
    val beca: Boolean
) {

    /**
     * Sobrescribe el método toString() para devolver una representación en forma de string
     * de los atributos del estudiante en el siguiente formato: "id,nombre,apellido,fechaNacimiento,direccion,costoCredito,materias,beca"
     *
     * @return string que representa los atributos del estudiante
     */
    override fun toString(): String {
        /*var idMaterias = "";
        materias.forEach { materia ->
            idMaterias += materia.id.toString() + "-"
        }*/
        return "$id  $nombre  $apellido  ${SimpleDateFormat("dd/MM/yyyy").format(this.fechaNacimiento)}  $direccion  $costoCredito  $beca"
    }
}