package com.spacexfanapplication.ui.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.spacexfanapplication.R;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {

    private Context mContext;
    ArrayList<String> image_lists= new ArrayList<>();
    int resource;

    public ImageAdapter(Context mContext, ArrayList<String> image_lists, int resource) {
        this.mContext = mContext;
        this.image_lists = image_lists;
        this.resource = resource;
    }

    @Override
    public int getCount()
    {
        return (null != image_lists ? image_lists.size() : 0);
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view.equals(object);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2)
    {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public Parcelable saveState()
    {
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.slide_image,null);
        container.addView(view);

        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(mContext).load(image_lists.get(position)).into(imageView);

        return view;
    }
}
