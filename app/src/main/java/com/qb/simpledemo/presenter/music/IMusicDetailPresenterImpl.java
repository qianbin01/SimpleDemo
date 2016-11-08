package com.qb.simpledemo.presenter.music;


import com.qb.simpledemo.view.music.IMusicDetaView;

public class IMusicDetailPresenterImpl implements IMusicDetailPresenter {
    private IMusicDetaView mView;

    public IMusicDetailPresenterImpl(IMusicDetaView mView) {
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
