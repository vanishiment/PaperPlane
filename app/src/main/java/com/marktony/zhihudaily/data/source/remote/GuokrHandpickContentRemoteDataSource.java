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

import com.marktony.zhihudaily.data.GuokrHandpickContent;
import com.marktony.zhihudaily.data.GuokrHandpickContentResult;
import com.marktony.zhihudaily.data.source.datasource.GuokrHandpickContentDataSource;
import com.marktony.zhihudaily.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lizhaotailang on 2017/5/26.
 *
 * Implementation of the {@link GuokrHandpickContent} data source that accesses network.
 */

public class GuokrHandpickContentRemoteDataSource implements GuokrHandpickContentDataSource {

    @Nullable
    private static GuokrHandpickContentRemoteDataSource INSTANCE = null;

    // Prevent direct instantiation.
    private GuokrHandpickContentRemoteDataSource() {}

    public static GuokrHandpickContentRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GuokrHandpickContentRemoteDataSource();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getGuokrHandpickContent(int id, @NonNull LoadGuokrHandpickContentCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.GUOKR_HANDPICK_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService.GuokrHandpickService service = retrofit.create(RetrofitService.GuokrHandpickService.class);

        service.getGuokrContent(id)
                .enqueue(new Callback<GuokrHandpickContent>() {
                    @Override
                    public void onResponse(Call<GuokrHandpickContent> call, Response<GuokrHandpickContent> response) {
                        callback.onContentLoaded(response.body().getResult());
                    }

                    @Override
                    public void onFailure(Call<GuokrHandpickContent> call, Throwable t) {

                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void saveContent(@NonNull GuokrHandpickContentResult content) {
        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }
}
