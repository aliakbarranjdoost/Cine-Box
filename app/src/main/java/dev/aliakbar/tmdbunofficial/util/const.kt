package dev.aliakbar.tmdbunofficial.util

const val YOUTUBE_THUMBNAIL_BASE_URL = "https://i.ytimg.com/vi/"
const val OVERVIEW_PREVIEW_MAX_LINE = 3

enum class YoutubeThumbnailSize(val size: String)
{
    STANDARD("/sddefault.jpg"),
    MEDIUM("/mqdefault.jpg"),
    HIGH("/hqdefault.jpg"),
    MAX("/maxresdefault.jpg")
}