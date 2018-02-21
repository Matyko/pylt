package com.matyas.krista.pylt.ToDo;

import com.matyas.krista.pylt.EIObjects.EIObject;
import com.matyas.krista.pylt.EIObjects.EITag;
import com.matyas.krista.pylt.EIObjects.EIType;

import java.util.ArrayList;

/**
 * Created by KristaM on 2018. 02. 21..
 */

public class ShoppingList extends Card {

    private String title;
    private ArrayList<EIObject> items = new ArrayList<>();

    public ShoppingList(String title) {
        super(title);
        this.title = title;
    }

    public void addItem(String item, long value, String tag) {
        EITag.addTag(item, value, EIType.EXPENSE);
        this.items.add(new EIObject(item, EIType.EXPENSE, value, EITag.getTag(tag)));
    }

    public void removeItem(String itemName) {
        for (EIObject object : items) {
            if (object.getName().equals(itemName));
            items.remove(object);
            EIObject.getAllObjects().remove(object);
        }
    }
}
