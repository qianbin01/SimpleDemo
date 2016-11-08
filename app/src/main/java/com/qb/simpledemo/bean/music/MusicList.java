package com.qb.simpledemo.bean.music;


public class MusicList<T> {
    private int count;
    private int start;
    private int total;
    private T musics;
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getMusic() {
        return musics;
    }

    public void setMusic(T music) {
        this.musics = music;
    }
}
