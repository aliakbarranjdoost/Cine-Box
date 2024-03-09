package dev.aliakbar.tmdbunofficial.util

const val BASE_URL = "https://api.themoviedb.org/3/"
const val BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNzkwYjcwMzRkOTFhODU0YmE5MmUxOTlkMWQ2MTk3MiIsInN1YiI6IjYzMGYxMTg0MTI0MjVjMDA5ZDdkMjAzZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xPKQ-BTT_SZqBtJKyQ36VoDDpqCr_BAp-b_NjOOXvhc"
const val AUTHORIZATION_HEADER = "Authorization"
const val SETTING_KEY = "setting"
const val YOUTUBE_THUMBNAIL_BASE_URL = "https://i.ytimg.com/vi/"
const val OVERVIEW_PREVIEW_MAX_LINE = 3
const val PAGE_SIZE = 20
const val MAX_TRAILERS_NUMBER = 10
const val THEME_OPTION_KEY = "theme_option"
const val DYNAMIC_THEME_KEY = "is_dynamic"
// Higher number means lower quality
const val IMAGE_QUALITY_LEVEL = 2

// Room table names
const val LOCAL_BOOKMARK_TABLE_NAME = "bookmarks"
const val LOCAL_CONFIGURATION_TABLE_NAME = "configurations"

// Retrofit serial names
const val NETWORK_PROFILE_PATH_SERIAL_NAME = "profile_path"
const val NETWORK_POSTER_PATH_SERIAL_NAME = "poster_path"
const val NETWORK_BACKDROP_PATH_SERIAL_NAME = "backdrop_path"
const val NETWORK_LOGO_PATH_SERIAL_NAME = "logo_path"
const val NETWORK_STILL_PATH_SERIAL_NAME = "still_path"
const val NETWORK_FILE_PATH_SERIAL_NAME = "file_path"
const val NETWORK_ORIGIN_COUNTRY_SERIAL_NAME = "origin_country"
const val NETWORK_IMAGES_SERIAL_NAME = "images"
const val NETWORK_CHANGE_KEYS_SERIAL_NAME = "change_keys"
const val NETWORK_ISO_31661_SERIAL_NAME = "iso_3166_1"
const val NETWORK_ISO_6391_SERIAL_NAME = "iso_639_1"
const val NETWORK_CREDIT_ID_SERIAL_NAME = "credit_id"
const val NETWORK_IMDB_ID_SERIAL_NAME = "imdb_id"
const val NETWORK_KNOWN_FOR_DEPARTMENT_SERIAL_NAME = "known_for_department"
const val NETWORK_ALSO_KNOWN_AS_SERIAL_NAME = "also_known_as"
const val NETWORK_PLACE_OF_BIRTH_SERIAL_NAME = "place_of_birth"
const val NETWORK_COMBINED_CREDITS_SERIAL_NAME = "combined_credits"
const val NETWORK_VOTE_AVERAGE_SERIAL_NAME = "vote_average"
const val NETWORK_VOTE_COUNT_SERIAL_NAME = "vote_count"
const val NETWORK_EPISODE_NUMBER_SERIAL_NAME = "episode_number"
const val NETWORK_EPISODE_TYPE_SERIAL_NAME = "episode_type"
const val NETWORK_EPISODE_COUNT_SERIAL_NAME = "episode_count"
const val NETWORK_SEASON_NUMBER_SERIAL_NAME = "season_number"
const val NETWORK_SHOW_ID_SERIAL_NAME = "show_id"
const val NETWORK_AIR_DATE_SERIAL_NAME = "air_date"
const val NETWORK_BASE_URL_SERIAL_NAME = "base_url"
const val NETWORK_SECURE_BASE_URL_SERIAL_NAME = "secure_base_url"
const val NETWORK_BACKDROP_SIZES_SERIAL_NAME = "backdrop_sizes"
const val NETWORK_LOGO_SIZES_SERIAL_NAME = "logo_sizes"
const val NETWORK_POSTER_SIZES_SERIAL_NAME = "poster_sizes"
const val NETWORK_PROFILE_SIZES_SERIAL_NAME = "profile_sizes"
const val NETWORK_STILL_SIZES_SERIAL_NAME = "still_sizes"
const val NETWORK_ENGLISH_NAME_SERIAL_NAME = "english_name"
const val NETWORK_ORIGINAL_TITLE_SERIAL_NAME = "original_title"
const val NETWORK_ORIGINAL_NAME_SERIAL_NAME = "original_name"
const val NETWORK_ORIGINAL_LANGUAGE_SERIAL_NAME = "original_language"
const val NETWORK_RELEASE_DATE_SERIAL_NAME = "release_date"
const val NETWORK_BELONGS_TO_COLLECTION_SERIAL_NAME = "belongs_to_collection"
const val NETWORK_PRODUCTION_COUNTRIES_SERIAL_NAME = "production_countries"
const val NETWORK_PRODUCTION_COMPANIES_SERIAL_NAME = "production_companies"
const val NETWORK_SPOKEN_LANGUAGES_SERIAL_NAME = "spoken_languages"
const val NETWORK_MEDIA_TYPE_SERIAL_NAME = "media_type"
const val NETWORK_GENRE_IDS_SERIAL_NAME = "genre_ids"
const val NETWORK_FIRST_AIR_DATE_SERIAL_NAME = "first_air_date"
const val NETWORK_LAST_AIR_DATE_SERIAL_NAME = "last_air_date"
const val NETWORK_VIDEO_SERIAL_NAME = "video"
const val NETWORK_TOTAL_PAGES_SERIAL_NAME = "total_pages"
const val NETWORK_TOTAL_RESULTS_SERIAL_NAME = "total_results"
const val NETWORK_GUEST_STARS_SERIAL_NAME = "guest_stars"
const val NETWORK_CREATED_BY_STARS_SERIAL_NAME = "created_by"
const val NETWORK_NUMBER_OF_SEASONS_SERIAL_NAME = "number_of_seasons"
const val NETWORK_NUMBER_OF_EPISODES_SERIAL_NAME = "number_of_episodes"
const val NETWORK_IN_PRODUCTION_SERIAL_NAME = "in_production"

// Retrofit query
const val LANGUAGE = "language"
const val ENGLISH = "en"
const val INCLUDE_ADULTS = "include_adults"
const val INCLUDE_VIDEO = "include_video"
const val SORT_BY = "sort_by"
const val VOTE_COUNT = "vote_count"
const val MIN_VOTE_COUNT = 1000

enum class YoutubeThumbnailSize(val size: String)
{
    STANDARD("/sddefault.jpg"),
    MEDIUM("/mqdefault.jpg"),
    HIGH("/hqdefault.jpg"),
    MAX("/maxresdefault.jpg")
}