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

package com.marktony.zhihudaily.data.source.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.marktony.zhihudaily.data.DoubanMomentContent;
import com.marktony.zhihudaily.data.source.datasource.DoubanMomentContentDataSource;

/**
 * Created by lizhaotailang on 2017/5/25.
 *
 * Concrete implementation to load {@link DoubanMomentContent} from the data sources into a cache.
 * <p>
 *     Use the remote data source firstly, which is obtained from the server.
 *     If the remote data was not available, then use the local data source,
 *     which was from the locally persisted in database.
 * </p>
 */

public class DoubanMomentContentRepository implements DoubanMomentContentDataSource {

    @Nullable
    private static DoubanMomentContentRepository INSTANCE = null;

    @NonNull
    private final DoubanMomentContentDataSource mLocalDataSource;

    @NonNull
    private final DoubanMomentContentDataSource mRemoteDataSource;

    @Nullable
    private DoubanMomentContent mContent;

    private DoubanMomentContentRepository(@NonNull DoubanMomentContentDataSource remoteDataSource,
                                          @NonNull DoubanMomentContentDataSource localDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static DoubanMomentContentRepository getInstance(@NonNull DoubanMomentContentDataSource remoteDataSource,
                                                            @NonNull DoubanMomentContentDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DoubanMomentContentRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getDoubanMomentContent(int id, @NonNull LoadDoubanMomentContentCallback callback) {
        if (mContent != null) {
            callback.onContentLoaded(mContent);
            return;
        }

        // Get data from net first.
        mRemoteDataSource.getDoubanMomentContent(id, new LoadDoubanMomentContentCallback() {
            @Override
            public void onContentLoaded(@NonNull DoubanMomentContent content) {
                if (mContent == null) {
                    mContent = content;
                    saveContent(content);
                }
                callback.onContentLoaded(content);
            }

            @Override
            public void onDataNotAvailable() {
                mLocalDataSource.getDoubanMomentContent(id, new LoadDoubanMomentContentCallback() {
                    @Override
                    public void onContentLoaded(@NonNull DoubanMomentContent content) {
                        if (mContent == null) {
                            mContent = content;
                        }
                        callback.onContentLoaded(content);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveContent(@NonNull DoubanMomentContent content) {
        mLocalDataSource.saveContent(content);
        mRemoteDataSource.saveContent(content);
    }
}
