package com.qb.simpledemo.util.http;


import com.qb.simpledemo.bean.image.ImageBean;
import com.qb.simpledemo.bean.movie.MovieList;
import com.qb.simpledemo.bean.movie.Subject;
import com.qb.simpledemo.bean.music.MusicBean;
import com.qb.simpledemo.bean.music.MusicList;
import com.qb.simpledemo.view.movie.IMovieFragmentList;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HttpMethod {
    private static final int DEFAULT_TIMEOUT = 5;
    private static Retrofit retrofit;
    private static HttpService httpService;
    private static String BASE_URL;
    private static OkHttpClient.Builder httpClientBuilder;

    private HttpMethod() {
    }

    private static class SingletonHolder {
        private static final HttpMethod INSTANCE = new HttpMethod();
    }

    //获取单例
    public static HttpMethod getInstance(String url) {
        BASE_URL = url;
        initRetrofit();
        return SingletonHolder.INSTANCE;
    }

    private static void initRetrofit() {
        httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        httpService = retrofit.create(HttpService.class);
    }

    public void getMovies(Subscriber<MovieList<List<Subject>>> subscriber, int type, int start, int count) {
        String urlType = "";
        switch (type) {
            case 0:
                urlType = "top250";
                break;
            case 1:
                urlType = "in_theaters";
                break;
            case 2:
                urlType = "coming_soon";
                break;
            default:
                urlType = "top250";
                break;
        }
        httpService.getTopNews(urlType, start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getImage(Subscriber<List<ImageBean>> subscriber) {
        httpService.getImage()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getMusic(Subscriber<MusicList<List<MusicBean>>> subscriber, String q, int start, int count) {
        httpService.getMusic(q, start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
