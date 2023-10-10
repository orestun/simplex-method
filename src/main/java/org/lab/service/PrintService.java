package org.lab.service;

public class PrintService {
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public void printTable(double[][] table, int unknown){
        printBlueLineBetweenRows(table);
        printMarks(table[0].length, unknown);
        printBlueLineBetweenRows(table);
        for (int i = 0; i < table.length; i++) {
            if(i == table.length - 1){
                printBlueLineBetweenRows(table);
            }
            for (int j = 0; j < table[i].length; j++) {
                System.out.printf("| %10.2f ", table[i][j]);
            }
            System.out.println("|");
        }
        printLineBetweenRows(table);
    }

    public void printTableWithPivotRawAndColumn(double[][] table, int pivotRawIndex, int minColumnIndex, int unknown){
        printBlueLineBetweenRows(table);
        printMarks(table[0].length, unknown);
        printBlueLineBetweenRows(table);
        for (int i = 0; i < table.length; i++) {
            if(i == table.length - 1){
                printBlueLineBetweenRows(table);
            }
            for (int j = 0; j < table[i].length; j++) {
                System.out.print("|");
                if(i == pivotRawIndex || j == minColumnIndex){
                    System.out.printf(ANSI_RED+" %10.2f "+ANSI_BLACK, table[i][j]);
                }else{
                    System.out.printf(" %10.2f ", table[i][j]);
                }

            }
            System.out.println("|");
        }
        printLineBetweenRows(table);
    }

    private void printLineBetweenRows(double[][] table){
        for (int k = 0; k < table[0].length; k++){
            System.out.print("+------------");
        }
        System.out.println("+");
    }

    private void printBlueLineBetweenRows(double[][] table){
        for (int k = 0; k < table[0].length; k++){
            System.out.print(ANSI_YELLOW+"+------------");
        }
        System.out.println("+"+ANSI_BLACK);
    }

    private void printMarks(int columns, int unknown){
        for (int j = 0; j < columns; j++) {
            System.out.print(ANSI_YELLOW);
            if(j < unknown){
                System.out.printf("| %10s ", "X"+(j+1));
            }else if(j != columns - 1){
                System.out.printf("| %10s ", "Y"+(j+1-unknown));
            }else{
                System.out.printf("| %10s ", "bi");
            }
        }
        System.out.println("|"+ANSI_BLACK);
    }

    public static void main(String[] args) {
        double [][] table = {
                {4, 1, 1, 0, 8},
                {-1, 1, 0, 1, 3},
                {-3, -4, 0, 0, 0}};
        PrintService ps = new PrintService();
        ps.printTable(table, 2);
        System.out.println();
        ps.printTable(table, 2);
        System.out.println();
        ps.printTableWithPivotRawAndColumn(table, 1, 1, 2);
    }
}
