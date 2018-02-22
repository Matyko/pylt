package com.matyas.krista.pylt.ToDo;

import com.matyas.krista.pylt.EIObjects.EIObject;
import com.matyas.krista.pylt.EIObjects.EITag;
import com.matyas.krista.pylt.EIObjects.EIType;

import java.util.ArrayList;

/**
 * Created by KristaM on 2018. 02. 21..
 */

public class ShoppingList {

    private static ArrayList<EIObject> objects = new ArrayList<>();



    private static ArrayList<String> items = new ArrayList<>();
    private static Card card = new Card("Shopping List");

    public static Card getShopCard() {
        card.setItems(getShopItems());
        return card;
    }

    public static ArrayList<String> getShopItems() {
        return items;
    }

    public static ArrayList<EIObject> getObjects() {
        return objects;
    }
}
