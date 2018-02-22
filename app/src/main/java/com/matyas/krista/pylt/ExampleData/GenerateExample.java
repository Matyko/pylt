package com.matyas.krista.pylt.ExampleData;

import com.matyas.krista.pylt.ToDo.Card;
import com.matyas.krista.pylt.ToDo.ShoppingList;

/**
 * Created by Matyas on 2018.02.03..
 */

public class GenerateExample {

    private static String[] things = {"Cacao", "Milk", "Coffee", "Rent", "Cleaning", "Utilities", "Alcohol", "Tobacco", "Candy", "Entrance fee"};
    private static String[] tags = {"Food expenses", "House expenses", "Leisure expenses"};

    public static void generate() {
        generateCards();
    }

    private static void generateCards() {
        Card card1 = new Card("Test");
        Card card2 = new Card("Test");
        card1.addItem("Test");
        card1.addItem("Test");
        card1.addItem("Test");
        card2.addItem("Test");
    }
}
