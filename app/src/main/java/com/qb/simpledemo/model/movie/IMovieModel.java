package com.qb.simpledemo.model.movie;



public interface IMovieModel {
    void loadMovies(IMovieModelImpl.OnLoadMovieCallback callback,int type,int start,int count);
}
