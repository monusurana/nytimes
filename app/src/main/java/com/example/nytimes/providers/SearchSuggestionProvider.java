package com.example.nytimes.providers;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by monusurana on 7/30/16.
 */
public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.nytimes.providers.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}