package com.matyas.krista.pylt.ToDo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by KristaM on 2018. 02. 21..
 */

public class Card implements Serializable {

    private static ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<String> tags = new ArrayList<>();
    private ArrayList<String> objects = new ArrayList<>();
    private String title;
    private String tag;

    public ArrayList<String> getItems() {
        return objects;
    }

    public void setItems(ArrayList<String> items) {
        this.objects = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public static ArrayList<String> getTags() {
        return tags;
    }

    public static void setTags(ArrayList<String> tags) {
        Card.tags = tags;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Card(String title) {
        this.title = title;
        if (!title.equals("Shopping List")) {cards.add(this);}
    }

    public void addItem(String item) {
        this.objects.add(item);
    }

    public void removeItem(String item) {
        this.objects.remove(item);
    }
}
