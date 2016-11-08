package com.qb.simpledemo.model.image;


import com.qb.simpledemo.bean.image.ImageBean;
import com.qb.simpledemo.common.UrlConstant;
import com.qb.simpledemo.util.http.HttpMethod;

import java.util.List;

import rx.Subscriber;

public class IImageModelImpl implements IImageModel {
    @Override
    public void onLoadImage(final OnLoadImageCallback callback) {
        Subscriber<List<ImageBean>> subscriber = new Subscriber<List<ImageBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callback.OnFailure(e.toString(), e);
            }

            @Override
            public void onNext(List<ImageBean> imageBeen) {
                callback.OnSuccess(imageBeen);
            }
        };
        HttpMethod.getInstance(UrlConstant.IMAGE_URL).getImage(subscriber);
    }

    public interface OnLoadImageCallback {
        void OnSuccess(List<ImageBean> list);

        void OnFailure(String msg, Throwable e);
    }
}
