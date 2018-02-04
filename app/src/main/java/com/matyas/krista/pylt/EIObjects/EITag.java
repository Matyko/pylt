package com.matyas.krista.pylt.EIObjects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Matyas on 2018.02.03..
 */

public class EITag {

    private String name;
    private long amount;
    private EIType type;
    private static List<EITag> tags = new ArrayList<>();

    private EITag(String name, long amount, EIType type) {
        this.name = name;
        this.amount = amount;
        this.type = type;
    }

    public EITag() {
    }

    public static boolean addTag(String name, long amount,  EIType type) {
        for (EITag tag : tags) {
            if (tag.name.equals(name)) {
                tag.amount += amount;
                return false;
            }
        }
        tags.add(new EITag(name, amount, type));
        return true;
    }

    public static EITag getTag(String name) {
        for (EITag tag : tags) {
            if(tag.name.equals(name)) {
                return tag;
            }
        }
        return null;
    }

    public long getAmount() {
        return amount;
    }

    public static List<EITag> getTags() {
        return tags;
    }

    public String getName() {
        return name;
    }

    public EIType getType() {
        return type;
    }

    public void setType(EIType type) {
        this.type = type;
    }

}
