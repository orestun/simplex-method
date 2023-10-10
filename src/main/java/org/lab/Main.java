package org.lab;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        final SimplexMethod simplexMethod = new SimplexMethod();
        double [][] table = {
                {-1, 4, 1, 0, 0, 8},
                {-2, 1, 0, 1, 0, -7},
                {4, -3, 0, 0, 1, 0},
                {3, -2, 0, 0, 0, 0}};
        Map<String, Double> result = simplexMethod.doMethod(table, 2);
        System.out.println(result);
    }
}