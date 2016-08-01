/*
 * Copyright 2016 Monu Surana
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.nytimes.app;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.example.nytimes.BuildConfig;
import com.example.nytimes.R;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by monusurana on 7/20/16.
 */
public class NYTimesApplication extends Application {
    private static NYTimesApplication singleton;

    public static NYTimesApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        singleton = this;

        Timber.plant(new Timber.DebugTree());
        Timber.d("Commit Id: " + BuildConfig.GIT_SHA);
        Timber.d("Time: " + BuildConfig.BUILD_TIME);

        if (BuildConfig.DEBUG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .build());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .penaltyDeathOnNetwork()
                        .build());
            }
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Georgia.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
