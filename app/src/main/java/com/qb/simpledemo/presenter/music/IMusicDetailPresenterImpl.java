package com.qb.simpledemo.presenter.music;


import com.qb.simpledemo.view.music.IMusicDetailView;

public class IMusicDetailPresenterImpl implements IMusicDetailPresenter {
    private IMusicDetailView mView;

    public IMusicDetailPresenterImpl(IMusicDetailView mView) {
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
