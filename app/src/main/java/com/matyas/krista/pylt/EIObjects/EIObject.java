package com.matyas.krista.pylt.EIObjects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.matyas.krista.pylt.Commons.Converters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Matyas on 2018.02.03..
 */


@Entity
public class EIObject {

    @PrimaryKey(autoGenerate = true)
    long id;
    private String name;
    @TypeConverters(Converters.class)
    private EITag tag;
    @TypeConverters(Converters.class)
    private EIType eitype;
    private long amount;
    @TypeConverters(Converters.class)
    private Date date;

    private static ArrayList<EIObject> allObjects = new ArrayList<>();

    public EIObject(String name, EIType eitype, long amount, EITag tag) {
        allObjects.add(this);
        this.name = name;
        this.eitype = eitype;
        this.amount = amount;
        this.tag = tag;
        this.date = Calendar.getInstance().getTime();
    }

    public static ArrayList<EIObject> getAllObjects() {
        return allObjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EIType getEitype() {
        return eitype;
    }

    public void setEitype(EIType eitype) {
        this.eitype = eitype;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EITag getTag() {
        return tag;
    }

    public void setTag(EITag tag) {
        this.tag = tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




    public static long getFullAmount(EIType eitype) {
        long result = 0;
        for (EIObject object : allObjects) {
            if(object.getEitype().equals(eitype)) {
                result += object.getAmount();
            }
        }
        return result;
    }

    public static void setAllObjects(ArrayList<EIObject> allObjects) {
        EIObject.allObjects = allObjects;
    }

    public static ArrayList<String> getAllObjectsAsStringsByType(EIType eiType) {
        ArrayList arrayList = new ArrayList(){};
        for (EIObject o : EIObject.getAllObjects()) {
            if (o.getEitype().equals(eiType)) {
                arrayList.add(o.getName() + " " +
                        o.getEitype().name() + " " +
                        o.getTag().getName() + " " +
                        o.getAmount() + " " +
                        o.getDate());
            }
        }
        return arrayList;
    }
}
