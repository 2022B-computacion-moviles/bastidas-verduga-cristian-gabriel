import java.text.SimpleDateFormat
import java.util.*

val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy");

class MainView {
    /**
     * Presenta la vista principal de la aplicación.
     */
    fun header(message: String): Unit {
        println("-".repeat(50));
        println(message.center(50));
        println("-".repeat(50));
    }

    /**
     * Presenta el menú de opciones de la aplicación.
     * @param opciones Lista de opciones a presentar
     * @return Int Opción seleccionada
     */
    fun menu(opciones: Map<String, () -> Unit>): Unit {
        while (true) {
            for (key in opciones.keys) {
                println(key);
            }
            println("0. Salir");
            print("Ingrese una opcion: ");
            val option = readLine()!!.toInt();
            if (option == 0) {
                header("MODULO DE INFORMACION ESTUDIANTIL");
                break;
            } else if (option in 1..opciones.keys.size) {
                opciones[opciones.keys.elementAt(option - 1)]!!();
            } else {
                println("Opcion invalida");
            }
        }
    }

    /**
     * Presenta el menu principal de la aplicación.
     * @return Int Opción seleccionada
     */
    fun mainMenu(): Unit {
        header("MODULO DE INFORMACION ESTUDIANTIL");
        this.menu(
            mapOf(
                "1. Estudiante" to ::estudianteMenu,
                "2. Materia" to ::materiaMenu,
            )
        );
    }

    /**
     * Presenta el menu de opciones para el estudiante.
     * @return Int Opción seleccionada
     */
    fun estudianteMenu(): Unit {
        header("ESTUDIANTE");
        this.menu(
            mapOf(
                "1. Crear estudiante" to ::estudianteCreate,
                "2. Listar estudiantes" to ::estudianteList,
                "3. Buscar estudiante" to ::estudianteSearch,
                "4. Actualizar estudiante" to ::estudianteUpdate,
                "5. Eliminar estudiante" to ::estudianteDelete,
                "6. Ingresar materia" to ::estudianteAddMateria,
            )
        );
    }

    fun estudianteCreate(): Unit {
        header("CREAR ESTUDIANTE");

        print("Ingrese el nombre del estudiante: ");
        val nombre = readLine()!!;
        print("Ingrese el apellido del estudiante: ");
        val apellido = readLine()!!;
        print("Ingrese la fecha de nacimiento del estudiante {dd/mm/aaaa}: ");
        val fechaNacimiento = readLine()!!;
        print("Ingrese la direccion del estudiante: ");
        val direccion = readLine()!!;
        print("Ingrese el costo del credito: ");
        val costoCredito = readLine()!!;
        print("El estudiante tiene beca? (s/n): ");
        val beca = readLine()!!;

        val id = DAO.readEstudiantes().size;
        val estudiante = Estudiante(
            id = id,
            nombre = nombre,
            apellido = apellido,
            fechaNacimiento = DATE_FORMAT.parse(fechaNacimiento),
            direccion = direccion,
            costoCredito = costoCredito.toDouble(),
            beca = beca == "s",
            materias = ArrayList(),
        );

        if (DAO.createEstudiante(estudiante))
            println("Estudiante creado exitosamente");
        else
            println("Error al crear el estudiante");
    }

    fun estudianteList(): Unit {
        header("LISTAR ESTUDIANTES");
        val estudiantes = DAO.readEstudiantes();
        println("ID\tNOMBRE\tAPELLIDO\tFECHA NACIMIENTO\tDIRECCION\tCOSTO CREDITO\tMATERIAS\tBECA");
        estudiantes.forEach { estudiante ->
            println(estudiante.customToString());
        }
    }

    fun estudianteSearch(): Unit {
        header("BUSCAR ESTUDIANTE");
        print("Ingrese el ID del estudiante: ");
        val id = readLine()!!;
        val estudiante = DAO.readEstudiante(id.toInt());
        if (estudiante.id == -1) {
            println("No se encontro el estudiante");
            return;
        }
        println("ID\tNOMBRE\tAPELLIDO\tFECHA NACIMIENTO\tDIRECCION\tCOSTO CREDITO\tMATERIAS\tBECA");
        println(estudiante.customToString());
    }

    fun estudianteUpdate(): Unit {
        header("ACTUALIZAR ESTUDIANTE");
        print("Ingrese el ID del estudiante: ");
        val id = readLine()!!;

        val es = DAO.readEstudiante(id.toInt());
        if (es.id == -1) {
            println("No se encontro el estudiante");
            return;
        }

        print("Ingrese el nombre del estudiante: ");
        val nombre = readLine()!!;
        print("Ingrese el apellido del estudiante: ");
        val apellido = readLine()!!;
        print("Ingrese la fecha de nacimiento del estudiante {dd/mm/aaaa}: ");
        val fechaNacimiento = readLine()!!;
        print("Ingrese la direccion del estudiante: ");
        val direccion = readLine()!!;
        print("Ingrese el costo del credito: ");
        val costoCredito = readLine()!!;
        print("El estudiante tiene beca? (s/n): ");
        val beca = readLine()!!;
        val estudiante = Estudiante(
            id = id.toInt(),
            nombre = nombre,
            apellido = apellido,
            fechaNacimiento = DATE_FORMAT.parse(fechaNacimiento),
            direccion = direccion,
            costoCredito = costoCredito.toDouble(),
            beca = beca == "s",
            materias = ArrayList(),
        );
        if (DAO.updateEstudiante(estudiante))
            println("Estudiante actualizado exitosamente");
        else
            println("Error al actualizar el estudiante");
    }

    fun estudianteDelete(): Unit {
        header("ELIMINAR ESTUDIANTE");
        print("Ingrese el ID del estudiante: ");
        val id = readLine()!!;

        val es = DAO.readEstudiante(id.toInt());
        if (es.id == -1) {
            println("No se encontro el estudiante");
            return;
        }

        if (DAO.deleteEstudiante(id.toInt()))
            println("Estudiante eliminado exitosamente");
        else
            println("Error al eliminar el estudiante");
    }

    fun estudianteAddMateria(): Unit {
        header("AGREGAR MATERIA A ESTUDIANTE");

        print("Ingrese el ID del estudiante: ");
        val id = readLine()!!;

        val es = DAO.readEstudiante(id.toInt());
        if (es.id == -1) {
            println("No se encontro el estudiante");
            return;
        }

        print("Ingrese el ID de la materia: ");
        val idMateria = readLine()!!;

        val ma = DAO.readMateria(idMateria.toInt());
        if (ma.id == -1) {
            println("No se encontro la materia");
            return;
        }

        if (DAO.addMateria(id.toInt(), idMateria.toInt()))
            println("Materia agregada exitosamente");
        else
            println("Error al agregar la materia");
    }

    fun materiaMenu(): Unit {
        header("MATERIAS");
        this.menu(
            mapOf(
                "1. Crear materia" to ::materiaCreate,
                "2. Listar materias" to ::materiaList,
                "3. Buscar materia" to ::materiaSearch,
                "4. Actualizar materia" to ::materiaUpdate,
                "5. Eliminar materia" to ::materiaDelete,
            )
        );
    }

    fun materiaCreate(): Unit {
        header("CREAR MATERIA");
        print("Ingrese el nombre de la materia: ");
        val nombre = readLine()!!;
        print("Ingrese el codigo de la materia: ");
        val codigo = readLine()!!;
        print("Ingrese el numero de creditos de la materia: ");
        val creditos = readLine()!!;
        print("Ingrese el costo del credito: ");
        val costoCredito = readLine()!!;
        print("Ingrese el aula de la materia: ");
        val aula = readLine()!!;

        val id = DAO.readMaterias().size;
        val materia = Materia(
            id = id,
            nombre = nombre,
            codigo = codigo,
            creditos = creditos.toInt(),
            aula = aula,
            costoCredito = costoCredito.toDouble(),
        );

        if (DAO.createMateria(materia))
            println("Materia creada exitosamente");
        else
            println("Error al crear la materia");
    }

    fun materiaList(): Unit {
        header("LISTAR MATERIAS");
        val materias = DAO.readMaterias();
        println("ID\tNOMBRE\tCODIGO\tCREDITOS\tCOSTO CREDITO");
        materias.forEach { materia ->
            println(materia.toString().replace(',', '\t'));
        }
    }

    fun materiaSearch(): Unit {
        header("BUSCAR MATERIA");
        print("Ingrese el ID de la materia: ");
        val id = readLine()!!;
        val materia = DAO.readMateria(id.toInt());
        if (materia.id == -1) {
            println("No se encontro la materia");
            return;
        }
        println("ID\tNOMBRE\tCODIGO\tCREDITOS\tCOSTO CREDITO");
        println(materia.toString().replace(',', '\t'));
    }

    fun materiaUpdate(): Unit {
        header("ACTUALIZAR MATERIA");
        print("Ingrese el ID de la materia: ");
        val id = readLine()!!;

        val ma = DAO.readMateria(id.toInt());
        if (ma.id == -1) {
            println("No se encontro la materia");
            return;
        }

        print("Ingrese el nombre de la materia: ");
        val nombre = readLine()!!;
        print("Ingrese el codigo de la materia: ");
        val codigo = readLine()!!;
        print("Ingrese el numero de creditos de la materia: ");
        val creditos = readLine()!!;
        print("Ingrese el costo del credito: ");
        val costoCredito = readLine()!!;
        print("Ingrese el aula de la materia: ");
        val aula = readLine()!!;

        val materia = Materia(
            id = id.toInt(),
            nombre = nombre,
            aula = aula,
            codigo = codigo,
            creditos = creditos.toInt(),
            costoCredito = costoCredito.toDouble(),
        );

        if (DAO.updateMateria(materia))
            println("Materia actualizada exitosamente");
        else
            println("Error al actualizar la materia");
    }

    fun materiaDelete(): Unit {
        header("ELIMINAR MATERIA");
        print("Ingrese el ID de la materia: ");
        val id = readLine()!!;

        val ma = DAO.readMateria(id.toInt());
        if (ma.id == -1) {
            println("No se encontro la materia");
            return;
        }

        if (DAO.deleteMateria(id.toInt()))
            println("Materia eliminada exitosamente");
        else
            println("Error al eliminar la materia");
    }
}