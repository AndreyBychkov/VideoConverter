import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.receiveStream
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.concurrent.TimeUnit


class Server(host: String, port: Int) {
    private val server = embeddedServer(Netty, host = host, port = port) {
        routing {
            get("/") {
                call.respondText("Welcome to server!", ContentType.Text.Html)
            }

            VideoFormats.values().forEach {
                val format = it.extension

                post("/convert/$format") {
                    val videoConverter = VideoConverter()

                    val inputFile = createTempFile("temp-input-file", ".tmp")
                    val outputFile = createTempFile("temp-output-file", ".$format")

                    val stream = call.receiveStream()
                    inputFile.writeBytes(stream.readBytes())

                    videoConverter.convert(inputFile, outputFile)

                    call.respondBytes(outputFile.readBytes())

                    inputFile.delete()
                    outputFile.delete()
                }
            }
        }
    }

    fun start() {
        server.start(true)
    }

    fun stop() {
        server.stop(timeUnit = TimeUnit.MILLISECONDS, gracePeriod = 100, timeout = 1000)
    }
}
