package com.spacexfanapplication.network;

import androidx.lifecycle.LiveData;

import com.spacexfanapplication.appData.dto.response.SpaceXRocketsResponse;
import com.spacexfanapplication.appData.dto.response.UpcomingLaunchesResponse;

import retrofit2.http.GET;

public interface WebService {

    @GET("launches")
    LiveData<ApiResponse<SpaceXRocketsResponse>> getSpaceXRockets();

    @GET("launches/upcoming")
    LiveData<ApiResponse<UpcomingLaunchesResponse>> getUpcomingLaunches();
}
