fun main(args: Array<String>) {

}

private fun String.center(i: Int): Any {
    return this.padStart((this.length + i) / 2).padEnd(i)
}
