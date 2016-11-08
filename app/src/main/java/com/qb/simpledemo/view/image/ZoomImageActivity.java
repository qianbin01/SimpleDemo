package com.qb.simpledemo.view.image;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qb.simpledemo.R;
import com.qb.simpledemo.util.ui.ZoomImageView;
import com.squareup.picasso.Picasso;

public class ZoomImageActivity extends AppCompatActivity {
    private ZoomImageView imageView;
    private Bundle mBundle;
    private String pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_image);
        imageView = (ZoomImageView) findViewById(R.id.ivImage);
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            pic = mBundle.getString("pic");
            if (pic != null) {
                Picasso.with(this).load(pic).into(imageView);
            }
        }
    }
}
