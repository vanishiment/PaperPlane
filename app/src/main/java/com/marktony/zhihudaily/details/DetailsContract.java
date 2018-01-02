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

package com.marktony.zhihudaily.details;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.marktony.zhihudaily.BasePresenter;
import com.marktony.zhihudaily.BaseView;
import com.marktony.zhihudaily.data.ContentType;
import com.marktony.zhihudaily.data.DoubanMomentContent;
import com.marktony.zhihudaily.data.DoubanMomentNewsThumbs;
import com.marktony.zhihudaily.data.GuokrHandpickContentResult;
import com.marktony.zhihudaily.data.ZhihuDailyContent;

import java.util.List;

/**
 * Created by lizhaotailang on 2017/5/24.
 *
 * This specifies contract between the view and the presenter.
 */

public interface DetailsContract {

    interface View extends BaseView<Presenter> {

        void showMessage(@StringRes int stringRes);

        boolean isActive();

        void showZhihuDailyContent(@NonNull ZhihuDailyContent content);

        void showDoubanMomentContent(@NonNull DoubanMomentContent content, @Nullable List<DoubanMomentNewsThumbs> list);

        void showGuokrHandpickContent(@NonNull GuokrHandpickContentResult content);

        void share(@Nullable String link);

        void copyLink(@Nullable String link);

        void openWithBrowser(@Nullable String link);

    }

    interface Presenter extends BasePresenter {

        void favorite(ContentType type, int id, boolean favorite);

        void loadDoubanContent(int id);

        void loadZhihuDailyContent(int id);

        void loadGuokrHandpickContent(int id);

        void getLink(ContentType type, int requestCode, int id);

    }

}
