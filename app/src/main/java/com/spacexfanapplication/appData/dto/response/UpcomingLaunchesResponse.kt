package com.spacexfanapplication.appData.dto.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpcomingLaunchesResponse(
    val upcomingLaunchesResponse: List<UpcomingLaunchesResponseItem>
) : Parcelable

@Parcelize
data class CoresItem(
    val core: String,
    val flight: Int,
    val landing_type: String,
    val gridfins: Boolean,
    val landing_attempt: Boolean,
    val legs: Boolean,
    val landpad: String,
    val reused: Boolean,
    val landing_success: Boolean
) : Parcelable

@Parcelize
data class Reddit(
    val campaign: String,
    val launch: String,
    val media: String,
    val recovery: String
) : Parcelable

@Parcelize
data class Flickr(
    val small: List<String>,
    val original: List<String>
) : Parcelable

@Parcelize
data class UpcomingLaunchesResponseItem(
    val fairings: Fairings,
    val launchpad: String,
    val payloads: List<String>,
    val rocket: String,
    val crew: List<String>,
    val date_unix: Int,
    val cores: List<CoresItem>,
    val auto_update: Boolean,
    val date_precision: String,
    val links: Links,
    val details: String,
    val id: String,
    val net: Boolean,
    val capsules: List<String>,
    val static_fire_date_utc: String,
    val failures: List<FailuresItem>,
    val date_local: String,
    val flight_number: Int,
    val launch_library_id: String,
    val ships: List<String>,
    val date_utc: String,
    val static_fire_date_unix: String,
    val success: Boolean,
    val tbd: Boolean,
    val name: String,
    val window: Int,
    val upcoming: Boolean
) : Parcelable

@Parcelize
data class Patch(
    val small: String,
    val large: String
) : Parcelable


@Parcelize
data class Links(
    val patch: Patch,
    val webcast: String,
    val flickr: Flickr,
    val reddit: Reddit,
    val wikipedia: String,
    val youtube_id: String,
    val presskit: String,
    val article: String
) : Parcelable
