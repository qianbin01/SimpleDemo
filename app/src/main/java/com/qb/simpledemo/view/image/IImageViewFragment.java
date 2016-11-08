package com.qb.simpledemo.view.image;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qb.simpledemo.R;
import com.qb.simpledemo.adapter.ImageAdapter;
import com.qb.simpledemo.bean.image.ImageBean;
import com.qb.simpledemo.presenter.image.IImagePresenter;
import com.qb.simpledemo.presenter.image.IImagePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IImageViewFragment extends Fragment implements IImageView {
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;
    @Bind(R.id.recycle_view)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private IImagePresenter mPresenter;
    private List<ImageBean> mList;
    private ImageAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new IImagePresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_fragment, null);
        ButterKnife.bind(this, view);
        initData();
        initConfig();
        return view;
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity());

    }

    private void initConfig() {
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorPrimaryDark,
                R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadImage();
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ImageAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    //加载更多
                    Snackbar.make(getActivity().findViewById(R.id.drawer_layout), "每次加载20条，请刷新重试", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();


            }
        });
        mPresenter.loadImage();
    }

    @Override
    public void addImages(List<ImageBean> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.setList(mList);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if (isAdded()) {
            View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
            Snackbar.make(view, "加载失败", Snackbar.LENGTH_SHORT).show();
        }
    }
}
