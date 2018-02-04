package com.matyas.krista.pylt.EIObjects;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by Matyas on 2018.02.04..
 */
@Dao
public interface EIObjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEIObject(EIObject... eiObjects);

    @Update
    public int updateEIObject(EIObject... eiObjects);

    @Delete
    public void deleteObject(EIObject... eiObjects);

    @Query("SELECT * FROM EIObject")
    public EIObject[] loadAllObjects();

    @Query("DELETE FROM EIObject")
    public void nukeTable();

}
