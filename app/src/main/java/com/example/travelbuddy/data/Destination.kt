package com.example.travelbuddy.data

/**
 * A single travel destination.
 *
 * It no longer needs to be Parcelable: navigation only passes the [id],
 * and the detail screen looks the full object up again in [DestinationRepository].
 */
data class Destination(
    val id: Int,
    val name: String,
    val country: String,
    val emoji: String,
    val tagline: String,
    val description: String,
    val bestSeason: String,
    val budget: String,
    val rating: Float,
    val wikiTitle: String
) {

    /** Link used by the "Read more" implicit ACTION_VIEW intent. */
    val webUrl: String
        get() = "https://en.wikipedia.org/wiki/$wikiTitle"
}
