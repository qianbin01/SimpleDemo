package com.qb.simpledemo.view.image;


import com.qb.simpledemo.bean.image.ImageBean;

import java.util.List;

public interface IImageView {
    void addImages(List<ImageBean> list);
    void showProgress();
    void hideProgress();
    void showLoadFailMsg();
}
