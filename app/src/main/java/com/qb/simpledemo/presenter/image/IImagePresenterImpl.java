package com.qb.simpledemo.presenter.image;


import com.qb.simpledemo.bean.image.ImageBean;
import com.qb.simpledemo.model.image.IImageModel;
import com.qb.simpledemo.model.image.IImageModelImpl;
import com.qb.simpledemo.view.image.IImageView;

import java.util.List;

public class IImagePresenterImpl implements IImagePresenter, IImageModelImpl.OnLoadImageCallback {
    private IImageModel mModel;
    private IImageView mView;

    public IImagePresenterImpl(IImageView mView) {
        this.mView = mView;
        mModel = new IImageModelImpl();
    }

    @Override
    public void OnSuccess(List<ImageBean> list) {
        mView.addImages(list);
        mView.hideProgress();
    }

    @Override
    public void OnFailure(String msg, Throwable e) {
        mView.hideProgress();
        mView.showLoadFailMsg();
    }

    @Override
    public void loadImage() {
        mView.showProgress();
        mModel.onLoadImage(this);
    }
}
