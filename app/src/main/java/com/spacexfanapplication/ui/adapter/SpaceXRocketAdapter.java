package com.spacexfanapplication.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spacexfanapplication.R;
import com.spacexfanapplication.appData.dto.response.FavoriteData;
import com.spacexfanapplication.appData.dto.response.SpaceXRocketsResponseItem;
import com.spacexfanapplication.appData.prefs.PrefConstantKt;
import com.spacexfanapplication.ui.activity.DetailActivity;
import com.spacexfanapplication.utils.AppUtils;
import com.spacexfanapplication.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.List;

public class SpaceXRocketAdapter extends RecyclerView.Adapter<SpaceXRocketAdapter.ViewHolder>{

    private Context mContext;
    private List<SpaceXRocketsResponseItem> rocketsResponses = new ArrayList<>();
    SpaceXRocketsResponseItem showList;
    FirebaseAuth auth;
    private DatabaseReference mDatabase;

    public SpaceXRocketAdapter(Context mContext, List<SpaceXRocketsResponseItem> rocketsResponses )
    {
        this.mContext = mContext;
        this.rocketsResponses = rocketsResponses;
        //Initialize
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rocket_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        showList = rocketsResponses.get(position);

        if (!showList.getName().equals("")){
            holder.tvId.setText("Id - "+showList.getId());
            holder.tvName.setText(showList.getName());
            holder.tvFlightNumber.setText("FLight no. : "+showList.getFlight_number());

            Glide.with(mContext).load(showList.getLinks().getPatch().getSmall()).into(holder.image);

            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("detail_list",showList);
                    mContext.startActivity(intent);
                }
            });

            //setting fav
            if (showList.getWindow()!=0){
                holder.ivFav.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_on));
            } else {
                holder.ivFav.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_off));
            }

            holder.ivFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AppUtils.Companion.isNetworkConnected()) {
                        //for removing send interest
                        if (showList.getWindow()!=0){
                            if (auth.getCurrentUser() != null) {
                                updateFavorite(showList.getId(), true, showList.getName(), showList.getFlight_number(), showList.getSuccess(), showList.getLinks().getPatch().getSmall(), holder);
                            } else {
                                auth.signInWithEmailAndPassword("vesteltest@mailinator.com","1234.Abc").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            updateFavorite(showList.getId(), true, showList.getName(), showList.getFlight_number(), showList.getSuccess(), showList.getLinks().getPatch().getSmall(), holder);
                                        }
                                    }
                                });
                            }
                        } else {
                            if (auth.getCurrentUser() != null) {
                                updateFavorite(showList.getId(), true, showList.getName(), showList.getFlight_number(), showList.getSuccess(), showList.getLinks().getPatch().getSmall(), holder);
                            } else {
                                auth.signInWithEmailAndPassword("vesteltest@mailinator.com","1234.Abc").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            updateFavorite(showList.getId(), true, showList.getName(), showList.getFlight_number(), showList.getSuccess(), showList.getLinks().getPatch().getSmall(), holder);
                                        }
                                    }
                                });
                            }
                        }


                    }else {
                        Toast.makeText(mContext,mContext.getResources().getString(R.string.no_internet_connection),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void updateFavorite(String id, boolean favorite, String name, int flight_number, boolean success, String image, ViewHolder viewHolder) {
        ProjectUtils.showProgressDialog(mContext, mContext.getResources().getString(R.string.please_wait), false);
            FavoriteData favoriteData = new FavoriteData();
            favoriteData.setFav_id(id);
            favoriteData.setSpacex_rocket_id(id);
            favoriteData.setName(name);
            favoriteData.setImage(image);
            favoriteData.setFlight_number(flight_number);
            favoriteData.setFavorite(favorite);
            favoriteData.setSuccess(success);
            //inserting data in realtime database
            mDatabase.child(PrefConstantKt.FB_DATABASE_USER).setValue(favoriteData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ProjectUtils.pauseProgressDialog();
                            if (favorite) {
                                viewHolder.ivFav.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_on));
                                ProjectUtils.showToast(mContext,"Added to Favorite!");
                            } else {
                                viewHolder.ivFav.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_off));
                                ProjectUtils.showToast(mContext,"Removed from Favorite!");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ProjectUtils.pauseProgressDialog();
                    ProjectUtils.showToast(mContext,mContext.getResources().getString(R.string.something_went_wrong));
                }
            });

    }

    @Override
    public int getItemCount()
    {
        return rocketsResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout ll_main;
        public CardView card_view;
        ImageView image,ivFav;
        public TextView tvId,tvName,tvFlightNumber,tvSuccess;

        public ViewHolder(View itemView)
        {
            super(itemView);

            ll_main = itemView.findViewById(R.id.ll_main);
            card_view = itemView.findViewById(R.id.card_view);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvFlightNumber = itemView.findViewById(R.id.tvFlightNumber);
            tvSuccess = itemView.findViewById(R.id.tvSuccess);
            image = itemView.findViewById(R.id.image);
            ivFav = itemView.findViewById(R.id.ivFav);
        }
    }
}
