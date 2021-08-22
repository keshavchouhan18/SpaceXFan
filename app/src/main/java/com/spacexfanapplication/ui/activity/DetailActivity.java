package com.spacexfanapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.spacexfanapplication.R;
import com.spacexfanapplication.appData.dto.response.SpaceXRocketsResponseItem;
import com.spacexfanapplication.databinding.ActivityDetailBinding;
import com.spacexfanapplication.utils.ProjectUtils;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding detailBinding;

    SpaceXRocketsResponseItem rocketsResponseItem;

    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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