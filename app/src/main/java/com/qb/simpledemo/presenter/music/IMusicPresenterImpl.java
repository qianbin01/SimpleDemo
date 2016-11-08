package com.qb.simpledemo.presenter.music;


import com.qb.simpledemo.bean.music.MusicBean;
import com.qb.simpledemo.model.music.IMusicModel;
import com.qb.simpledemo.model.music.IMusicModelImpl;
import com.qb.simpledemo.view.music.IMusicView;

import java.util.List;

public class IMusicPresenterImpl implements IMusicPresenter, IMusicModelImpl.OnLoadMusicCallback {
    private IMusicView mView;
    private IMusicModel mModel;


    public IMusicPresenterImpl(IMusicView mView) {
        this.mView = mView;
        this.mModel = new IMusicModelImpl();
    }

    @Override
    public void OnSuccess(List<MusicBean> list) {
        mView.addMusic(list);
        mView.hideProgress();
    }

    @Override
    public void OnFailure(String msg, Throwable e) {
        mView.hideProgress();
        mView.showLoadFailMsg();
    }

    @Override
    public void OnLoadMusic(String q, int start, int count) {
        mView.showProgress();
        mModel.onLoadMusic(this, q, start, count);
    }
}
