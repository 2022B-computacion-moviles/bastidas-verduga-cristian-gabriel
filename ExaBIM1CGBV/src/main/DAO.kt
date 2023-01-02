class DAO {
    fun createFile(name: String): Unit {
        val file = File("src\\main\\data\\$name.json");
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    fun readFile(name: String): String {
        createFile(name);
        val file = File("src\\main\\data\\$name.json");
        return file.readText();
    }

    fun writeFile(name: String, data: String): Unit {
        createFile(name);
        val file = File("src\\main\\data\\$name.json");
        file.writeText(data);
    }

    /**
     * **************************************************
     * Materia
     * **************************************************
     **/
    fun createEstudiante(estudiante: Estudiante): Boolean {
        val data = readFile("estudiantes");
        val estudiantes = Gson().fromJson(data, Array<Estudiante>::class.java).toMutableList();
        estudiantes.add(estudiante);
        writeFile("estudiantes", Gson().toJson(estudiantes));
        return true;
    }

    fun readEstudiante(id: Int): Estudiante {
        val data = readFile("estudiantes");
        val estudiantes = Gson().fromJson(data, Array<Estudiante>::class.java).toMutableList();
        return estudiantes.find { estudiante -> estudiante.id == id }!!;
    }

    fun readEstudiantes(): List<Estudiante> {
        val data = readFile("estudiantes");
        val estudiantes = Gson().fromJson(data, Array<Estudiante>::class.java).toMutableList();
        return estudiantes;
    }

    fun updateEstudiante(estudiante: Estudiante): Boolean {
        val data = readFile("estudiantes");
        val estudiantes = Gson().fromJson(data, Array<Estudiante>::class.java).toMutableList();
        val index = estudiantes.indexOfFirst { it.id == estudiante.id };
        estudiantes[index] = estudiante;
        writeFile("estudiantes", Gson().toJson(estudiantes));
        return true;
    }

    fun deleteEstudiante(id: Int): Boolean {
        val data = readFile("estudiantes");
        val estudiantes = Gson().fromJson(data, Array<Estudiante>::class.java).toMutableList();
        val index = estudiantes.indexOfFirst { it.id == id };
        estudiantes.removeAt(index);
        writeFile("estudiantes", Gson().toJson(estudiantes));
        return true;
    }

    /**
     * **************************************************
     * Materia
     * **************************************************
     **/
    fun createMateria(materia: Materia): Boolean {
        val data = readFile("materias");
        val materias = Gson().fromJson(data, Array<Materia>::class.java).toMutableList();
        materias.add(materia);
        writeFile("materias", Gson().toJson(materias));
        return true;
    }

    fun readMateria(id: Int): Materia {
        val data = readFile("materias");
        val materias = Gson().fromJson(data, Array<Materia>::class.java).toMutableList();
        return materias.find { materia -> materia.id == id }!!;
    }

    fun readMaterias(): List<Materia> {
        val data = readFile("materias");
        val materias = Gson().fromJson(data, Array<Materia>::class.java).toMutableList();
        return materias;
    }

    fun updateMateria(materia: Materia): Boolean {
        val data = readFile("materias");
        val materias = Gson().fromJson(data, Array<Materia>::class.java).toMutableList();
        val index = materias.indexOfFirst { it.id == materia.id };
        materias[index] = materia;
        writeFile("materias", Gson().toJson(materias));
        return true;
    }

    fun deleteMateria(id: Int): Boolean {
        val data = readFile("materias");
        val materias = Gson().fromJson(data, Array<Materia>::class.java).toMutableList();
        val index = materias.indexOfFirst { it.id == id };
        materias.removeAt(index);
        writeFile("materias", Gson().toJson(materias));
        return true;
    }

}