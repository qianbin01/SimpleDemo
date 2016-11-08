package com.qb.simpledemo.model.music;

public interface IMusicModel {
    void onLoadMusic(IMusicModelImpl.OnLoadMusicCallback callback,String q,int start,int count);
}
