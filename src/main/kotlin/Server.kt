import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.receiveMultipart
import io.ktor.response.respondFile
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
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

                    val multipart = call.receiveMultipart()

                    multipart.forEachPart { part ->
                        if(part is PartData.FileItem) {
                            part.streamProvider().use { partData ->
                                inputFile.outputStream().buffered().use { fileData ->
                                    partData.copyTo(fileData)
                                }
                            }
                        }
                        part.dispose()
                    }

                    videoConverter.convert(inputFile, outputFile)

                    call.respondFile(outputFile)

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
