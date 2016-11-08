package com.qb.simpledemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qb.simpledemo.R;
import com.qb.simpledemo.bean.movie.Subject;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private Context mContext;
    private List<Subject> mList;
    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;

    public void setmList(List<Subject> mList) {
        this.mList = mList;
    }

    public MovieAdapter(Context context, List<Subject> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        final Subject subject = mList.get(position);
        if (subject == null) {
            return;
        }
        String genres = "";
        for (int i = 0; i < subject.getGenres().size(); i++) {
            genres += " " + subject.getGenres().get(i);
        }
        holder.tvTitle.setText(subject.getTitle());
        holder.tvRating.setText(subject.getRats().getAverage());
        holder.tvYear.setText(subject.getYear());
        holder.tvGenres.setText(genres);
        Picasso.with(mContext).load(subject.getImages().getLarge()).noFade().into(holder.ivImage);
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
                System.out.println(subject.getAlt());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvYear, tvRating, tvGenres;
        private ImageView ivImage;

        MovieHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvYear = (TextView) itemView.findViewById(R.id.tvYear);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            tvGenres = (TextView) itemView.findViewById(R.id.tvGenres);
        }
    }
}
