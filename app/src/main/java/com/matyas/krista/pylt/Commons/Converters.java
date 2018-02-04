package com.matyas.krista.pylt.Commons;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.matyas.krista.pylt.EIObjects.EITag;
import com.matyas.krista.pylt.EIObjects.EIType;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Matyas on 2018.02.04..
 */

public class Converters {
    @TypeConverter
    public static EITag fromString(String value) {
        Type objType = new TypeToken<EITag>() {}.getType();
        return new Gson().fromJson(value, objType);
    }
    @TypeConverter
    public static String fromEITag(EITag eiTag) {
        Gson gson = new Gson();
        String json = gson.toJson(eiTag);
        return json;
    }

    @TypeConverter
    public static EIType fromStringEIType(String value) {
        Type objType = new TypeToken<EIType>() {}.getType();
        return new Gson().fromJson(value, objType);
    }
    @TypeConverter
    public static String fromEIType(EIType eiType) {
        Gson gson = new Gson();
        String json = gson.toJson(eiType);
        return json;
    }

    @TypeConverter
    public static Date fromStringDate(String value) {
        Type objType = new TypeToken<Date>() {}.getType();
        return new Gson().fromJson(value, objType);
    }
    @TypeConverter
    public static String fromDate(Date date) {
        Gson gson = new Gson();
        String json = gson.toJson(date);
        return json;
    }
}