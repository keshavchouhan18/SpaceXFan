package com.spacexfanapplication.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricConstants;
import androidx.biometric.BiometricPrompt;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spacexfanapplication.R;
import com.spacexfanapplication.appData.Response;
import com.spacexfanapplication.appData.dto.response.FavoriteData;
import com.spacexfanapplication.appData.dto.response.SpaceXRocketsResponse;
import com.spacexfanapplication.appData.dto.response.SpaceXRocketsResponseItem;
import com.spacexfanapplication.appData.prefs.PrefConstantKt;
import com.spacexfanapplication.base.HBaseFragment;
import com.spacexfanapplication.databinding.FragmentFavoriteBinding;
import com.spacexfanapplication.databinding.FragmentRocketsBinding;
import com.spacexfanapplication.ui.adapter.SpaceXRocketAdapter;
import com.spacexfanapplication.utils.AppUtils;
import com.spacexfanapplication.utils.ProjectUtils;
import com.spacexfanapplication.viewmodel.MainViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoriteFragment extends HBaseFragment<MainViewModel, FragmentFavoriteBinding> {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<SpaceXRocketsResponseItem> list;
    List<SpaceXRocketsResponseItem> showList;

    SpaceXRocketAdapter spaceXRocketAdapter;
    RecyclerView.LayoutManager layoutManager;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFavoriteBinding favoriteBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite, container, false);
        setup(MainViewModel.class, favoriteBinding);
        return favoriteBinding.getRoot();
    }


    @Override
    public void init() {
        //Initialize
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(PrefConstantKt.FB_DATABASE_USER);
        //Create a thread pool with a single thread//
        Executor newExecutor = Executors.newSingleThreadExecutor();
        //Start listening for authentication events//
        final BiometricPrompt myBiometricPrompt = new BiometricPrompt(getActivity(), newExecutor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            //onAuthenticationError is called when a fatal error occurrs//
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                } else {
                    //Print a message to Logcat//
                    ProjectUtils.showToast(getActivity(),errString.toString());
                    ProjectUtils.showLog(TAG, "An unrecoverable error occurred");
                }
            }
            //onAuthenticationSucceeded is called when a fingerprint is matched successfully//
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //Print a message to Logcat//
                ProjectUtils.showLog(TAG, "Fingerprint recognised successfully");

                getFavoriteData();
                //get List to match Id
                addObserver(viewModel.getSpaceXrocketsData(), spaceXRocketsDataObserver);
            }
            //onAuthenticationFailed is called when the fingerprint doesn’t match//
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //Print a message to Logcat//
                ProjectUtils.showLog(TAG, "Fingerprint not recognised");
            }
        });

        //Create the BiometricPrompt instance//
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                //Add some text to the dialog//
                .setTitle("Unlock to Check Favorites")
                .setDescription("Touch the fingerprint sensor")
                .setNegativeButtonText("Cancel")
                //Build the dialog//
                .build();
        myBiometricPrompt.authenticate(promptInfo);
    }

    final Observer spaceXRocketsDataObserver = new Observer<Response<SpaceXRocketsResponse>>() {
        @Override
        public void onChanged(@Nullable final Response<SpaceXRocketsResponse> response) {
            if (response.isSuccess()){

                list = response.getData().getSpaceXRocketsResponse();
            }
        }
    };

    private void getFavoriteData()
    {
        if (AppUtils.Companion.isNetworkConnected()){
            binding.cpvRockets.setVisibility(View.VISIBLE);
            getFavDataFromFirebase();
        } else {
            Toast.makeText(getActivity(),getResources().getString(R.string.no_internet_connection),Toast.LENGTH_LONG).show();
        }
    }

    private void getFavDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.cpvRockets.setVisibility(View.GONE);
                for(DataSnapshot userpostSnapShot:snapshot.getChildren()) {
                    FavoriteData favoriteData = userpostSnapShot.getValue(FavoriteData.class);
                    for (int i=0; i<list.size(); i++){
                        if (favoriteData.getSpacex_rocket_id().equals(list.get(i).getId()))
                        {
                            showList.add(list.get(i));
                        }
                    }
                }

                if (!showList.isEmpty()){
                    ProjectUtils.showLog(TAG,"fav resposne : "+showList.toString());
                    spaceXRocketAdapter = new SpaceXRocketAdapter(getActivity(),list);
                    layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
                    binding.rvFavorite.setLayoutManager(layoutManager);
                    binding.rvFavorite.setAdapter(spaceXRocketAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void setUpListeners() {

    }
}