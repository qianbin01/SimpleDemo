package com.qb.simpledemo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qb.simpledemo.R;
import com.qb.simpledemo.bean.music.MusicBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private Context mContext;
    private List<MusicBean> mList;
    private LayoutInflater mInflater;
    private onItemClickListener onItemClickListener;

    public MusicAdapter(Context mContext, List<MusicBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<MusicBean> mList) {
        this.mList = mList;
    }

    public interface onItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(MusicAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.music_item, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, final int position) {

        MusicBean musicBean = mList.get(position);
        if (musicBean == null) {
            return;
        }
        holder.tvTitle.setText(musicBean.getTitle());
        holder.tvName.setText(musicBean.getAuthor().get(0).getName());
        holder.tvDate.setText(musicBean.getPubdate());
        holder.tvRating.setText(musicBean.getRating().getAverage());
        Picasso.with(mContext).load(musicBean.getImage()).into(holder.ivImage);
        holder.rlMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvName, tvDate, tvRating;
        ImageView ivImage;
        RelativeLayout rlMusic;

        MusicViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            rlMusic = (RelativeLayout) itemView.findViewById(R.id.rlMusic);
        }
    }
}
