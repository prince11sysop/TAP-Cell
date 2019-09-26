package com.nitjamshedpur.tapcell.Carousel;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nitjamshedpur.tapcell.R;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<String> sliderImages;
    private List<String> sliderText;

    public SliderAdapter(Context context, List<String> sliderImages, List<String> sliderText) {
        this.context = context;
        this.sliderImages = sliderImages;
        this.sliderText = sliderText;
    }

    @Override
    public int getCount() {
        return sliderImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, null);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        ImageView imageView=(ImageView) view.findViewById(R.id.imgView);


        textView.setText(sliderText.get(position));
        //imageView.setImageDrawable(context.getResources().getDrawable(sliderImages.get(position)));

        //loading image to Cardview from URI using glide
        Glide.with(context)
                .load(sliderImages.get(position))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .centerInside())
                .into(imageView);


        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}