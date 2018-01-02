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

package com.marktony.zhihudaily.retrofit;

import com.marktony.zhihudaily.data.DoubanMomentContent;
import com.marktony.zhihudaily.data.DoubanMomentNews;
import com.marktony.zhihudaily.data.GuokrHandpickContent;
import com.marktony.zhihudaily.data.GuokrHandpickNews;
import com.marktony.zhihudaily.data.ZhihuDailyContent;
import com.marktony.zhihudaily.data.ZhihuDailyNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Interface of retrofit requests. API included.
 */

public interface RetrofitService {

    String ZHIHU_DAILY_BASE = "https://news-at.zhihu.com/api/4/news/";

    String DOUBAN_MOMENT_BASE = "https://moment.douban.com/api/";

    String GUOKR_HANDPICK_BASE = "http://apis.guokr.com/minisite/";

    interface ZhihuDailyService {

        @GET("before/{date}")
        Call<ZhihuDailyNews> getZhihuList(@Path("date") String date);

        @GET("{id}")
        Call<ZhihuDailyContent> getZhihuContent(@Path("id") int id);

    }

    interface DoubanMomentService {

        @GET("stream/date/{date}")
        Call<DoubanMomentNews> getDoubanList(@Path("date") String date);

        @GET("post/{id}")
        Call<DoubanMomentContent> getDoubanContent(@Path("id") int id);

    }

    interface GuokrHandpickService {

        @GET("article.json?retrieve_type=by_minisite")
        Call<GuokrHandpickNews> getGuokrHandpick(@Query("offset")int offset, @Query("limit")int limit );

        @GET("article/{id}.json")
        Call<GuokrHandpickContent> getGuokrContent(@Path("id")int id);

    }

}
