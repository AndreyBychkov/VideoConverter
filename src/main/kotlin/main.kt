fun main() {
    val server = Server("127.0.0.1", 8080).apply {
        start()
    }
}