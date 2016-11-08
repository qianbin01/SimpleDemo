package com.qb.simpledemo.model.music;


import com.qb.simpledemo.bean.music.MusicBean;
import com.qb.simpledemo.bean.music.MusicList;
import com.qb.simpledemo.common.UrlConstant;
import com.qb.simpledemo.util.http.HttpMethod;

import java.util.List;

import rx.Subscriber;

public class IMusicModelImpl implements IMusicModel {
    @Override
    public void onLoadMusic(final OnLoadMusicCallback callback, String q, int start, int count) {
        Subscriber<MusicList<List<MusicBean>>> subscriber = new Subscriber<MusicList<List<MusicBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callback.OnFailure(e.toString(), e);
            }

            @Override
            public void onNext(MusicList<List<MusicBean>> listMusicList) {
                callback.OnSuccess(listMusicList.getMusic());
            }
        };
        HttpMethod.getInstance(UrlConstant.MUSIC_URL).getMusic(subscriber,q,start,count);
    }

    public interface OnLoadMusicCallback {
        void OnSuccess(List<MusicBean> list);

        void OnFailure(String msg, Throwable e);
    }
}
