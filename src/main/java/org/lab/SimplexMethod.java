package org.lab;

import org.lab.service.PrintService;

import java.util.HashMap;
import java.util.Map;

public class SimplexMethod {
    private final PrintService printService = new PrintService();

    public Map<String, Double> doMethod(double[][] table, int unknown){
        int count = 1;
        while (!isOptimal(table) ){
            System.out.println("    Iteration # " + count++);
            final int MIN_COLUMN = findMinInObjectiveFunctionRow(table);
            final int PIVOT_RAW_INDEX = findPivotRowIndex(table, MIN_COLUMN);
            if(PIVOT_RAW_INDEX == -1){
                break;
            }
            printService.printTableWithPivotRawAndColumn(table, PIVOT_RAW_INDEX, MIN_COLUMN, unknown);
            countRaws(table, PIVOT_RAW_INDEX, MIN_COLUMN);
            System.out.println();
            printService.printTable(table, unknown);
        }
        int[] basis = findAllBasis(table);
        return getResult(table, basis, unknown);
    }

    private boolean isOptimal(double[][] table){
        double[] lastRow = table[table.length-1];
        for (double value: lastRow) {
            if(value < 0){
                return false;
            }
        }
        return true;
    }

    private int findMinInObjectiveFunctionRow(double[][] table){
        double[] lastRow = table[table.length-1];
        int minIndex = 0;
        for(int i = 0; i < lastRow.length; i++){
            if(lastRow[i] < lastRow[minIndex]){
                minIndex = i;
            }
        }
        return minIndex;
    }

    private int findPivotRowIndex(double[][] table, int minColumnIndex){
        int pivotRowIndex = -1;
        for(int i = 0; i < table.length - 1; i++){
            int rightSideValueIndex = table[i].length - 1;
            double rightSideValue = table[i][rightSideValueIndex] / table[i][minColumnIndex];
            if(pivotRowIndex >= 0){
                if(rightSideValue > 0 && rightSideValue < table[pivotRowIndex][rightSideValueIndex]/table[pivotRowIndex][minColumnIndex]){
                    pivotRowIndex = i;
                }
            }else {
                if(rightSideValue > 0){
                    pivotRowIndex = i;
                }
            }
        }
        return pivotRowIndex;
    }

    private void countRaws(double[][] table, int pivotRawIndex, int minColumnIndex){
        double pivotRawMultiplyIndex = 1 / table[pivotRawIndex][minColumnIndex];
        for(int k = 0; k < table[pivotRawIndex].length; k++){
            if(k != minColumnIndex){
                table[pivotRawIndex][k] *= pivotRawMultiplyIndex;
            }else{
                table[pivotRawIndex][k] = 1;
            }
        }
        for(int i = 0; i < table.length; i++){
            if(i != pivotRawIndex){
                double multiplyIndex = -(table[i][minColumnIndex] / table[pivotRawIndex][minColumnIndex]);
                for(int j = 0; j < table[i].length; j++){
                    double pivotRawValue = table[pivotRawIndex][j];
                    double currentRawValue = table[i][j];
                    table[i][j] = multiplyIndex * pivotRawValue + currentRawValue;
                }
            }
        }
    }

    private int[] findAllBasis(double[][] table){
        int numOfColumns = table[0].length;
        int[] basis = new int[table.length - 1];
        for (int i = 0; i < numOfColumns - 1; i++) {
            int countZeroValues = 0;
            int notNullIndex = -1;
            for (int j = 0; j < table.length; j++){
                if(table[j][i] == 0){
                    countZeroValues++;
                }else{
                    notNullIndex = j;
                }
            }
            if(countZeroValues == table.length - 1){
                basis[notNullIndex] = i;
            }
        }
        return basis;
    }

    private Map<String, Double> getResult(double[][] table, int[] basis, int unknown){
        Map<String, Double> result = new HashMap<>();
        int tableRawLength = table[0].length;
        for (int k = 0; k < tableRawLength - 1; k++){
            String valueName;
            if(k < unknown){
                valueName = "x" + (k + 1);
            } else {
                valueName = "y" + (k + 1 - unknown);
            }
            result.put(valueName, 0.);
        }
        for(int i = 0; i < basis.length; i++){
            String valueName = basis[i] < unknown ? "x" + (basis[i] + 1) : "y" + (basis[i] + 1 - unknown);
            double value = table[i][tableRawLength - 1] / table[i][basis[i]];
            result.put(valueName, value);
        }
        result.put("F", table[table.length - 1][tableRawLength - 1]);
        return result;
    }
}
