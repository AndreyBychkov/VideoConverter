internal enum class VideoFormats {
    AVI { override val extension = "avi" },
    MP4 { override val extension = "mp4"},
    MOV { override val extension = "mov"};

    abstract val extension: String
}