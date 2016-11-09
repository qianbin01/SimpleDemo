package com.qb.simpledemo.presenter.movie;


import com.qb.simpledemo.view.movie.IMoviewDetailView;

public class IMovieDetailPresenterImpl implements IMovieDetailPresenter {
    private IMoviewDetailView mView;

    public IMovieDetailPresenterImpl(IMoviewDetailView mView) {
        this.mView = mView;
    }

    @Override
    public void loadOver() {
        mView.hideProgress();
    }

    @Override
    public void loadStart() {
        mView.showProgress();
        mView.showMusicDetail();
    }
}
