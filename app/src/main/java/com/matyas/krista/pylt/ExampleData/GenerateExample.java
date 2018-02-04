//package com.matyas.krista.pylt.ExampleData;
//
//import com.matyas.krista.pylt.EIObjects.EIObject;
//import com.matyas.krista.pylt.EIObjects.EIType;
//
///**
// * Created by Matyas on 2018.02.03..
// */
//
//public class GenerateExample {
//
//    private static String[] things = {"Cacao", "Milk", "Coffee", "Rent", "Cleaning", "Utilities", "Alcohol", "Tobacco", "Candy", "Entrance fee"};
//    private static String[] tags = {"Food expenses", "House expenses", "Leisure expenses"};
//
//    public static void generate() {
//        generateExpenses();
//    }
//
//    private static void generateExpenses() {
//        int count = 0;
//        for (int i = 0; i < 10; i++) {
//             new EIObject(things[i], EIType.EXPENSE, i*1000, tags[count]);
//             if (i % 3 == 0 && i != 0) {
//                 count += 1;
//             }
//        }
//        new EIObject("Work", EIType.INCOME, 14000, "Pay");
//        new EIObject("Parents gift", EIType.INCOME, 60000, "Parents");
//    }
//}
