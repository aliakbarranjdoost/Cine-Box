package dev.aliakbar.tmdbunofficial.data.source.sample

import dev.aliakbar.tmdbunofficial.data.Episode
import dev.aliakbar.tmdbunofficial.data.SeasonDetails

val episode = Episode(
    id = 63066,
    name = "The North Remembers",
    overview = "As Robb Stark and his northern army continue the war against the Lannisters, Tyrion arrives in King’s Landing to counsel Joffrey and temper the young king’s excesses.  On the island of Dragonstone, Stannis Baratheon plots an invasion to claim his late brother’s throne, allying himself with the fiery Melisandre, a strange priestess of a stranger god.  Across the sea, Daenerys, her three young dragons, and the khalasar trek through the Red Waste in search of allies, or water.  In the North, Bran presides over a threadbare Winterfell, while beyond the Wall, Jon Snow and the Night’s Watch must shelter with a devious wildling.",
    voteAverage = 8.038F,
    voteCount = 143,
//    airDate = "2012-04-01",
//    runtime = 53,
    seasonNumber = 2,
    episodeNumber = 1,
//    productionCode = "201",
    showId = 1399,
    stillUrl = "/gGHtlTvHpSGZ8DIrxMyK3Ewkc1Y.jpg",
    episodeType = "standard"
)

val episodes = mutableListOf<Episode>().apply { repeat(10) { this.add(episode) } }.toList()

val seasonDetails = SeasonDetails(
    id = 3625,
//    _id = "5256c89f19c2956ff6046d75",
    name = "Season 2",
    overview = "The cold winds of winter are rising in Westeros...war is coming...and five kings continue their savage quest for control of the all-powerful Iron Throne. With winter fast approaching, the coveted Iron Throne is occupied by the cruel Joffrey, counseled by his conniving mother Cersei and uncle Tyrion. But the Lannister hold on the Throne is under assault on many fronts. Meanwhile, a new leader is rising among the wildings outside the Great Wall, adding new perils for Jon Snow and the order of the Night's Watch.",
    episodes = episodes,
    airDate = "2012-04-01",
    seasonNumber = 2,
    voteAverage = 8.2F,
    posterUrl = "/9xfNkPwDOqyeUvfNhs1XlWA0esP.jpg"
)
