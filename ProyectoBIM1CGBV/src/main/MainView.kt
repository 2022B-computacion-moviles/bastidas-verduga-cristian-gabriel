class MainView {
    /**
     * Presenta la vista principal de la aplicación.
     */
    fun header(): Unit {
        println("-".repeat(50));
        println("MODULO DE INFORMACION ESTUDIANTIL".center(50));
        println("-".repeat(50));
    }

    /**
     * Presenta el menú de opciones de la aplicación.
     * @param opciones Lista de opciones a presentar
     * @return Int Opción seleccionada
     */
    fun menu(Map<String, fun()> opciones): Unit
    {
        while (true) {
            for (key in opciones.keys) {
                println(key);
            }
            println("0. Salir");
            print("Ingrese una opcion: ");
            val option = readLine()!!;
            if (option == "0") {
                break;
            } else if (option in opciones.keys) {
                opciones[option]!!();
            } else {
                println("Opcion invalida");
            }
        }
    }

    /**
     * Presenta el menu principal de la aplicación.
     * @return Int Opción seleccionada
     */
    fun mainMenu(): Int {
        this.menu(
            mapOf(
                "1. Estudiante" to this.estudianteMenu,
                "2. Maestro" to this.maestroMenu,
            )
        );
    }

    /**
     * Presenta el menu de opciones para el estudiante.
     * @return Int Opción seleccionada
     */
    fun estudianteMenu(): Int {
        this.menu(
            mapOf(
                "1. Crear estudiante" to this.estudianteCreate,
                "2. Listar estudiante" to this.estudianteList,
                "3. Buscar estudiante" to this.estudianteSearch,
                "4. Actualizar estudiante" to this.estudianteUpdate,
                "5. Eliminar estudiante" to this.estudianteDelete,
                "6. Agregar materia" to this.estudianteAddMateria,
                "7. Regresar" to this.mainMenu,
            )
        );
    }

    fun estudianteCreate(): Boolean {
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
            nombre = nombre,
            apellido = apellido,
            fechaNacimiento = fechaNacimiento,
            direccion = direccion,
            costoCredito = costoCredito,
            beca = beca,
        );
        return DAO.estudianteCreate(estudiante);
    }

    fun estudianteList(): Unit {
        val estudiantes = DAO.estudianteList();
        println("ID\tNOMBRE\tAPELLIDO\tFECHA NACIMIENTO\tDIRECCION\tCOSTO CREDITO\tNUMERO MATERIAS\tBECA");
        estudiantes.forEach { estudiante ->
            println(estudiante);
        }
    }

    fun estudianteSearch(): Unit {
        print("Ingrese el ID del estudiante: ");
        val id = readLine()!!;
        val estudiante = DAO.estudianteSearch(id);
        if (estudiante != null) {
            println("ID\tNOMBRE\tAPELLIDO\tFECHA NACIMIENTO\tDIRECCION\tCOSTO CREDITO\tNUMERO MATERIAS\tBECA");
            println(estudiante);
        } else {
            println("El estudiante no existe");
        }
    }

    fun estudianteUpdate(): Boolean {
        print("Ingrese el ID del estudiante: ");
        val id = readLine()!!;
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
            id = id,
            nombre = nombre,
            apellido = apellido,
            fechaNacimiento = fechaNacimiento,
            direccion = direccion,
            costoCredito = costoCredito,
            beca = beca,
        );
        return DAO.estudianteUpdate(estudiante);
    }

    fun estudianteDelete(): Boolean {
        print("Ingrese el ID del estudiante: ");
        val id = readLine()!!;
        return DAO.estudianteDelete(id);
    }

    fun estudianteAddMateria(): Boolean {
        print("Ingrese el ID del estudiante: ");
        val id = readLine()!!;
        print("Ingrese el ID de la materia: ");
        val idMateria = readLine()!!;
        return DAO.estudianteAddMateria(id, idMateria);
    }


    fun materiaMenu(): Int {
        this.menu(
            mapOf(
                "1. Crear materia" to this.materiaCreate,
                "2. Listar materias" to this.materiaList,
                "3. Buscar materia" to this.materiaSearch,
                "4. Actualizar materia" to this.materiaUpdate,
                "5. Eliminar materia" to this.materiaDelete,
                "6. Regresar" to this.mainMenu,
            )
        );
    }

    fun materiaCreate(): Boolean {
        print("Ingrese el nombre de la materia: ");
        val nombre = readLine()!!;
        print("Ingrese el codigo de la materia: ");
        val codigo = readLine()!!;
        print("Ingrese el numero de creditos de la materia: ");
        val creditos = readLine()!!;
        print("Ingrese el costo del credito: ");
        val costoCredito = readLine()!!;
        print("Ingrese el ID del estudiante: ");
        val estudianteId = readLine()!!;
        val materia = Materia(
            nombre = nombre,
            codigo = codigo,
            creditos = creditos,
            costoCredito = costoCredito,
            estudianteId = estudianteId,
        );
        return DAO.materiaCreate(materia);
    }

    fun materiaList(): Unit {
        val materias = DAO.materiaList();
        println("ID\tNOMBRE\tCODIGO\tCREDITOS\tCOSTO CREDITO\tESTUDIANTE ID");
        materias.forEach { materia ->
            println(materia);
        }
    }

    fun materiaSearch(): Unit {
        print("Ingrese el ID de la materia: ");
        val id = readLine()!!;
        val materia = DAO.materiaSearch(id);
        if (materia != null) {
            println("ID\tNOMBRE\tCODIGO\tCREDITOS\tCOSTO CREDITO\tESTUDIANTE ID");
            println(materia);
        } else {
            println("La materia no existe");
        }
    }

    fun materiaUpdate(): Boolean {
        print("Ingrese el ID de la materia: ");
        val id = readLine()!!;
        print("Ingrese el nombre de la materia: ");
        val nombre = readLine()!!;
        print("Ingrese el codigo de la materia: ");
        val codigo = readLine()!!;
        print("Ingrese el numero de creditos de la materia: ");
        val creditos = readLine()!!;
        print("Ingrese el costo del credito: ");
        val costoCredito = readLine()!!;
        print("Ingrese el ID del estudiante: ");
        val estudianteId = readLine()!!;
        val materia = Materia(
            id = id,
            nombre = nombre,
            codigo = codigo,
            creditos = creditos,
            costoCredito = costoCredito,
            estudianteId = estudianteId,
        );
        return DAO.materiaUpdate(materia);
    }

    fun materiaDelete(): Boolean {
        print("Ingrese el ID de la materia: ");
        val id = readLine()!!;
        return DAO.materiaDelete(id);
    }
}