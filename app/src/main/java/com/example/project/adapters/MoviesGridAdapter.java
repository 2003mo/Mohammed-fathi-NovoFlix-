package com.example.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.models.Movie;

import java.util.List;

public class MoviesGridAdapter extends BaseAdapter {
    private final Context context;
    private List<Movie> data;
    private final LayoutInflater inflater;

    public MoviesGridAdapter(Context context, List<Movie> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    public void update(List<Movie> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    @Override public int getCount() { return data.size(); }
    @Override public Object getItem(int i) { return data.get(i); }
    @Override public long getItemId(int i) { return i; }

    static class ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvRating;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            h = new ViewHolder();
            h.ivPoster = convertView.findViewById(R.id.ivPoster);
            h.tvTitle = convertView.findViewById(R.id.tvTitle);
            h.tvRating = convertView.findViewById(R.id.tvRating);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        Movie m = data.get(pos);
        h.ivPoster.setImageResource(m.posterRes);
        h.tvTitle.setText(m.title);
        h.tvRating.setText("⭐ " + m.rating);
        return convertView;
    }
}
