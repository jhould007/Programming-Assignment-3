/* PROGRAMMING ASSIGNMENT 3, SPRING 2022. 
Author: Josh Houlding (W01358050).

This program takes in a grid of numbers representing a truffle field and each cell's respective amount of truffles, and finds the optimal path through the truffle field that maximizes the number of truffles collected.

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Truffle {
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File(args[0]);
        int numRows = countLines(f);
        int numColumns = countWidth(f);
        int[][] grid = new int[numRows][numColumns];

        // Populate the grid with values from the file.
        Scanner sc = new Scanner(f);
        int i = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] lineSplit = line.split("\\s+");
            for (int j = 0; j < numColumns; j++) {
                grid[i][j] = Integer.parseInt(lineSplit[j]);
            }
            i++;
        }

        System.out.println("Truffle field given:");
        print2dArray(grid);
        sc.close();

        // Run the algorithm.
        findPath(grid);
    }

    // The algorithm that finds an optimal path through the truffle field which
    // maximizes the yield.
    public static void findPath(int[][] grid) {
        // Find the optimal starting cell in the first row.
        int[] startingCell = findMax(grid[0]);
        int value = startingCell[0];
        // This index variable will be used to keep track of which column we are in at
        // any given time.
        int index = startingCell[1];
        System.out.println("We will start at the cell at index " + startingCell[1] + ", which has a value of "
                + startingCell[0] + ".");

        int row = 1;
        int totalYield = value;
        while (row < grid.length) {
            // If we are at the far left cell of the row, only consider cells down and
            // diagonally right.
            if (index == 0) {
                int[] possibleMoves = getSubarray(grid[row], 0, 1);
                int[] nextMove = findMax(possibleMoves);
                value = nextMove[0];
                index = nextMove[1];
                System.out.println("The next move to row " + (row + 1) + " is to the cell at index " + index
                        + " with a value of " + value + ".");
                totalYield += value;
            }

            // If we are at the far right cell of the row, only consider cells down and
            // diagonally left.
            if (index == grid[0].length - 1) {
                int[] possibleMoves = getSubarray(grid[row], index - 1, index);
                int[] nextMove = findMax(possibleMoves);
                value = nextMove[0];
                // If the next move is at index(index-1), update the index variable accordingly.
                if (value == possibleMoves[0]) {
                    index = index - 1;
                }
                System.out.println("The next move to row " + (row + 1) + " is to the cell at index " + index
                        + " with a value of " + value + ".");
                totalYield += value;
            }

            // If we are at a middle cell, consider cells diagonally left, down, and
            // diagonally right.
            else {
                int[] possibleMoves = getSubarray(grid[row], index - 1, index + 1);
                int[] nextMove = findMax(possibleMoves);
                value = nextMove[0];
                // If the next move is at index(index-1) or index(index+1), update the index
                // variable accordingly.
                if (value == possibleMoves[0]) {
                    index = index - 1;
                } else if (value == possibleMoves[2]) {
                    index = index + 1;
                }
                System.out.println("The next move to row " + (row + 1) + " is to the cell at index " + index
                        + " with a value of " + value + ".");
                totalYield += value;
            }

            row++;
        }
        System.out.println("The total truffle yield from this path is " + totalYield + ".");
    }

    // Counts the number of lines of a file.
    // This is to determine how many rows the 2d array needs.
    public static int countLines(File f) throws FileNotFoundException {
        Scanner sc = new Scanner(f);
        ArrayList<String> lines = new ArrayList<String>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        sc.close();
        // System.out.println("There are " + lines.size() + " lines in the file.");
        return lines.size();
    }

    // Counts the number of values in a single line of a file.
    // This is to determine how many columns the 2d array needs.
    public static int countWidth(File f) throws FileNotFoundException {
        Scanner sc = new Scanner(f);
        String line = sc.nextLine();
        String[] lineSplit = line.split("\\s+");
        int numColumns = lineSplit.length;
        sc.close();
        // System.out.println("There are " + numColumns + " values in a line of the
        // file.");
        return numColumns;
    }

    // Prints out a 2d array.
    public static void print2dArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Finds the max value in an array, as well as where it appears.
    // Returns a size 2 array, [max, index].
    public static int[] findMax(int[] arr) {
        int[] maxAndIndex = new int[2];
        int max = arr[0];
        int index = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] >= max) {
                max = arr[i];
                index = i;
            }
        }
        maxAndIndex[0] = max;
        maxAndIndex[1] = index;
        return maxAndIndex;
    }

    // Gets a specified subarray from an array (inclusive).
    public static int[] getSubarray(int[] arr, int begin, int end) {
        int[] subarray = new int[(end - begin) + 1];
        int index = 0;
        for (int i = begin; i <= end; i++) {
            subarray[index] = arr[i];
            index++;
        }
        return subarray;
    }
}