package com.qb.simpledemo.view.movie;


import com.qb.simpledemo.bean.movie.Subject;

import java.util.List;

public interface IMovieView {
    void addMovies(List<Subject> list);
    void showProgress();
    void hideProgress();
    void showLoadFailMsg();
}
