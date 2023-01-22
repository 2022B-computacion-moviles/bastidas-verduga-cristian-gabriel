/**
 * La función principal del programa.
 * Crea una instancia de MainView y llama al método mainMenu.
 *
 * @param args argumentos pasados al programa al ser ejecutado
 */
fun main(args: Array<String>) {
    val mainView = MainView()
    mainView.mainMenu();
}

/**
 * Extension function que permite centrar una cadena en una longitud específica.
 *
 * @param i longitud deseada para la cadena centrada
 * @return la cadena centrada en la longitud especificada
 */
fun String.center(i: Int): Any {
    return this.padStart((this.length + i) / 2).padEnd(i)
}
