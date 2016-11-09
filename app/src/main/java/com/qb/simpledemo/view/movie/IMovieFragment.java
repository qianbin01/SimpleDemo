package com.qb.simpledemo.view.movie;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qb.simpledemo.R;
import com.qb.simpledemo.adapter.MovieAdapter;
import com.qb.simpledemo.bean.movie.Subject;
import com.qb.simpledemo.presenter.movie.IMoviePresenter;
import com.qb.simpledemo.presenter.movie.IMoviePresenterImpl;
import com.qb.simpledemo.view.music.IMusicDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IMovieFragment extends Fragment implements IMovieView {

    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private IMoviePresenter mPresenter;
    private List<Subject> mList;
    private MovieAdapter mAdapter;
    private int start = 0;
    private int count = 10;
    private static int ADD_FLAG = 0;
    private static int ADD_HEAD = 0;
    private static int ADD_BOTTOM = 1;
    private int mType=IMovieFragmentList.MOVIE_TYPE_TOP;
    public static IMovieFragment newInstance(int type) {
        Bundle args = new Bundle();
        IMovieFragment fragment = new IMovieFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new IMoviePresenterImpl(this);
        mType=getArguments().getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragment, null);
        ButterKnife.bind(this, view);
        initData();
        initConfig();
        mPresenter.loadMovies(mType,start, count);
        return view;
    }

    private void initData() {
        mList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new MovieAdapter(getActivity(), mList);
    }

    private void initConfig() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count += 3;
                mPresenter.loadMovies(mType,start, count);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +1 == mAdapter.getItemCount() && ADD_FLAG == ADD_BOTTOM) {
                    //加载更多
                    Snackbar.make(getActivity().findViewById(R.id.drawer_layout), "正在加载", Snackbar.LENGTH_SHORT).show();
                    count += 3;
                    mPresenter.loadMovies(mType,start, count);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                boolean isSignificantDelta = Math.abs(dy) > 10;
                if (isSignificantDelta) {
                    if (dy > 0) {//判断上下滑
                        ADD_FLAG = ADD_BOTTOM;
                    } else {
                        ADD_FLAG = ADD_HEAD;
                    }
                }

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String title, image, content;
                title = mList.get(position).getTitle();
                image = mList.get(position).getImages().getLarge();
                content = mList.get(position).getAlt()+"/mobile";
                Intent intent = new Intent(getActivity(), IMovieDetailActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("image", image);
                intent.putExtra("content", content);
                View tranView = v.findViewById(R.id.ivImage);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                tranView, "transtion_view");
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        });
    }

    @Override
    public void addMovies(List<Subject> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.setmList(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if (isAdded()) {
            View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
            Snackbar.make(view, "加载失败", Snackbar.LENGTH_SHORT).show();
        }
    }
}
