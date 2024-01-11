package dev.aliakbar.tmdbunofficial.data.source.sample

import dev.aliakbar.tmdbunofficial.data.source.network.NetworkMultiSearchResult
import kotlinx.serialization.json.Json

val json = """
            {
              "adult": false,
              "backdrop_path": "/i7XqYOBk19UrTt3RoHhaJZmnb9R.jpg",
              "id": 223047,
              "name": "House 12",
              "original_language": "ar",
              "original_name": "منزل 12",
              "overview": "After 35 years of marriage, Mounira’s husband, Mohammad, loses his fortune. As they move with their kids into a more modest house, they face a life they never knew before.",
              "poster_path": "/rZ4Sj5hr57tHkUSJsDqzBqdWXTZ.jpg",
              "media_type": "tv",
              "genre_ids": [
                18
              ],
              "popularity": 0.969,
              "first_air_date": "2023-03-23",
              "vote_average": 0,
              "vote_count": 0,
              "origin_country": [
                "KW"
              ]
            }
        """

val multisearch = Json.decodeFromString<NetworkMultiSearchResult>(json)