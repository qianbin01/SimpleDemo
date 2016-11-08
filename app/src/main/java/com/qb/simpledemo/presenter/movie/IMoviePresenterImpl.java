package com.qb.simpledemo.presenter.movie;


import com.qb.simpledemo.bean.movie.Subject;
import com.qb.simpledemo.model.movie.IMovieModel;
import com.qb.simpledemo.model.movie.IMovieModelImpl;
import com.qb.simpledemo.view.movie.IMovieView;

import java.util.List;

public class IMoviePresenterImpl implements IMoviePresenter, IMovieModelImpl.OnLoadMovieCallback {
    private IMovieView mView;
    private IMovieModel mModel;

    public IMoviePresenterImpl(IMovieView mView) {
        this.mView = mView;
        this.mModel = new IMovieModelImpl();
    }

    @Override
    public void loadMovies(int type,int start, int count) {
        if (count == 10) {
            mView.showProgress();
        }
        mModel.loadMovies(this,type, start, count);
    }

    @Override
    public void OnSuccess(List<Subject> list) {
        mView.addMovies(list);
        mView.hideProgress();

    }

    @Override
    public void OnFailure(String msg, Throwable e) {
        mView.hideProgress();
        mView.showLoadFailMsg();
    }
}
