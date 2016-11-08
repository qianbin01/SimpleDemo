package com.qb.simpledemo.model.movie;


import com.qb.simpledemo.bean.movie.MovieList;
import com.qb.simpledemo.bean.movie.Subject;
import com.qb.simpledemo.common.UrlConstant;
import com.qb.simpledemo.util.http.HttpMethod;

import java.util.List;

import rx.Subscriber;

public class IMovieModelImpl implements IMovieModel {
    @Override
    public void loadMovies(final OnLoadMovieCallback callback, int type, int start, int count) {
        Subscriber<MovieList<List<Subject>>> subscriber = new Subscriber<MovieList<List<Subject>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.toString());
                callback.OnFailure(e.toString(), e);
            }

            @Override
            public void onNext(MovieList<List<Subject>> movieList) {
                callback.OnSuccess(movieList.getSubjects());
            }
        };
        HttpMethod.getInstance(UrlConstant.MOVIE_URL).getMovies(subscriber, type, start, count);
    }

    public interface OnLoadMovieCallback {
        void OnSuccess(List<Subject> list);

        void OnFailure(String msg, Throwable e);
    }
}
