@file:Suppress("SpellCheckingInspection")

fun main(args: Array<String>) {
    println("Hola mundo");

    // Tipos de variables

    // Inmutables: no se pueden reasignar, pero si se puede modificar. Pueden servir de constante
    val inmutable: String = "Cristian";

    // Mutables
    var mutable: String = "Cristian";
    mutable = "$mutable Bastidas";
    println("Hola $mutable");

    // Usar val antes que var hasta que sea necesario

    // ----------------------------------------------------------------------------------------------
    // Duck Typing
    // En Kotlin es posible omitir el tipo y el punto y coma
    val ejemploVariable = "Ejemplo  ";
    ejemploVariable.trim();

    val edadEjemplo: Int = 12;

    // Primitivas
    val nombreProfesor: String = "Cristian Bastidas";
    val sueldo: Double = 1.2;
    val estadoCivil: Char = 'S';
    val mayorEdad: Boolean = true;

    // NO se usa new para instanciar una clase

    // If - Else funciona como en Java
    // No existe switch, pero en su lugar tenemos When
    val estadoCivilWhen = "S";
    when (estadoCivilWhen) {
        ("S") -> {
            println("Soltero");
        }

        "C" -> println("Casado");
        else -> println("Desconocido");
    }

    // Sentencia If - Else de una sola línea
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No";

    val sumaUno = Suma(uno = 1, dos = 2);
    val sumaDos = Suma(1, null);
    val sumaTres = Suma(null, 2);
    val sumaCuatro = Suma(null, null);

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()

    println(Suma.historialSuma)

    // ----------------------------------------------------------------------------------------------
    // Arreglos
    // Arreglos estáticos
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3);
    println(arregloEstatico);

    // Arreglos dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 4, 5, 6, 7, 8, 9, 10);
    println(arregloDinamico);
    arregloDinamico.add(11);
    arregloDinamico.add(12);
    println(arregloDinamico);

    // ----------------------------------------------------------------------------------------------
    // Operadores -> Sirven para los arreglos estáticos y dinámicos
    // For Each -> Unit
    val respuestaForEach: Unit = arregloDinamico.forEach { valorActual: Int ->
        println("Valor actual: $valorActual");
    }

    arregloEstatico.forEachIndexed { indice: Int, valorActual: Int ->
        println("Valor actual: $valorActual, indice: $indice");
    }

    print(respuestaForEach);

    // ----------------------------------------------------------------------------------------------
    // Map -> muta el arreglo (Cambia el arreglo)
    // 1. Enviemos el nuevo valor de la iteración
    // 2. Nos devuelve un nuevo arreglo con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico.map { valorActual: Int -> toDoublePlus100(valorActual) };
    //    return@map valorActual.toDouble() + 100.0;
    //}

    println(respuestaMap);

    val respuestaMapDos = arregloDinamico.map { it + 15 };

    // ----------------------------------------------------------------------------------------------
    // Filter -> Filtra el arreglo (No cambia el arreglo)
    // 1. Devolver una expresión booleana
    // 2. Nuevo arreglo Filtrado
    val respuestaFilter: List<Int> = arregloDinamico.filter { valorActual: Int ->
        val mayoresACinco: Boolean = valorActual > 5;
        return@filter mayoresACinco;
    }

    val respuestaFilterDos = arregloDinamico.filter { it > 5 };

    println(respuestaFilter);
    println(respuestaFilterDos);

    // ----------------------------------------------------------------------------------------------
    // OR, AND
    // OR -> any
    // AND -> all

    // ¿Hay algún valor mayor a 5?
    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5);
        }
    println(respuestaAny);

    // ¿Todos los valores son mayores a 5?
    val respuestaAll: Boolean = arregloDinamico.all { x: Int -> return@all (x > 5) }
    println(respuestaAll);

    // ----------------------------------------------------------------------------------------------
    // Reduce -> Reduce el arreglo a un solo valor
    // 1. Valor acumulado (Inicialmente el primer valor del arreglo)
    // 2. Valor actual
    // 3. Nuevo valor acumulado
    val respuestaReduce: Int = arregloDinamico.reduce { valorAcumulado: Int, valorActual: Int ->
        return@reduce valorAcumulado + valorActual;
    }
}

fun toDoublePlus100(valorActual: Int): Double {
    return valorActual.toDouble() + 100.0;
}

// Funciones
fun imprimirNombre(nombre: String): Unit { // Unit = Void
    println("$nombre");
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional por defecto
    bonoEspecial: Double? = null // Opcional nulo: puede ser nulo en algún momento
): Double {
    return (sueldo * tasa * bonoEspecial!!);
}

// Clases
abstract class NumerosJava {
    protected val numeroUno: Int;
    private val numeroDos: Int;

    constructor(uno: Int, dos: Int) {
        this.numeroUno = uno;
        this.numeroDos = dos;
        println("Iniciando");
    }
}

abstract class Numeros( // Constructor Primario
    // uno: Int // Parametro
    // public var uno: Int // propiedad de la clase pública
    protected var numeroUno: Int,
    protected var numeroDos: Int
) {
    init { // Bloque de código constructor primario
        numeroUno
        numeroDos
        println("Iniciando")
    }
}

class Suma(// Constructor Primario Suma
    uno: Int,
    dos: Int
) : Numeros(
// Heredamos de la clase Numeros, Super constructor Numeros
    uno,
    dos,
) {
    init { // Bloque de constructor primario
        this.numeroUno
        this.numeroDos
    }

    constructor( // Segundo Constructor
        uno: Int?, // Parámetros
        dos: Int // Parámetros
    ) : this(
        uno ?: 0, dos
    ) // También se pueden omitir las llaves de bloque

    constructor( // Tercer Constructor
        uno: Int, // Parámetros
        dos: Int? // Parámetros
    ) : this(
        uno,
        dos ?: 0
    ) // También se pueden omitir las llaves de bloque

    constructor( // Cuarto Constructor
        uno: Int?, // Parámetros
        dos: Int? // Parámetros
    ) : this(
        uno ?: 0,
        dos ?: 0
    ) // También se pueden omitir las llaves de bloque

    fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object { // Los valores que se guardan aquí es por toda la clase. No únicamente por instancia
        val pi = 3.14 // Suma.pi -> 3.14
        val historialSuma = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int) {
            historialSuma.add(valorNuevaSuma)
        }
    }
}