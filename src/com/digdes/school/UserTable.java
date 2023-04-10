package com.digdes.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTable {
    public static void processUserTable() {
        Map<String, Object> row1 = new HashMap<>();
        row1.put("id", 1);
        row1.put("lastName", "Петров");
        row1.put("age", 30);
        row1.put("cost", 5.4);
        row1.put("active", true);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("id", 2);
        row2.put("lastName", "Иванов");
        row2.put("age", 25);
        row2.put("cost", 4.3);
        row2.put("active", false);

        // Создание списка записей
        List<Map<String, Object>> data = new ArrayList<>();
        data.add(row1);
        data.add(row2);


        Map<String,Object> row3 = new HashMap<>();
        row3.put("id",3);
        row3.put("lastName","Федоров");
        row3.put("age",40);
        row3.put("active", true);

        data.add(row3);

        // Доступ к значениям в записях
        for (Map<String, Object> row : data) {
            int id = (int) row.get("id");
            String lastName = (String) row.get("lastName");
            int age = (int) row.get("age");
            Double cost = (Double) row.get("cost");
            boolean active = (boolean) row.get("active");

            // Использование значений
            System.out.println("ID: " + id);
            System.out.println("Last Name: " + lastName);
            System.out.println("Age: " + age);
            System.out.println("Cost: " + (cost != null ? cost : null));
            System.out.println("Active: " + active);
            System.out.println("-----");
        }

    }
}
