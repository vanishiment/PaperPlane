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

import com.marktony.zhihudaily.data.ZhihuDailyNewsQuestion;
import com.marktony.zhihudaily.data.source.datasource.ZhihuDailyNewsDataSource;
import com.marktony.zhihudaily.database.AppDatabase;
import com.marktony.zhihudaily.database.DatabaseCreator;

import java.util.List;

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Concrete implementation of a {@link ZhihuDailyNewsQuestion} data source as database.
 */

public class ZhihuDailyNewsLocalDataSource implements ZhihuDailyNewsDataSource {

    @Nullable
    private static ZhihuDailyNewsLocalDataSource INSTANCE = null;

    @Nullable
    private AppDatabase mDb = null;

    private ZhihuDailyNewsLocalDataSource(@NonNull Context context) {
        DatabaseCreator creator = DatabaseCreator.getInstance();
        if (!creator.isDatabaseCreated()) {
            creator.createDb(context);
        }
    }

    public static ZhihuDailyNewsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ZhihuDailyNewsLocalDataSource(context);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getZhihuDailyNews(boolean forceUpdate, boolean clearCache, long date, @NonNull LoadZhihuDailyNewsCallback callback) {

        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, List<ZhihuDailyNewsQuestion>>() {

                @Override
                protected List<ZhihuDailyNewsQuestion> doInBackground(Void... voids) {
                    return mDb.zhihuDailyNewsDao().queryAllByDate(date);
                }

                @Override
                protected void onPostExecute(List<ZhihuDailyNewsQuestion> list) {
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
    public void getFavorites(@NonNull LoadZhihuDailyNewsCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if(mDb != null) {
            new AsyncTask<Void, Void, List<ZhihuDailyNewsQuestion>>() {

                @Override
                protected List<ZhihuDailyNewsQuestion> doInBackground(Void... voids) {
                    return mDb.zhihuDailyNewsDao().queryAllFavorites();
                }

                @Override
                protected void onPostExecute(List<ZhihuDailyNewsQuestion> list) {
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
    public void getItem(int itemId, @NonNull GetNewsItemCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, ZhihuDailyNewsQuestion>() {
                @Override
                protected ZhihuDailyNewsQuestion doInBackground(Void... voids) {
                    return mDb.zhihuDailyNewsDao().queryItemById(itemId);
                }

                @Override
                protected void onPostExecute(ZhihuDailyNewsQuestion item) {
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
                ZhihuDailyNewsQuestion tmp = mDb.zhihuDailyNewsDao().queryItemById(itemId);
                tmp.setFavorite(favorite);
                mDb.zhihuDailyNewsDao().update(tmp);
            }).start();
        }
    }

    @Override
    public void saveAll(@NonNull List<ZhihuDailyNewsQuestion> list) {

        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null){
            new Thread(() -> {
                mDb.beginTransaction();
                try {
                    mDb.zhihuDailyNewsDao().insertAll(list);
                    mDb.setTransactionSuccessful();
                } finally {
                    mDb.endTransaction();
                }
            }).start();
        }
    }
}
