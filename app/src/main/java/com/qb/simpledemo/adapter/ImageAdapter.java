package com.qb.simpledemo.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qb.simpledemo.R;
import com.qb.simpledemo.bean.image.ImageBean;
import com.qb.simpledemo.util.destiny.DestinyUtil;
import com.qb.simpledemo.view.image.ZoomImageActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private int mMaxWidth;
    private int mMaxHeight;
    private List<ImageBean> mList;
    private OnItemClickListener onItemClickListener;


    public ImageAdapter(Context mContext, List<ImageBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mMaxWidth = DestinyUtil.getWidthInPx(mContext) - 20;
        mMaxHeight = DestinyUtil.getHeightInPx(mContext) - DestinyUtil.getStatusHeight(mContext) -
                DestinyUtil.dip2px(mContext, 96);
    }

    public void setList(List<ImageBean> mList) {
        this.mList = mList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int posistion);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        final ImageBean imageBean = mList.get(position);
        if (imageBean == null) {
            return;
        }
        holder.tvTitle.setText(imageBean.getTitle());
        float scale = (float) imageBean.getWidth() / (float) mMaxWidth;
        int height = (int) (imageBean.getHeight() / scale);
        if (height > mMaxHeight) {
            height = mMaxHeight;
        }
        holder.ivImage.setLayoutParams(new LinearLayout.LayoutParams(mMaxWidth, height));
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
                }
                Bundle bundle = new Bundle();
                bundle.putString("pic", imageBean.getThumburl());
                Intent intent = new Intent(mContext, ZoomImageActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        Picasso.with(mContext).load(imageBean.getThumburl()).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivImage;

        ImageViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            ivImage = (ImageView) v.findViewById(R.id.ivImage);

        }


    }

}
