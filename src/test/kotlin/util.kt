import java.io.File

fun String.asResource(): File? {
    val res = object {}::class.java.classLoader.getResource(this)

    return if (res != null)
        File(res.toURI())
    else
        null

}