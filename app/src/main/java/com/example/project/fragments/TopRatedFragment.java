package com.example.project.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.project.R;
import com.example.project.activitys.detail;
import com.example.project.data.MoviesRepository;
import com.example.project.models.Movie;
import com.example.project.adapters.MoviesListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TopRatedFragment extends Fragment {

    private ListView list;
    private MoviesListAdapter adapter;
    private ArrayList<Movie> top;

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup container, Bundle s) {
        View v = inf.inflate(R.layout.fragment_top_rated, container, false);
        list = v.findViewById(R.id.listTop);

        top = MoviesRepository.getAll();

        // ترتيب تنازليا حسب التقييم
        try {
            Collections.sort(top, new Comparator<Movie>() {
                @Override public int compare(Movie a, Movie b) {
                    float ra = Float.parseFloat(a.rating);
                    float rb = Float.parseFloat(b.rating);
                    return Float.compare(rb, ra);
                }
            });
        } catch (Exception ignore) {}

        adapter = new MoviesListAdapter(requireContext(), top);
        list.setAdapter(adapter);

        // فتح التفاصيل وتمرير كل البيانات عبر makeIntent
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie m = top.get(position);
                startActivity(detail.makeIntent(requireContext(), m));
            }
        });

        return v;
    }
}
