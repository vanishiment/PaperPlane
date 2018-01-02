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

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.marktony.zhihudaily.data.DoubanMomentContent;
import com.marktony.zhihudaily.data.DoubanMomentNewsPosts;
import com.marktony.zhihudaily.data.GuokrHandpickContentResult;
import com.marktony.zhihudaily.data.GuokrHandpickNewsResult;
import com.marktony.zhihudaily.data.ZhihuDailyContent;
import com.marktony.zhihudaily.data.ZhihuDailyNewsQuestion;
import com.marktony.zhihudaily.database.dao.DoubanMomentContentDao;
import com.marktony.zhihudaily.database.dao.DoubanMomentNewsDao;
import com.marktony.zhihudaily.database.dao.GuokrHandpickContentDao;
import com.marktony.zhihudaily.database.dao.GuokrHandpickNewsDao;
import com.marktony.zhihudaily.database.dao.ZhihuDailyContentDao;
import com.marktony.zhihudaily.database.dao.ZhihuDailyNewsDao;


/**
 * Created by lizhaotailang on 2017/6/15.
 *
 * Main database description.
 */

@Database(entities = {
        ZhihuDailyNewsQuestion.class,
        DoubanMomentNewsPosts.class,
        GuokrHandpickNewsResult.class,
        ZhihuDailyContent.class,
        DoubanMomentContent.class,
        GuokrHandpickContentResult.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "paper-plane-db";

    public abstract ZhihuDailyNewsDao zhihuDailyNewsDao();

    public abstract DoubanMomentNewsDao doubanMomentNewsDao();

    public abstract GuokrHandpickNewsDao guokrHandpickNewsDao();

    public abstract ZhihuDailyContentDao zhihuDailyContentDao();

    public abstract DoubanMomentContentDao doubanMomentContentDao();

    public abstract GuokrHandpickContentDao guokrHandpickContentDao();

}
