package com.nitjamshedpur.tapcell.Highlights;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nitjamshedpur.tapcell.R;

public class FullScreenImageActivity extends Activity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        img = (ImageView) findViewById(R.id.image);

        // Recieve data
        Intent intent = getIntent();

        String image = intent.getExtras().getString("Thumbnail") ;

        Glide.with(this)
                .load(image)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .fitCenter())
                .into(img);

        img.setOnTouchListener(new ImageMatrixTouchHandler(getApplicationContext()));



    }
}
