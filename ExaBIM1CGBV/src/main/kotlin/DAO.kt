import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class DAO {
    companion object {
        /**
         * Crea un archivo con el nombre especificado y extensión .csv
         *
         * @param name nombre del archivo a crear
         */
        fun createFile(name: String): Unit {
            // Crea un objeto File con el nombre especificado y extensión .csv
            val file = File("$name.csv");
            // Verifica si el archivo ya existe
            if (!file.exists()) {
                // Si no existe, crea un nuevo archivo
                file.createNewFile();
            }
        }


        fun parseCSV(data: String): List<List<String>> {
            val lines = data.split("\n");
            val result = mutableListOf<List<String>>();
            for (line in lines) {
                if (line.isNotEmpty()) {
                    result.add(line.split(","));
                }
            }
            return result;
        }

        fun toCSV(E: MutableList<Estudiante>? = null, M: MutableList<Materia>? = null): String {
            val data = E ?: M;
            var result = "";
            for (line in data!!) {
                result += line.toString() + "\n";
            }
            return result;
        }


        fun readFile(name: String): String {
            createFile(name);
            val file = File("$name.csv");
            return file.readText();
        }

        fun writeFile(name: String, data: String): Unit {
            createFile(name);
            val file = File("$name.csv");
            file.writeText(data);
        }

        fun createEstudiante(estudiante: Estudiante): Boolean {
            val data = readFile("estudiantes");
            val estudiantes = parseCSV(data).map { Estudiante(it) }.toMutableList();
            estudiantes.add(estudiante);
            writeFile("estudiantes", toCSV(E = estudiantes));
            return true;
        }

        fun readEstudiante(id: Int): Estudiante {
            val data = readFile("estudiantes");
            val estudiantes = parseCSV(data).map { Estudiante(it) }.toMutableList();
            val R = estudiantes.find { estudiante -> estudiante.id == id };
            if (R != null) {
                return R;
            }
            return Estudiante(-1, "", "", Date(), "", 0.0, ArrayList(), false);
        }

        fun readEstudiantes(): List<Estudiante> {
            val data = readFile("estudiantes");
            val estudiantes = parseCSV(data).map { Estudiante(it) }.toMutableList();
            return estudiantes;
        }

        fun updateEstudiante(estudiante: Estudiante): Boolean {
            val data = readFile("estudiantes");
            val estudiantes = parseCSV(data).map { Estudiante(it) }.toMutableList();
            val index = estudiantes.indexOfFirst { it.id == estudiante.id };
            estudiantes[index] = estudiante;
            writeFile("estudiantes", toCSV(E = estudiantes));
            return true;
        }

        fun deleteEstudiante(id: Int): Boolean {
            val data = readFile("estudiantes");
            val estudiantes = parseCSV(data).map { Estudiante(it) }.toMutableList();
            val index = estudiantes.indexOfFirst { it.id == id };
            estudiantes.removeAt(index);
            writeFile("estudiantes", toCSV(E = estudiantes));
            return true;
        }

        fun addMateria(id: Int, idMateria: Int): Boolean {
            val data = readFile("estudiantes");
            val estudiantes = parseCSV(data).map { Estudiante(it) }.toMutableList();
            val index = estudiantes.indexOfFirst { it.id == id };
            estudiantes[index].materias.add(readMaterias().find { it.id == idMateria }!!);
            writeFile("estudiantes", toCSV(E = estudiantes));
            return true;
        }

        /**
         * **************************************************
         * Materia
         * **************************************************
         **/
        fun createMateria(materia: Materia): Boolean {
            val data = readFile("materias");
            val materias = parseCSV(data).map { Materia(it) }.toMutableList();
            materias.add(materia);
            writeFile("materias", toCSV(M = materias));
            return true;
        }

        fun readMateria(id: Int): Materia {
            val data = readFile("materias");
            val materias = parseCSV(data).map { Materia(it) }.toMutableList();
            val R = materias.find { materia -> materia.id == id };
            if (R != null) {
                return R;
            }
            return Materia(-1, "", "", "", 0, 0.0);
        }

        fun readMaterias(): List<Materia> {
            val data = readFile("materias");
            val materias = parseCSV(data).map { Materia(it) }.toMutableList();
            return materias;
        }

        fun updateMateria(materia: Materia): Boolean {
            val data = readFile("materias");
            val materias = parseCSV(data).map { Materia(it) }.toMutableList();
            val index = materias.indexOfFirst { it.id == materia.id };
            materias[index] = materia;
            writeFile("materias", toCSV(M = materias));
            return true;
        }

        fun deleteMateria(id: Int): Boolean {
            val data = readFile("materias");
            val materias = parseCSV(data).map { Materia(it) }.toMutableList();
            val index = materias.indexOfFirst { it.id == id };
            materias.removeAt(index);
            writeFile("materias", toCSV(M = materias));
            return true;
        }
    }
}