package dev.aliakbar.tmdbunofficial.util

const val BASE_URL = "https://api.themoviedb.org/3/"
const val BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNzkwYjcwMzRkOTFhODU0YmE5MmUxOTlkMWQ2MTk3MiIsInN1YiI6IjYzMGYxMTg0MTI0MjVjMDA5ZDdkMjAzZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xPKQ-BTT_SZqBtJKyQ36VoDDpqCr_BAp-b_NjOOXvhc"
const val AUTHORIZATION_HEADER = "Authorization"
const val SETTING_KEY = "setting"
const val YOUTUBE_THUMBNAIL_BASE_URL = "https://i.ytimg.com/vi/"
const val OVERVIEW_PREVIEW_MAX_LINE = 3
const val PAGE_SIZE = 20


enum class YoutubeThumbnailSize(val size: String)
{
    STANDARD("/sddefault.jpg"),
    MEDIUM("/mqdefault.jpg"),
    HIGH("/hqdefault.jpg"),
    MAX("/maxresdefault.jpg")
}