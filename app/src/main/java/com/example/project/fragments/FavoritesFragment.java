package com.example.project.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.activitys.detail;
import com.example.project.data.MoviesRepository;
import com.example.project.models.Movie;
import com.example.project.adapters.MoviesGridAdapter;
import com.example.project.utils.FavoritesStore;

import java.util.ArrayList;
import java.util.Set;

public class FavoritesFragment extends Fragment {

    private GridView grid;
    private TextView tvEmpty;
    private MoviesGridAdapter adapter;
    private ArrayList<Movie> favMovies = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup container, Bundle s) {
        View v = inf.inflate(R.layout.fragment_favorites, container, false);
        tvEmpty = v.findViewById(R.id.tvEmpty);
        grid = v.findViewById(R.id.gridFavs);

        loadFavs();
        adapter = new MoviesGridAdapter(requireContext(), favMovies);
        grid.setAdapter(adapter);
        grid.setEmptyView(tvEmpty);

        //AdapterView<?> parent : الي تم الضغط على العنصر الي بداخلهGrid view  هو ال
        //View view:  الفردي اللي ضغط عليها المستخدم Viewال
        //position : ترتيب الفلم داخل القائمة
        //id : رقم الاي دي للفلم
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie m = favMovies.get(position);
                //يفتح واجهة التفاصيل الخاصة بالفلم الي ضغط عليه المستخدم وخزنه في متغير (m)
                startActivity(detail.makeIntent(requireContext(), m));
            }
        });
        return v;
    }
    //onResume() : دالة عشان اضل واجهة المفضلة دائما محدثة
    @Override
    public void onResume() {
        super.onResume();
        loadFavs();
        adapter.update(favMovies);
    }

    private void loadFavs() {
        favMovies.clear();
        Set<String> titles = FavoritesStore.getAllTitles(requireContext());
        if (titles == null || titles.isEmpty()) return;

        ArrayList<Movie> all = MoviesRepository.getAll();
        for (Movie m : all) {
            if (titles.contains(m.title)) favMovies.add(m);
        }
    }
}
