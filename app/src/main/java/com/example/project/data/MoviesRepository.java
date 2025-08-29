package com.example.project.data;

import com.example.project.R;
import com.example.project.models.Movie;
import java.util.ArrayList;

public class MoviesRepository {

    public static ArrayList<Movie> getAll() {
        ArrayList<Movie> list = new ArrayList<>();
        list.add(new Movie(R.drawable.mv, "Venom", "8.8"));
        list.add(new Movie(R.drawable.extraction, "Extraction 2", "9.3"));
        list.add(new Movie(R.drawable.avatar_detail, "Avatar", "8.5"));
        list.add(new Movie(R.drawable.old_guard, "The Old Guard 2", "9.5"));
        return list;
    }
}
