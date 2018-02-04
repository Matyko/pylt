package com.matyas.krista.pylt.Calculator;

import com.matyas.krista.pylt.EIObjects.EIObject;
import com.matyas.krista.pylt.EIObjects.EIType;

import java.util.ArrayList;

/**
 * Created by Matyas on 2018.02.03..
 */

public class Calculator {

    public static long getBalance() {
        long income = 0;
        long expense = 0;
        ArrayList<EIObject> objects = EIObject.getAllObjects();
        for (EIObject object : objects) {
            if (object.getEitype().equals(EIType.INCOME)) {
                income += object.getAmount();
            } else if (object.getEitype().equals(EIType.EXPENSE)) {
                expense += object.getAmount();
            }
        }
        return income - expense;
    }

    public  static long getEI(EIType type) {
        long result = 0;
        ArrayList<EIObject> objects = EIObject.getAllObjects();
        for (EIObject object : objects) {
            if (object.getEitype().equals(type)) {
                result += object.getAmount();
            }
        }
        return result;
    }
}
