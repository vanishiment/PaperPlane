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
import com.marktony.zhihudaily.data.GuokrHandpickContentChannel;
import com.marktony.zhihudaily.data.GuokrHandpickNewsAuthor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaotailang on 2017/6/17.
 *
 * Type converters of {@link com.marktony.zhihudaily.data.GuokrHandpickNewsResult}.
 * Converts a list of {@link String} or {@link GuokrHandpickContentChannel}
 * or {@link GuokrHandpickNewsAuthor} to a {@link String} and back.
 */

public class GuokrResultTypeConverter {

    @TypeConverter
    public static String stringListToString(List<String> strings) {
        return new Gson().toJson(strings);
    }

    @TypeConverter
    public static List<String> stringToStringList(String string) {
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(string, listType);
    }

    @TypeConverter
    public static String resultListToString(List<GuokrHandpickContentChannel> channels) {
        return new Gson().toJson(channels);
    }

    @TypeConverter
    public static List<GuokrHandpickContentChannel> stringToResultList(String string) {
        Type listType = new TypeToken<ArrayList<GuokrHandpickContentChannel>>(){}.getType();
        return new Gson().fromJson(string, listType);
    }

    @TypeConverter
    public static String authorListToString(List<GuokrHandpickNewsAuthor> authors) {
        return new Gson().toJson(authors);
    }

    @TypeConverter
    public static List<GuokrHandpickNewsAuthor> stringToAuthorList(String string) {
        Type listType = new TypeToken<ArrayList<GuokrHandpickNewsAuthor>>(){}.getType();
        return new Gson().fromJson(string, listType);
    }

}
