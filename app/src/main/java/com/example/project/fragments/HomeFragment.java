package com.example.project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.activitys.detail;
import com.example.project.data.MoviesRepository;
import com.example.project.models.Movie;
import com.example.project.adapters.MoviesGridAdapter;
import com.example.project.utils.FavoritesStore;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private GridView grid;
    private EditText etSearchHome;
    private MoviesGridAdapter adapter;
    private ArrayList<Movie> allMovies;
    private ArrayList<Movie> filteredMovies;

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup container, Bundle s) {
        View v = inf.inflate(R.layout.fragment_home, container, false);

        grid = v.findViewById(R.id.gridMovies);
        etSearchHome = v.findViewById(R.id.et_search_home);

        Button btnTopRated = v.findViewById(R.id.btnTopRated);
        if (btnTopRated != null) {
            btnTopRated.setOnClickListener(click ->
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new TopRatedFragment())
                            .addToBackStack(null)
                            .commit()
            );
        }

        // المصدر الأساسي للأفلام
        allMovies = MoviesRepository.getAll();
        if (allMovies == null) allMovies = new ArrayList<>();
        filteredMovies = new ArrayList<>(allMovies);

        //  (Adapter)
        adapter = new MoviesGridAdapter(requireContext(), filteredMovies);
        grid.setAdapter(adapter);

        // فتح صفحة التفاصيل وتمرير البيانات فعليًا
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie m = filteredMovies.get(position);
                Intent i = new Intent(requireContext(), detail.class);
                i.putExtras(buildDetailsBundle(m));
                startActivity(i);
            }
        });




        // بحث حيّ مع تحديث القائمة
        etSearchHome.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { filter(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        return v;
    }

    private Bundle buildDetailsBundle(Movie m) {
        Bundle b = new Bundle();
        b.putString("title", m.title);
        b.putString("rating", m.rating);
        b.putInt("posterRes", m.posterRes);

        switch (m.title) {
            case "Venom":
                b.putString("duration", "2h 20m");
                b.putString("summary", "A Journalist Eddie Brock investigates the shady experiments of the Life Foundation, led by Carlton Drake. During an investigation, Eddie becomes host to an alien symbiote called Venom, gaining superhuman strength, agility, and violent instincts. At first, Eddie struggles with Venom’s hunger and brutal nature, but they slowly form a bond. Together, they fight Drake, who merges with another symbiote, Riot, planning to bring more aliens to Earth. In a fierce battle, Venom sacrifices himself to stop Riot, but secretly survives, staying with Eddie. The story mixes dark humor, action, and the start of an unusual hero–antihero partnership.");
                b.putStringArrayList("genres", new ArrayList<>(Arrays.asList("Action","Science Fiction","Superhero","Thriller dark humor")));
                b.putIntArray("actorsRes", new int[]{
                        R.drawable.tom_hardy, R.drawable.riz_ahmed, R.drawable.scott_haze, R.drawable.michelle_williams
                });
                b.putStringArrayList("actorsNames", new ArrayList<>(Arrays.asList(
                        "Tom Hardy","Riz Ahmed","Scott Haze","Michelle Williams"
                )));
                break;

            case "Extraction 2":
                b.putString("duration", "2h 3m");
                b.putString("summary", "After narrowly surviving the deadly mission in Dhaka, Tyler Rake recovers but is soon pulled back into action. This time, he must rescue his ex-wife’s sister and her two children, held captive by her abusive gangster husband inside a heavily guarded Georgian prison. The mission erupts into chaos with an explosive one-shot sequence of riots, shootouts, and daring escapes. The danger intensifies when Zurab, the gangster’s brother, vows revenge and relentlessly hunts Tyler. With the help of his team, including Nik and Yaz, Tyler fights through brutal chases on trains, cars, and helicopters. Beyond the action, the film highlights themes of family, redemption, and sacrifice as Tyler seeks not only to protect the innocent but also to find personal closure.");
                b.putStringArrayList("genres", new ArrayList<>(Arrays.asList("Action","Thriller","Adventure")));
                b.putIntArray("actorsRes", new int[]{
                        R.drawable.chris_hemsworth, R.drawable.golshifteh_farahani, R.drawable.adam_bessa, R.drawable.tornike_gogrichiani
                });
                b.putStringArrayList("actorsNames", new ArrayList<>(Arrays.asList(
                        "Chris Hemsworth","Golshifteh Farahani","Adam Bessa","Tornike Gogrichiani"
                )));
                break;

            case "Avatar":
                b.putString("duration", "2h 42m");
                b.putString("summary", "Discover a world of enchanting visuals and epic storytelling in Avatar: The Way to Water. Directed by visionary filmmaker James Cameron, this highly anticipated sequel to the groundbreaking original promises to transport you to the mesmerizing realm of Pandora once again. Set in the lush and vibrant underwater habitats of Pandora, this cinematic masterpiece unravels an extraordinary adventure. As the Na’vi navigate the depths, you’ll embark on a journey that blends cutting-edge technology with a tale of environmental harmony and the resilience of the human spirit.");
                b.putStringArrayList("genres", new ArrayList<>(Arrays.asList("Drama","Adventure","Science Fiction","Action")));
                b.putIntArray("actorsRes", new int[]{
                        R.drawable.sam, R.drawable.weaver, R.drawable.zoe, R.drawable.ribisi
                });
                b.putStringArrayList("actorsNames", new ArrayList<>(Arrays.asList(
                        "Sam Worthington","Sigourney Weaver","Zoe Saldana","Giovanni Ribisi"
                )));
                break;

            case "The Old Guard 2":
                b.putString("duration", "2h 5m");
                b.putString("summary", "Six months after the first film, Andy continues to lead her immortal team with Nile, Joe, Nicky, and Copley. Meanwhile, Booker is captured by Quỳnh, who returns seeking revenge. The group learns that Nile has a rare power to weaken other immortals. A greater threat arises when Discord, the first immortal, seizes a nuclear plant and threatens millions. Booker sacrifices himself to save Andy, while Nile and Quỳnh are captured. The film ends with Andy and Quỳnh vowing to rescue their comrades, setting up a third installment.");
                b.putStringArrayList("genres", new ArrayList<>(Arrays.asList("Action","Fantasy","Supernatural","Team Adventure")));
                b.putIntArray("actorsRes", new int[]{
                        R.drawable.charlize_theron, R.drawable.kiki_layne, R.drawable.chiwetel_ejiofor, R.drawable.luca_marinelli
                });
                b.putStringArrayList("actorsNames", new ArrayList<>(Arrays.asList(
                        "Charlize Theron","KiKi Layne","Chiwetel Ejiofor","Luca Marinelli"
                )));
                break;

            default:
                b.putString("duration", "2h 00m");
                b.putString("summary", "No summary available.");
                b.putStringArrayList("genres", new ArrayList<>(Arrays.asList("Action")));
                b.putIntArray("actorsRes", new int[]{0, 0, 0, 0});
                b.putStringArrayList("actorsNames", new ArrayList<>(Arrays.asList(
                        "Actor 1","Actor 2","Actor 3","Actor 4"
                )));
        }
        return b;
    }

    // فلترة البحث مع تحديث القائمة
    private void filter(String q) {
        q = q == null ? "" : q.toLowerCase().trim();
        filteredMovies.clear();
        if (q.isEmpty()) {
            filteredMovies.addAll(allMovies);
        } else {
            for (Movie m : allMovies) {
                if (m.title != null && m.title.toLowerCase().contains(q)) {
                    filteredMovies.add(m);
                }
            }
        }
        adapter.update(filteredMovies);
    }
}
