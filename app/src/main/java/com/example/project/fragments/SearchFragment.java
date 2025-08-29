package com.example.project.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.project.R;
import com.example.project.activitys.detail;
import com.example.project.data.MoviesRepository;
import com.example.project.models.Movie;
import com.example.project.adapters.MoviesGridAdapter;
// (اختياري) للمفضلة:
// import com.example.project.utils.FavoritesStore;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private EditText etSearch;
    private GridView grid;
    private MoviesGridAdapter adapter;
    private ArrayList<Movie> all;
    private ArrayList<Movie> filtered;

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup container, Bundle s) {
        View v = inf.inflate(R.layout.fragment_search, container, false);
        etSearch = v.findViewById(R.id.et_search);
        grid = v.findViewById(R.id.gridSearch);

        all = MoviesRepository.getAll();
        filtered = new ArrayList<>(all);

        adapter = new MoviesGridAdapter(requireContext(), filtered);
        grid.setAdapter(adapter);

        // فتح التفاصيل عند الضغط (يمرّر كل البيانات عبر detail.makeIntent)
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie m = filtered.get(position);
                startActivity(detail.makeIntent(requireContext(), m));
            }
        });



        // فلترة للبحث
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { filter(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        return v;
    }

    private void filter(String q) {
        q = q.toLowerCase().trim();
        filtered.clear();
        if (q.isEmpty()) {
            filtered.addAll(all);
        } else {
            for (Movie m : all) {
                if (m.title.toLowerCase().contains(q)) filtered.add(m);
            }
        }
        adapter.update(filtered);
    }
}
