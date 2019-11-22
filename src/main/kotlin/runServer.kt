import kotlin.concurrent.thread

fun main() {
    val server = Server(host = "127.0.0.1", port = 8080)
    thread {
        server.start();
    }
}