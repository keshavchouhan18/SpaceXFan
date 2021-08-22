package com.spacexfanapplication.repo;

import androidx.lifecycle.LiveData;

import com.spacexfanapplication.appData.ApiInteractor;
import com.spacexfanapplication.network.NetworkManager;

import org.jetbrains.annotations.NotNull;

public class HomeRepo {

    public final LiveData getSpaceXrockets() {
        return (new ApiInteractor() {
            @NotNull
            protected LiveData createCall() {
                return NetworkManager.INSTANCE.getWebService().getSpaceXRockets();
            }
        }).getAsLiveData();
    }

    public final LiveData getUpcomingLaunches() {
        return (new ApiInteractor() {
            @NotNull
            protected LiveData createCall() {
                return NetworkManager.INSTANCE.getWebService().getUpcomingLaunches();
            }
        }).getAsLiveData();
    }
}
