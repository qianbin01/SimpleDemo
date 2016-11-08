package com.qb.simpledemo.util.http;


import com.qb.simpledemo.bean.image.ImageBean;
import com.qb.simpledemo.bean.movie.MovieList;
import com.qb.simpledemo.bean.movie.Subject;
import com.qb.simpledemo.bean.music.MusicBean;
import com.qb.simpledemo.bean.music.MusicList;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


interface HttpService {
    @GET("{type}")
    Observable<MovieList<List<Subject>>> getTopNews(@Path("type")String type,@Query("start") int start, @Query("count") int count);

    @GET("tupian.json")
    Observable<List<ImageBean>> getImage();

    @GET("search")
    Observable<MusicList<List<MusicBean>>> getMusic(@Query("q") String q, @Query("start") int start, @Query("count") int count);
}
