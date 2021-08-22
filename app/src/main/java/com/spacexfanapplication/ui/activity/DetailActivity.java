package com.spacexfanapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;

import com.spacexfanapplication.R;
import com.spacexfanapplication.appData.dto.response.SpaceXRocketsResponseItem;
import com.spacexfanapplication.databinding.ActivityDetailBinding;
import com.spacexfanapplication.ui.adapter.ImageAdapter;
import com.spacexfanapplication.utils.ProjectUtils;
import com.spacexfanapplication.viewmodel.MainViewModel;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding detailBinding;

    SpaceXRocketsResponseItem rocketsResponseItem;

    ImageAdapter imageAdapter;

    MainViewModel mainViewModel;

    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        detailBinding = DataBindingUtil.setContentView(DetailActivity.this, R.layout.activity_detail);
        detailBinding.setLifecycleOwner(this);

        rocketsResponseItem = getIntent().getParcelableExtra("detail_list");
        ProjectUtils.showLog(TAG,"id : "+rocketsResponseItem.getId());

        //setting Toolbar
        detailBinding.toolbar.setTitle("");
        setSupportActionBar(detailBinding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setData(rocketsResponseItem);
    }

    private void setData(SpaceXRocketsResponseItem rocketsResponseItem) {
        detailBinding.tvId.setText(rocketsResponseItem.getId());
        detailBinding.tvName.setText(rocketsResponseItem.getName());
        detailBinding.tvFlightNumber.setText(rocketsResponseItem.getFlight_number());
        detailBinding.tvUpcoming.setText("Upcoming : "+rocketsResponseItem.getUpcoming());
        detailBinding.tvDetail.setText(rocketsResponseItem.getDetails());

        ArrayList<String> imageList = new ArrayList<>();
        imageList.add(rocketsResponseItem.getLinks().getPatch().getSmall());
        imageList.add(rocketsResponseItem.getLinks().getPatch().getLarge());

        imageAdapter=new ImageAdapter(DetailActivity.this,imageList,R.layout.slide_image);
        detailBinding.viewpager.setAdapter(imageAdapter);
        detailBinding.dotsIndicator.setViewPager(detailBinding.viewpager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}