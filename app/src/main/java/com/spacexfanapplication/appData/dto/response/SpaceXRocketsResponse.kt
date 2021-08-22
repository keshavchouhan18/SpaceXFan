package com.spacexfanapplication.appData.dto.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpaceXRocketsResponse(
	val spaceXRocketsResponse: List<SpaceXRocketsResponseItem>
) : Parcelable

@Parcelize
data class FailuresItem(
	val altitude: Int,
	val reason: String,
	val time: Int
) : Parcelable

@Parcelize
data class SpaceXRocketsResponseItem(
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
	val fairings: Fairings,
	val ships: List<String>,
	val date_utc: String,
	val static_fire_date_unix: Int,
	val success: Boolean,
	val tbd: Boolean,
	val name: String,
	val window: Int,
	val upcoming: Boolean
) : Parcelable

@Parcelize
data class Fairings(
	val recovered: Boolean,
	val ships: List<String>,
	val recovery_attempt: Boolean,
	val reused: Boolean
) : Parcelable
