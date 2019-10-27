import io.kotlintest.specs.StringSpec
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.url


class ServerTests : StringSpec() {

    init {
        "Server responds" {

            val client = HttpClient()
            val source = "SampleVideo_1280x720_1mb.mp4".asResource()!!

            val respond = client.post<ByteArray> {
                url("http://127.0.0.1:8080/convert/avi")
                body = source.inputStream().readBytes()
            }

            assert(respond.isNotEmpty())
        }
    }
}