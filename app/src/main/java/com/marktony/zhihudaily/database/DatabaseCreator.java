/*
 * Copyright 2016 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marktony.zhihudaily.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lizhaotailang on 2017/6/15.
 *
 * Create the {@link AppDatabase} asynchronously.
 */

public class DatabaseCreator {

    @Nullable
    private static DatabaseCreator INSTANCE = null;

    private AppDatabase mDb;

    private final AtomicBoolean mInitializing = new AtomicBoolean(true);
    private final AtomicBoolean mIsDbCreated = new AtomicBoolean(false);

    // For Singleton instantiation
    private static final Object LOCK = new Object();

    public synchronized static DatabaseCreator getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new DatabaseCreator();
                }
            }
        }
        return INSTANCE;
    }

    public void createDb(Context context) {

        Log.d("DatabaseCreator", "Creating DB from " + Thread.currentThread().getName());

        if (!mInitializing.compareAndSet(true, false)) {
            return;
        }

        new AsyncTask<Context, Void, Void>() {

            @Override
            protected Void doInBackground(Context... contexts) {
                Log.d("DatabaseCreator", "Starting bg job " + Thread.currentThread().getName());

                Context ctx = contexts[0].getApplicationContext();

                mDb = Room.databaseBuilder(ctx, AppDatabase.class, AppDatabase.DATABASE_NAME).build();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mIsDbCreated.set(true);
            }
        }.execute(context.getApplicationContext());


    }

    public boolean isDatabaseCreated() {
        return mIsDbCreated.get();
    }

    @Nullable
    public AppDatabase getDatabase() {
        return mDb;
    }

}
