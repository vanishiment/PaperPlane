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

package com.marktony.zhihudaily.data.source.local;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.marktony.zhihudaily.data.GuokrHandpickNewsResult;
import com.marktony.zhihudaily.data.source.datasource.GuokrHandpickDataSource;
import com.marktony.zhihudaily.database.AppDatabase;
import com.marktony.zhihudaily.database.DatabaseCreator;

import java.util.List;

/**
 * Created by lizhaotailang on 2017/5/24.
 *
 * Concrete implementation of a {@link GuokrHandpickNewsResult} data source as database.
 */

public class GuokrHandpickNewsLocalDataSource implements GuokrHandpickDataSource {

    @Nullable
    private static GuokrHandpickNewsLocalDataSource INSTANCE = null;

    @Nullable
    private AppDatabase mDb = null;

    private GuokrHandpickNewsLocalDataSource(@NonNull Context context) {
        DatabaseCreator creator = DatabaseCreator.getInstance();
        if (!creator.isDatabaseCreated()) {
            creator.createDb(context);
        }
        mDb = creator.getDatabase();
    }

    public static GuokrHandpickNewsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GuokrHandpickNewsLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getGuokrHandpickNews(boolean forceUpdate, boolean clearCache, int offset, int limit, @NonNull LoadGuokrHandpickNewsCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, List<GuokrHandpickNewsResult>>() {

                @Override
                protected List<GuokrHandpickNewsResult> doInBackground(Void... voids) {
                    return mDb.guokrHandpickNewsDao().queryAllByOffsetAndLimit(offset, limit);
                }

                @Override
                protected void onPostExecute(List<GuokrHandpickNewsResult> list) {
                    super.onPostExecute(list);
                    if (list == null) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onNewsLoad(list);
                    }
                }
            }.execute();
        }
    }

    @Override
    public void getFavorites(@NonNull LoadGuokrHandpickNewsCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, List<GuokrHandpickNewsResult>>() {

                @Override
                protected List<GuokrHandpickNewsResult> doInBackground(Void... voids) {
                    return mDb.guokrHandpickNewsDao().queryAllFavorites();
                }

                @Override
                protected void onPostExecute(List<GuokrHandpickNewsResult> list) {
                    super.onPostExecute(list);
                    if (list == null) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onNewsLoad(list);
                    }
                }
            }.execute();
        }
    }

    @Override
    public void getItem(int itemId, @NonNull GetNewsItemCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, GuokrHandpickNewsResult>() {

                @Override
                protected GuokrHandpickNewsResult doInBackground(Void... voids) {
                    return mDb.guokrHandpickNewsDao().queryItemById(itemId);
                }

                @Override
                protected void onPostExecute(GuokrHandpickNewsResult item) {
                    super.onPostExecute(item);
                    if (item == null) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onItemLoaded(item);
                    }
                }
            }.execute();
        }
    }

    @Override
    public void favoriteItem(int itemId, boolean favorite) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new Thread(() -> {
                GuokrHandpickNewsResult tmp = mDb.guokrHandpickNewsDao().queryItemById(itemId);
                if (tmp != null) {
                    tmp.setFavorite(favorite);
                    mDb.guokrHandpickNewsDao().update(tmp);
                }
            }).start();
        }
    }

    @Override
    public void saveAll(@NonNull List<GuokrHandpickNewsResult> list) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new Thread(() -> {
                mDb.beginTransaction();
                try {
                    mDb.guokrHandpickNewsDao().insertAll(list);
                    mDb.setTransactionSuccessful();
                } finally {
                    mDb.endTransaction();
                }
            }).start();
        }
    }

}
