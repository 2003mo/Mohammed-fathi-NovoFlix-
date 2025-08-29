package com.example.project.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class FavoritesStore {
    private static final String PREF = "FAVS";
    private static final String KEY_SET = "titles";

    private static SharedPreferences prefs(Context c) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public static void toggle(Context c, String title) {
        Set<String> s = new HashSet<>(getAllTitles(c));
        if (s.contains(title)) s.remove(title); else s.add(title);
        prefs(c).edit().putStringSet(KEY_SET, s).apply();
    }

    public static boolean isFav(Context c, String title) {
        return getAllTitles(c).contains(title);
    }

    public static Set<String> getAllTitles(Context c) {
        Set<String> s = prefs(c).getStringSet(KEY_SET, null);
        return (s == null) ? new HashSet<>() : new HashSet<>(s);
    }
}
