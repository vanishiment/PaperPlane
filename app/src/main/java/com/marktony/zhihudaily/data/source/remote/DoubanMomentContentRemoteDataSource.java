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

package com.marktony.zhihudaily.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.marktony.zhihudaily.data.DoubanMomentContent;
import com.marktony.zhihudaily.data.source.datasource.DoubanMomentContentDataSource;
import com.marktony.zhihudaily.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lizhaotailang on 2017/5/25.
 *
 * Implementation of the {@link DoubanMomentContent} data source that accesses network.
 */

public class DoubanMomentContentRemoteDataSource implements DoubanMomentContentDataSource {

    @Nullable
    private static DoubanMomentContentRemoteDataSource INSTANCE = null;

    // Prevent direct instantiation.
    private DoubanMomentContentRemoteDataSource() {}

    public static DoubanMomentContentRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DoubanMomentContentRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getDoubanMomentContent(int id, @NonNull LoadDoubanMomentContentCallback callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.DOUBAN_MOMENT_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService.DoubanMomentService service = retrofit.create(RetrofitService.DoubanMomentService.class);

        service.getDoubanContent(id).enqueue(new Callback<DoubanMomentContent>() {
            @Override
            public void onResponse(Call<DoubanMomentContent> call, Response<DoubanMomentContent> response) {
                callback.onContentLoaded(response.body());
            }

            @Override
            public void onFailure(Call<DoubanMomentContent> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void saveContent(@NonNull DoubanMomentContent content) {
        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }
}
