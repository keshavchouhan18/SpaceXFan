package com.spacexfanapplication.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.spacexfanapplication.R;
import com.spacexfanapplication.appData.Response;
import com.spacexfanapplication.appData.dto.response.SpaceXRocketsResponse;
import com.spacexfanapplication.appData.dto.response.SpaceXRocketsResponseItem;
import com.spacexfanapplication.base.HBaseFragment;
import com.spacexfanapplication.databinding.FragmentUpcomingBinding;
import com.spacexfanapplication.ui.adapter.SpaceXRocketAdapter;
import com.spacexfanapplication.utils.AppUtils;
import com.spacexfanapplication.viewmodel.MainViewModel;

import java.util.List;

public class UpcomingFragment extends HBaseFragment<MainViewModel, FragmentUpcomingBinding> {

    SpaceXRocketAdapter spaceXRocketAdapter;
    RecyclerView.LayoutManager layoutManager;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentUpcomingBinding upcomingBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_upcoming, container, false);
        setup(MainViewModel.class, upcomingBinding);
        return upcomingBinding.getRoot();
    }

    @Override
    public void init() {
        if (AppUtils.Companion.isNetworkConnected()){
            binding.cpvRockets.setVisibility(View.VISIBLE);
            addObserver(viewModel.getUpcomingLaunchesData(), upcomingRocketsDataObserver);

        }else {
            Toast.makeText(getActivity(),getResources().getString(R.string.no_internet_connection),Toast.LENGTH_LONG).show();
        }
    }

    final Observer upcomingRocketsDataObserver = new Observer<Response<SpaceXRocketsResponse>>() {
        @Override
        public void onChanged(@Nullable final Response<SpaceXRocketsResponse> response) {
            binding.cpvRockets.setVisibility(View.GONE);
            if (response.isSuccess()){
                addData(response.getData());
            }
        }
    };

    private void addData(SpaceXRocketsResponse response) {
        List<SpaceXRocketsResponseItem> list = response.getSpaceXRocketsResponse();

        spaceXRocketAdapter = new SpaceXRocketAdapter(getActivity(),list);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        binding.rvUpcoming.setLayoutManager(layoutManager);
        binding.rvUpcoming.setAdapter(spaceXRocketAdapter);
    }

    @Override
    public void setUpListeners() {

    }
}