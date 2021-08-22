package com.spacexfanapplication.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.spacexfanapplication.appData.Response;
import com.spacexfanapplication.appData.dto.response.SpaceXRocketsResponse;
import com.spacexfanapplication.base.BaseViewModel;
import com.spacexfanapplication.repo.HomeRepo;

public class MainViewModel extends BaseViewModel {

    HomeRepo homeRepo = new HomeRepo();

    public MediatorLiveData<Response<SpaceXRocketsResponse>> getSpaceXrocketsData() {
        //creating livedata object which will be returned and observed by activity/fragment when server response come back
        MediatorLiveData<Response<SpaceXRocketsResponse>> spaceXRocketsLiveData = new MediatorLiveData<>();

        if (spaceXRocketsLiveData.getValue()==null || spaceXRocketsLiveData.getValue().getStatus() != Response.Status.LOADING) {
            spaceXRocketsLiveData.addSource(homeRepo.getSpaceXrockets(), new Observer<Response<SpaceXRocketsResponse>>() {
                @Override
                public void onChanged(Response<SpaceXRocketsResponse> baseResponseResponse) {
                    spaceXRocketsLiveData.setValue(baseResponseResponse);
                }
            });
        }
        return spaceXRocketsLiveData;
    }

    public MediatorLiveData<Response<SpaceXRocketsResponse>> getUpcomingLaunchesData() {
        //creating livedata object which will be returned and observed by activity/fragment when server response come back
        MediatorLiveData<Response<SpaceXRocketsResponse>> spaceXRocketsLiveData = new MediatorLiveData<>();

        if (spaceXRocketsLiveData.getValue()==null || spaceXRocketsLiveData.getValue().getStatus() != Response.Status.LOADING) {
            spaceXRocketsLiveData.addSource(homeRepo.getUpcomingLaunches(), new Observer<Response<SpaceXRocketsResponse>>() {
                @Override
                public void onChanged(Response<SpaceXRocketsResponse> baseResponseResponse) {
                    spaceXRocketsLiveData.setValue(baseResponseResponse);
                }
            });
        }
        return spaceXRocketsLiveData;
    }
}
