package com.example.project.adapters;

import android.content.Context;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.models.Movie;

import java.util.List;

public class MoviesListAdapter extends BaseAdapter {
    private final Context ctx;
    private List<Movie> data;
    private final LayoutInflater inf;

    public MoviesListAdapter(Context ctx, List<Movie> data) {
        this.ctx = ctx;
        this.data = data;
        this.inf = LayoutInflater.from(ctx);
    }

    public void update(List<Movie> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    @Override public int getCount() { return data.size(); }
    @Override public Object getItem(int position) { return data.get(position); }
    @Override public long getItemId(int position) { return position; }

    static class VH {
        ImageView ivThumb;
        TextView tvTitle, tvMeta;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        VH h;
        if (convertView == null) {
            convertView = inf.inflate(R.layout.item_movie_row, parent, false);
            h = new VH();
            h.ivThumb = convertView.findViewById(R.id.ivThumb);
            h.tvTitle = convertView.findViewById(R.id.tvTitle);
            h.tvMeta  = convertView.findViewById(R.id.tvMeta);
            convertView.setTag(h);
        } else {
            h = (VH) convertView.getTag();
        }

        Movie m = data.get(pos);
        h.ivThumb.setImageResource(m.posterRes);
        h.tvTitle.setText(m.title);
        h.tvMeta.setText("⭐ " + m.rating + " • 2h 15m"); // مدة تجريبية
        return convertView;
    }
}
