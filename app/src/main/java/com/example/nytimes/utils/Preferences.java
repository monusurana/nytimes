package com.example.nytimes.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Preferences {
    private static final String PREF_FILTER_BEGIN_DATE = "beginDate";
    private static final String PREF_FILTER_SORT_ORDER = "sortOrder";
    private static final String PREF_FILTER_NEWS_DESK_VALUE_ARTS = "arts";
    private static final String PREF_FILTER_NEWS_DESK_VALUE_FASHION_AND_STYLE = "fashion_and_style";
    private static final String PREF_FILTER_NEWS_DESK_VALUE_SPORTS = "sports";
    private static final SimpleDateFormat dateFormatterForApi = new SimpleDateFormat("yyyyMMdd");

    public static long getFilterBeginDate(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(PREF_FILTER_BEGIN_DATE, 0);
    }

    public static void setFilterBeginDate(Context context, long timeInMillis) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putLong(PREF_FILTER_BEGIN_DATE, timeInMillis)
                .apply();
    }

    public static void clearFilterBeginDate(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(PREF_FILTER_BEGIN_DATE)
                .apply();
    }

    public static String getFilterSortOrder(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_FILTER_SORT_ORDER, null);
    }

    public static void setFilterSortOrder(Context context, String sortOrder) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_FILTER_SORT_ORDER, sortOrder)
                .apply();
    }

    public static boolean isFilterNewsDeskValueArts(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_FILTER_NEWS_DESK_VALUE_ARTS, false);
    }

    public static void setIsFilterNewsDeskValueArts(Context context, boolean isNewsDeskValueArts) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_FILTER_NEWS_DESK_VALUE_ARTS, isNewsDeskValueArts)
                .apply();
    }

    public static boolean isFilterNewsDeskValueFashionAndStyle(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_FILTER_NEWS_DESK_VALUE_FASHION_AND_STYLE, false);
    }

    public static void setIsFilterNewsDeskValueFashionAndStyle(
            Context context, boolean isNewsDeskValueFashionAndStyle) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(
                        PREF_FILTER_NEWS_DESK_VALUE_FASHION_AND_STYLE,
                        isNewsDeskValueFashionAndStyle)
                .apply();
    }

    public static boolean isFilterNewsDeskValueSports(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_FILTER_NEWS_DESK_VALUE_SPORTS, false);
    }

    public static void setIsFilterNewsDeskValueSports(
            Context context, boolean isNewsDeskValueSports) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_FILTER_NEWS_DESK_VALUE_SPORTS, isNewsDeskValueSports)
                .apply();
    }

    public static Map<String, String> optionalQueryParameters(Context context) {
        HashMap<String, String> queryAdditionalParameters = new HashMap<>();

        long beginDateMillis = getFilterBeginDate(context);
        if (beginDateMillis != 0) {
            Date d = new Date(beginDateMillis);
            queryAdditionalParameters.put("begin_date", dateFormatterForApi.format(d));
        }
        String sortOrder = getFilterSortOrder(context);
        if (sortOrder != null) {
            sortOrder = sortOrder.toLowerCase();
        }
        if (TextUtils.equals("oldest", sortOrder) || TextUtils.equals("newest", sortOrder)) {
            queryAdditionalParameters.put("sort", sortOrder);
        }

        List<String> newsDeskValues = new ArrayList<>();
        if (isFilterNewsDeskValueArts(context)) {
            newsDeskValues.add("Arts");
        }
        if (isFilterNewsDeskValueFashionAndStyle(context)) {
            newsDeskValues.add("Fashion & Style");
        }
        if (isFilterNewsDeskValueSports(context)) {
            newsDeskValues.add("Sports");
        }

        if (!newsDeskValues.isEmpty()) {
            StringBuilder sb = new StringBuilder("news_desk:(\"");
            sb.append(newsDeskValues.get(0));
            sb.append('"');

            for (int i = 1; i < newsDeskValues.size(); i++) {
                sb.append(" \"");
                sb.append(newsDeskValues.get(i));
                sb.append('"');
            }
            sb.append(')');
            queryAdditionalParameters.put("fq", sb.toString());
        }

        return queryAdditionalParameters;
    }
}