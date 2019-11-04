import ws.schild.jave.*
import java.io.File

internal class VideoConverter {
    fun convert(source: File, target: File) {
        val audio = AudioAttributes().apply {
            setCodec(AudioAttributes.DIRECT_STREAM_COPY)
        }

        val video = VideoAttributes().apply {
            setCodec("mpeg4")
        }

        val encodingAttributes = EncodingAttributes().apply {
            format = target.extension
            audioAttributes = audio
            videoAttributes = video
        }

        val encoder = Encoder()
        encoder.encode(MultimediaObject(source), target, encodingAttributes)
    }
}