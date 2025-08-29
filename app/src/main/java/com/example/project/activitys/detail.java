package com.example.project.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.R;
import com.example.project.models.Movie;
import com.example.project.utils.FavoritesStore;

import java.util.ArrayList;
import java.util.Arrays;

public class detail extends AppCompatActivity {

    // Views
    private TextView movieTitle, tvRating, tvDuration, summaryText;
    private ImageView posterImage, img1, img2, img3, img4;
    private LinearLayout genreLayout;

    // أسماء الممثلين تحت الصور
    private TextView name1, name2, name3, name4;

    // زر القلب + عنوان الفيلم
    private ImageView btnFav;
    private String movieTitleStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        View root = findViewById(R.id.main);
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        bindViews();

        // ========== قراءة البيانات القادمة ==========
        Bundle b = getIntent().getExtras();
        if (b == null) {
            Toast.makeText(this, "No details received", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String title     = b.getString("title", "");
        String rating    = b.getString("rating", "");
        int posterRes    = b.getInt("posterRes", 0);
        String duration  = b.getString("duration", "");
        String summary   = b.getString("summary", "");
        ArrayList<String> genres = b.getStringArrayList("genres");
        int[] actorsRes          = b.getIntArray("actorsRes");
        ArrayList<String> actorsNames = b.getStringArrayList("actorsNames");

        movieTitleStr = title;

        // ========== تعيين القيم ==========
        movieTitle.setText(title);
        if (!rating.isEmpty()  && tvRating   != null) tvRating.setText("⭐ "  + rating);
        if (!duration.isEmpty()&& tvDuration != null) tvDuration.setText("⏱️ " + duration);
        if (!summary.isEmpty() && summaryText!= null) summaryText.setText(summary);
        if (posterRes != 0 && posterImage != null) posterImage.setImageResource(posterRes);

        bindGenres(genres);
        bindActorsAndNames(actorsRes, actorsNames);

        // ========== زر القلب ==========
        if (btnFav != null && movieTitleStr != null && !movieTitleStr.trim().isEmpty()) {
            boolean isFav = FavoritesStore.isFav(this, movieTitleStr);
            updateFavIcon(isFav);

            btnFav.setOnClickListener(v -> {
                if (movieTitleStr == null || movieTitleStr.trim().isEmpty()) return;
                FavoritesStore.toggle(this, movieTitleStr);
                boolean nowFav = FavoritesStore.isFav(this, movieTitleStr); // اقرأ الحالة بعد التبديل
                updateFavIcon(nowFav);
                Toast.makeText(this,
                        nowFav ? "Added to favorites" : "Removed from favorites",
                        Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void bindViews() {
        // عناصر تنقّل (إن وُجدت في XML)
        ImageButton bt_back = findViewById(R.id.bt_back);
        ImageView iv_home   = findViewById(R.id.iv_home);
        ImageView iv_settings = findViewById(R.id.iv_settings);
        ImageView iv_fav    = findViewById(R.id.iv_fav);
        ImageView iv_search = findViewById(R.id.iv_search);

        if (bt_back != null) bt_back.setOnClickListener(v -> finish());

        // Home: رجوع بدل تكديس MainActivity
        if (iv_home != null) {
            iv_home.setOnClickListener(v -> finish());
        }

        // Settings: لو عندك Activity اسمها settings (يفضل تسميتها SettingsActivity)
        if (iv_settings != null) {
            iv_settings.setOnClickListener(v -> {
                Intent i_setting = new Intent(detail.this, settings.class);
                startActivity(i_setting);
            });
        }

        if (iv_search != null) {
            iv_search.setOnClickListener(v -> {
                Intent i = new Intent(detail.this, MainActivity.class);
                i.putExtra("dest", "search");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                // ملاحظة: لا تستدعي finish() هنا
            });
        }

        if (iv_fav != null) {
            iv_fav.setOnClickListener(v -> {
                Intent i = new Intent(detail.this, MainActivity.class);
                i.putExtra("dest", "favorites");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                // لا تستدعي finish() هنا
            });
        }



        // عناصر العرض
        movieTitle   = findViewById(R.id.movieTitle);
        posterImage  = findViewById(R.id.posterImage);
        tvRating     = findViewById(R.id.tvRating);
        tvDuration   = findViewById(R.id.tvDuration);
        summaryText  = findViewById(R.id.summaryText);
        genreLayout  = findViewById(R.id.genreLayout);

        // صور الممثلين
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);

        // أسماء الممثلين
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        name4 = findViewById(R.id.name4);

        btnFav = findViewById(R.id.btnFav);
    }

    private void goToMainWithDest(String dest) {
        Intent i = new Intent(detail.this, MainActivity.class);
        i.putExtra("dest", dest); // MainActivity لازم يقرأ هذا الـextra ويفتح التبويب المناسب
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    private void bindGenres(ArrayList<String> genres) {
        if (genreLayout == null || genres == null || genres.isEmpty()) return;

        genreLayout.removeAllViews();
        final float d = getResources().getDisplayMetrics().density;

        for (String g : genres) {
            TextView chip = new TextView(this);
            chip.setText(g);
            chip.setTypeface(Typeface.DEFAULT_BOLD);
            chip.setTextColor(0xFFFFFFFF);
            chip.setBackgroundColor(0xFF252836);
            int pad = (int) (8 * d);
            chip.setPadding(pad, pad, pad, pad);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(0, 0, (int) (8 * d), 0);
            chip.setLayoutParams(lp);

            genreLayout.addView(chip);
        }
    }

    private void bindActorsAndNames(int[] actorsRes, ArrayList<String> actorsNames) {
        ImageView[] imgs = new ImageView[]{img1, img2, img3, img4};
        TextView[] names = new TextView[]{name1, name2, name3, name4};

        if (actorsRes != null) {
            for (int i = 0; i < Math.min(imgs.length, actorsRes.length); i++) {
                if (imgs[i] != null && actorsRes[i] != 0) {
                    imgs[i].setImageResource(actorsRes[i]);
                }
            }
        }

        if (actorsNames != null) {
            for (int i = 0; i < Math.min(names.length, actorsNames.size()); i++) {
                if (names[i] != null) names[i].setText(actorsNames.get(i));
                if (imgs[i] != null) imgs[i].setContentDescription(actorsNames.get(i));
            }
        }
    }

    private void updateFavIcon(boolean isFav) {
        if (btnFav == null) return;
        btnFav.setColorFilter(isFav
                ? android.graphics.Color.parseColor("#E53935")
                : android.graphics.Color.parseColor("#BDBDBD"));
    }

    public static Intent makeIntent(Context ctx, Movie m) {
        Intent i = new Intent(ctx, detail.class);
        if (m == null) return i;

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
                b.putIntArray("actorsRes", new int[]{0,0,0,0});
                b.putStringArrayList("actorsNames", new ArrayList<>(Arrays.asList(
                        "Actor 1","Actor 2","Actor 3","Actor 4"
                )));
        }

        i.putExtras(b);
        return i;
    }
}
