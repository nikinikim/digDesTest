package com.digdes.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JavaSchoolStarter {
    private List<Map<String, Object>> collection;

    public JavaSchoolStarter() {
        collection = new ArrayList<>();
    }

    public List<Map<String,Object>> execute (String request) throws Exception {

        String[] tokens = request.split("\\s+");
        String command = tokens[0].toLowerCase();
        List<Map<String, Object>> result = new ArrayList<>();

        switch(command) {
            case "insert":
                Map<String, Object> newRow = new HashMap<>();
                for (int i = 2; i < tokens.length; i += 2) {
                    if (i + 1 < tokens.length) {
                        String columnName = tokens[i].replace("'", "");
                        String valueString = tokens[i + 1].replace(",", "").replace("'", "");
                        Object value;
                        if (valueString.equalsIgnoreCase("null")) {
                            value = null;
                        } else if (isNumber(valueString)) {
                            if (valueString.contains(".")) {
                                value = Double.parseDouble(valueString);
                            } else {
                                value = Long.parseLong(valueString);
                            }
                        } else if (isBoolean(valueString)) {
                            value = Boolean.parseBoolean(valueString);
                        } else {
                            value = valueString;
                        }
                        newRow.put(columnName, value);
                    }
                }
                collection.add(newRow);
                result.add(newRow);
                break;
            case "update":
                String columnName = tokens[2].replace("'", "");
                Object value;
                String valueString = tokens[4].replace(",", "").replace("'", "");
                if (valueString.equalsIgnoreCase("null")){
                    value = null;
                } else if (isNumber(valueString)) {
                    if (valueString.contains(".")) {
                        value = Double.parseDouble(valueString);
                    }else{
                        value = Long.parseLong(valueString);
                    }
                }else if (isBoolean(valueString)){
                    value = Boolean.parseBoolean(valueString);
                }else {
                    value = valueString;
                }
                for (Map<String, Object> row : collection) {
                    if (row.containsKey(columnName)){
                        row.put(columnName, value);
                        result.add(row);
                    }
                }
                break;
            case "select":
                String condition = tokens[1].toLowerCase();
                columnName = tokens[3].replace("'", "");
                String operator = tokens[4];
                valueString = tokens[5].replace(",", "").replace("'", "");
                Object conditionValue;
                if (isNumber(valueString)) {
                    if(valueString.contains(".")) {
                        conditionValue = Double.parseDouble(valueString);
                    }else{
                        conditionValue = Long.parseLong(valueString);
                    }
                }else if (isBoolean(valueString)){
                    conditionValue = Boolean.parseBoolean(valueString);
                }else{
                    conditionValue = valueString;
                }
                for (Map<String, Object> row : collection) {
                    if (row.containsKey(columnName)) {
                        Object rowValue = row.get(columnName);
                        if (isConditionMet(rowValue, operator, conditionValue)){
                            result.add(row);
                        }
                    }
                }
                break;
            case "delete":
                columnName = tokens[2].replace("'", "");
                valueString = tokens[4].replace(",", "").replace("'", "");
                conditionValue = null;
                if (valueString.equalsIgnoreCase("null")) {
                    conditionValue = null;
                }else if (isNumber(valueString)){
                    if (valueString.contains(".")) {
                        conditionValue = Double.parseDouble(valueString);
                    }else{
                        conditionValue = Long.parseLong(valueString);
                    }
                }else if (isBoolean(valueString)) {
                    conditionValue = Boolean.parseBoolean(valueString);
                }else{
                    conditionValue = valueString;
                }
                operator = tokens[3];
                List<Map<String, Object>> removedRows = new ArrayList<>();
                for (int i = collection.size() - 1; i >= 0; i--) {
                    Map<String, Object> row = collection.get(i);
                    if (row.containsKey(columnName)) {
                        Object rowValue = row.get(columnName);
                        if (isConditionMet (rowValue, operator, conditionValue)) {
                            removedRows.add(row);
                            collection.remove(i);
                        }
                    }
                }
                result.addAll(removedRows);
                break;
            case "show":
                result.addAll(collection);
                break;
            default:
                throw new Exception("Unknown command: " + command);
        }
        return result;
    }

    private boolean isNumber(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    private boolean isConditionMet(Object value1, String operator, Object value2) {
        switch (operator.toLowerCase()) {
            case "=":
                return value1.equals(value2);
            case "<>":
                return !value1.equals(value2);
            case ">":
                return compareValues(value1, value2) > 0;
            case "<":
                return compareValues(value1, value2) < 0;
            case ">=":
                return compareValues(value1, value2) >= 0;
            case "<=":
                return compareValues(value1, value2) <= 0;
            default:
                return false;
        }
    }

    private int compareValues(Object value1, Object value2) {
        if (value1 instanceof Comparable && value2 instanceof Comparable) {
            Comparable comp1 = (Comparable) value1;
            Comparable comp2 = (Comparable) value2;
            return comp1.compareTo(comp2);
        }else{
            throw new IllegalArgumentException("Values aren't comparable");

        }
    }
}
