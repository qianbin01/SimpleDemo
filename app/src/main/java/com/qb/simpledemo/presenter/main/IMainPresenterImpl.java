package com.qb.simpledemo.presenter.main;


import com.qb.simpledemo.R;
import com.qb.simpledemo.view.main.IMainView;

public class IMainPresenterImpl implements IMainPresenter {
    private IMainView mView;

    public IMainPresenterImpl(IMainView mView) {
        this.mView = mView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation_item_movies:
                mView.switch2Movies();
                break;
            case R.id.navigation_item_images:
                mView.switch2Images();
                break;
            case R.id.navigation_item_musics:
                mView.switch2Music();
                break;
            case R.id.navigation_item_about:
                mView.switch2About();
                break;
            default:
                mView.switch2Movies();
                break;
        }
    }
}
