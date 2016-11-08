package com.qb.simpledemo.view.music;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.qb.simpledemo.R;
import com.qb.simpledemo.presenter.music.IMusicDetailPresenter;
import com.qb.simpledemo.presenter.music.IMusicDetailPresenterImpl;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IMusicDetailActivity extends AppCompatActivity implements IMusicDetaView {

    @Bind(R.id.progress)
    ProgressBar mProgressBar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.ivImage)
    ImageView imageView;
    @Bind(R.id.WebView)
    WebView webView;
    private String title, image, content;
    private IMusicDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_detail);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        image = getIntent().getStringExtra("image");
        content = getIntent().getStringExtra("content");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter = new IMusicDetailPresenterImpl(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mCollapsingToolbarLayout.setTitle(title);
        Picasso.with(this).load(image).into(imageView);
        mPresenter.loadStart();
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mPresenter.loadOver();
                }
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    @Override
    public void showMusicDetail() {
        webView.loadUrl(content);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
