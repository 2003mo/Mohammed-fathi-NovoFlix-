package com.example.project.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.R;
import com.example.project.fragments.FavoritesFragment;
import com.example.project.fragments.HomeFragment;
import com.example.project.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_home, iv_search, iv_fav, iv_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // حواف النظام - طبّق الأربع جهات
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });

        // ربط العناصر
        iv_home     = findViewById(R.id.iv_home);
        iv_search   = findViewById(R.id.iv_search);
        iv_fav      = findViewById(R.id.iv_fav);
        iv_settings = findViewById(R.id.iv_settings);

        // الواجهة الابتدائية (تراعي dest لو جاي من DetailActivity)
        if (savedInstanceState == null) {
            navigateTo(getIntent().getStringExtra("dest"));
        }

        // تنقل الشريط السفلي (بدون فتح Activity لنفسها)
        if (iv_home != null)     iv_home.setOnClickListener(v -> showHome());
        if (iv_search != null)   iv_search.setOnClickListener(v -> showSearch());
        if (iv_fav != null) {
            iv_fav.setOnClickListener(v -> showFavorites());
            iv_fav.setOnLongClickListener(v -> false); // إلغاء أي ضغط مطوّل قديم
        }
        if (iv_settings != null) {
            // إن كانت Activity اسمها settings؛ يفضّل لاحقًا تسميتها SettingsActivity
            iv_settings.setOnClickListener(v ->
                    startActivity(new Intent(MainActivity.this, settings.class))
            );
        }
    }

    // لو رجعت عبر FLAG_ACTIVITY_SINGLE_TOP | CLEAR_TOP
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        navigateTo(intent.getStringExtra("dest"));
    }

    private void navigateTo(String dest) {
        if ("search".equals(dest)) {
            showSearch();
        } else if ("favorites".equals(dest)) {
            showFavorites();
        } else {
            showHome();
        }
    }

    //انشأت دالة showHome
    private void showHome() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new HomeFragment())
                .commit();
    }

    //انشأت دالة showSearch
    private void showSearch() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new SearchFragment())
                .addToBackStack(null)
                .commit();
    }

    //انشأت دالة showFavorites
    private void showFavorites() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new FavoritesFragment())
                .addToBackStack(null)
                .commit();
    }
}
