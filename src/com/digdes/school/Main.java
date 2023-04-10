package com.digdes.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
            UserTable.processUserTable();

            JavaSchoolStarter starter = new JavaSchoolStarter();
            try {

                List<Map<String, Object>> result1 = starter.execute("INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true");
                List<Map<String, Object>> result2 = starter.execute("UPDATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3");
                List<Map<String, Object>> result3 = starter.execute("SELECT WHERE ‘age’>=30 and ‘lastName’ ilike ‘%п%’");
                List<Map<String, Object>> result4 = starter.execute("SELECT SHOW");
            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}