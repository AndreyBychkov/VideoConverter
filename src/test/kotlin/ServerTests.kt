import io.kotlintest.specs.StringSpec
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.http.headersOf
import kotlinx.io.streams.asInput


class ServerTests : StringSpec() {

    init {
        "Server responds" {

            val client = HttpClient()
            val source = "SampleVideo_1280x720_1mb.mp4".asResource()!!

            val respond = client.submitFormWithBinaryData<ByteArray>(
                port = 8080,
                path = "convert/avi",
                formData = listOf(
                    PartData.FormItem(
                        "sample", {}, headersOf(
                            HttpHeaders.ContentDisposition,
                            ContentDisposition.Inline
                                .withParameter(ContentDisposition.Parameters.Name, "foo")
                                .toString()
                        )
                    ),
                    PartData.FileItem({ source.inputStream().asInput() }, {}, headersOf(
                        HttpHeaders.ContentDisposition,
                        ContentDisposition.File
                            .withParameter(ContentDisposition.Parameters.Name, "video")
                            .withParameter(ContentDisposition.Parameters.FileName, "upload")
                            .toString()
                        )
                    )
                )
            )

            assert(respond.isNotEmpty())
        }
    }
}