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

import com.marktony.zhihudaily.data.DoubanMomentNewsPosts;
import com.marktony.zhihudaily.data.source.datasource.DoubanMomentNewsDataSource;
import com.marktony.zhihudaily.database.AppDatabase;
import com.marktony.zhihudaily.database.DatabaseCreator;

import java.util.List;

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * * Concrete implementation of a {@link DoubanMomentNewsPosts} data source as database .
 */

public class DoubanMomentNewsLocalDataSource implements DoubanMomentNewsDataSource {

    @Nullable
    private static DoubanMomentNewsLocalDataSource INSTANCE = null;

    @Nullable
    private AppDatabase mDb = null;

    private DoubanMomentNewsLocalDataSource(@NonNull Context context) {
        DatabaseCreator creator = DatabaseCreator.getInstance();
        if (!creator.isDatabaseCreated()) {
            creator.createDb(context);
        }
    }

    public static DoubanMomentNewsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DoubanMomentNewsLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getDoubanMomentNews(boolean forceUpdate, boolean clearCache, long date, @NonNull LoadDoubanMomentDailyCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, List<DoubanMomentNewsPosts>>() {

                @Override
                protected List<DoubanMomentNewsPosts> doInBackground(Void... voids) {
                    return mDb.doubanMomentNewsDao().queryAllByDate(date);
                }

                @Override
                protected void onPostExecute(List<DoubanMomentNewsPosts> list) {
                    super.onPostExecute(list);
                    if (list == null) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onNewsLoaded(list);
                    }
                }
            }.execute();
        }
    }

    @Override
    public void getFavorites(@NonNull LoadDoubanMomentDailyCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, List<DoubanMomentNewsPosts>>() {

                @Override
                protected List<DoubanMomentNewsPosts> doInBackground(Void... voids) {
                    return mDb.doubanMomentNewsDao().queryAllFavorites();
                }

                @Override
                protected void onPostExecute(List<DoubanMomentNewsPosts> list) {
                    super.onPostExecute(list);
                    if (list == null) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onNewsLoaded(list);
                    }
                }
            }.execute();
        }
    }

    @Override
    public void getItem(int id, @NonNull GetNewsItemCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, DoubanMomentNewsPosts>() {

                @Override
                protected DoubanMomentNewsPosts doInBackground(Void... voids) {
                    return mDb.doubanMomentNewsDao().queryItemById(id);
                }

                @Override
                protected void onPostExecute(DoubanMomentNewsPosts item) {
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
                DoubanMomentNewsPosts tmp = mDb.doubanMomentNewsDao().queryItemById(itemId);
                tmp.setFavorite(favorite);
                mDb.doubanMomentNewsDao().update(tmp);
            }).start();
        }
    }

    @Override
    public void saveAll(@NonNull List<DoubanMomentNewsPosts> list) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new Thread(() -> {
                mDb.beginTransaction();
                try {
                    mDb.doubanMomentNewsDao().insertAll(list);
                    mDb.setTransactionSuccessful();
                } finally {
                    mDb.endTransaction();
                }
            }).start();
        }
    }

}
