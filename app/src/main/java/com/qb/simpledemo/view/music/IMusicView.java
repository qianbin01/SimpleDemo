package com.qb.simpledemo.view.music;


import com.qb.simpledemo.bean.music.MusicBean;

import java.util.List;

public interface IMusicView {
    void addMusic(List<MusicBean> list);
    void showProgress();
    void hideProgress();
    void showLoadFailMsg();
}
