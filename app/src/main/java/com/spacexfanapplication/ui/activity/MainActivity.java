package com.spacexfanapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.spacexfanapplication.R;
import com.spacexfanapplication.databinding.ActivityMainBinding;
import com.spacexfanapplication.ui.fragment.FavoriteFragment;
import com.spacexfanapplication.ui.fragment.RocketsFragment;
import com.spacexfanapplication.ui.fragment.UpcomingFragment;
import com.spacexfanapplication.ui.adapter.ViewPagerAdapter;
import com.spacexfanapplication.viewmodel.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    ActivityMainBinding mainBinding;

    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mainBinding.setLifecycleOwner(this);
        //Adding Fragment into arraylist
        ArrayList<Fragment> fragList = new ArrayList<>();
        fragList.add(new RocketsFragment());
        fragList.add(new FavoriteFragment());
        fragList.add(new UpcomingFragment());
        //here adding frgment list into Adapter and setting adapter to view pager
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(fragList, getSupportFragmentManager());
        mainBinding.viewPagerFrag.setAdapter(pagerAdapter);

        //CLick events
        mainBinding.viewPagerFrag.addOnPageChangeListener(this);
        mainBinding.llSpaceXRockets.setOnClickListener(this);
        mainBinding.llFavorite.setOnClickListener(this);
        mainBinding.llUpcoming.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_space_x_rockets:
                mainBinding.viewPagerFrag.setCurrentItem(0);
                mainBinding.tvRockets.setTextColor(getResources().getColor(R.color.colorBlue));
                mainBinding.tvFavorite.setTextColor(getResources().getColor(R.color.colorGrey));
                mainBinding.tvUpcoming.setTextColor(getResources().getColor(R.color.colorGrey));
                break;
            case R.id.ll_favorite:
                mainBinding.viewPagerFrag.setCurrentItem(1);
                mainBinding.tvRockets.setTextColor(getResources().getColor(R.color.colorGrey));
                mainBinding.tvFavorite.setTextColor(getResources().getColor(R.color.colorBlue));
                mainBinding.tvUpcoming.setTextColor(getResources().getColor(R.color.colorGrey));
                break;

            case R.id.ll_upcoming:
                mainBinding.viewPagerFrag.setCurrentItem(2);
                mainBinding.tvRockets.setTextColor(getResources().getColor(R.color.colorGrey));
                mainBinding.tvFavorite.setTextColor(getResources().getColor(R.color.colorGrey));
                mainBinding.tvUpcoming.setTextColor(getResources().getColor(R.color.colorBlue));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mainBinding.tvRockets.setTextColor(getResources().getColor(R.color.colorBlue));
                mainBinding.tvFavorite.setTextColor(getResources().getColor(R.color.colorGrey));
                mainBinding.tvUpcoming.setTextColor(getResources().getColor(R.color.colorGrey));
                break;
            case 1:
                mainBinding.tvRockets.setTextColor(getResources().getColor(R.color.colorGrey));
                mainBinding.tvFavorite.setTextColor(getResources().getColor(R.color.colorBlue));
                mainBinding.tvUpcoming.setTextColor(getResources().getColor(R.color.colorGrey));
                break;

            case 2:
                mainBinding.tvRockets.setTextColor(getResources().getColor(R.color.colorGrey));
                mainBinding.tvFavorite.setTextColor(getResources().getColor(R.color.colorGrey));
                mainBinding.tvUpcoming.setTextColor(getResources().getColor(R.color.colorBlue));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}