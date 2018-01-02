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

package com.marktony.zhihudaily.database.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marktony.zhihudaily.data.DoubanMomentNewsThumbs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaotailang on 2017/6/17.
 *
 * Type converters for {@link com.marktony.zhihudaily.data.DoubanMomentNewsPosts}.
 * Converts list of {@link DoubanMomentNewsThumbs} to a {@link String} and back.
 */

public class DoubanTypeConverters {

    @TypeConverter
    public static String thumbListToString(List<DoubanMomentNewsThumbs> thumbs) {
        return new Gson().toJson(thumbs);
    }

    @TypeConverter
    public static List<DoubanMomentNewsThumbs> stringToThumbList(String string) {
        Type listType = new TypeToken<ArrayList<DoubanMomentNewsThumbs>>(){}.getType();
        return new Gson().fromJson(string, listType);
    }

}
