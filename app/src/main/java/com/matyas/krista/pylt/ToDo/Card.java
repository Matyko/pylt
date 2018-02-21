package com.matyas.krista.pylt.ToDo;

import java.util.ArrayList;

/**
 * Created by KristaM on 2018. 02. 21..
 */

public class Card {

    private static ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<String> tags = new ArrayList<>();
    private ArrayList<String> items = new ArrayList<>();
    private String title;
    private String tag;

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
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
        cards.add(this);
    }

    public void addItem(String item) {
        this.items.add(item);
    }

    public void removeItem(String item) {
        this.items.remove(item);
    }
}
