/* PROGRAMMING ASSIGNMENT 3, SPRING 2022. 
Author: Josh Houlding (W01358050).

This program takes in a grid of numbers representing a truffle field and each cell's respective amount of truffles, and finds the optimal path through the truffle field that maximizes the number of truffles collected.

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
        for (int k = 0; k < numRows; k++) {
            String line = sc.nextLine();
            String[] lineSplit = line.split("\\s+");
            for (int j = 0; j < numColumns; j++) {
                grid[i][j] = Integer.parseInt(lineSplit[j]);
            }
            i++;
        }

        System.out.println();
        System.out.println("Truffle field given:");
        print2dArray(grid);
        sc.close();

        // Run the algorithm.
        findPath(grid);
    }

    // The algorithm that finds an optimal path through the truffle field which
    // maximizes the yield.
    public static void findPath(int[][] grid) {

        // Account for a grid with only one column
        if (grid[0].length == 1) {
            int totalYield = 0;
            for (int i = 1; i < grid.length; i++) {
                totalYield += grid[i][0];
                System.out.println("Moved to the next cell down at row " + i + " with a value of " + grid[i][0] + ".");
            }
            System.out.println("The total truffle yield is " + totalYield + ".");
        }

        else {

            // Keep track of all the possible paths, the best yield of any path so far, and
            // which path had it.
            ArrayList<ArrayList<int[]>> paths = new ArrayList<ArrayList<int[]>>();
            int maxYield = 0;
            int indexOfBestYield = 0;

            // Go through every possible starting cell, testing the greedy path from each
            // and finding the one with the best yield.
            for (int i = 0; i < grid[0].length; i++) {
                ArrayList<int[]> path = new ArrayList<int[]>();
                int[] startingCell = { grid[0][i], i };
                path.add(startingCell);
                int value = startingCell[0];
                int column = startingCell[1];
                int totalYield = value;
                int row = 1;

                // System.out.println("Testing starting cell at index " + i + " with value " +
                // value + ".");
                while (row < grid.length) {
                    // If we are at the far left cell of the row, only consider cells down and
                    // diagonally right.
                    if (column == 0) {
                        int[] possibleMoves = getSubarray(grid[row], 0, 1);
                        int[] nextMove = findMax(possibleMoves);
                        value = nextMove[0];
                        // If the next move is diagonally right at index(index+1), update the index
                        // variable accordingly.
                        if (value == possibleMoves[1]) {
                            column = column + 1;
                        }
                        /*
                         * System.out.println("The next move to row " + (row + 1) +
                         * " is to the cell at index " + index
                         * + " with a value of " + value + ".");
                         */
                        nextMove[1] = column;
                        path.add(nextMove);
                        totalYield += value;
                    }

                    // If we are at the far right cell of the row, only consider cells down and
                    // diagonally left.
                    else if (column == grid[0].length - 1) {
                        int[] possibleMoves = getSubarray(grid[row], column - 1, column);
                        int[] nextMove = findMax(possibleMoves);
                        value = nextMove[0];
                        // If the next move is diagonally left at index(index-1), update the index
                        // variable accordingly.
                        if (value == possibleMoves[0]) {
                            column = column - 1;
                        }
                        /*
                         * System.out.println("The next move to row " + (row + 1) +
                         * " is to the cell at index " + index
                         * + " with a value of " + value + ".");
                         */
                        nextMove[1] = column;
                        path.add(nextMove);
                        totalYield += value;
                    }

                    // If we are at a middle cell, consider cells diagonally left, down, and
                    // diagonally right.
                    else {
                        int[] possibleMoves = getSubarray(grid[row], column - 1, column + 1);
                        int[] nextMove = findMax(possibleMoves);
                        value = nextMove[0];
                        // If the next move is at index(index-1) or index(index+1), update the index
                        // variable accordingly.
                        if (value == possibleMoves[0]) {
                            column = column - 1;
                        } else if (value == possibleMoves[2]) {
                            column = column + 1;
                        }
                        /*
                         * System.out.println("The next move to row " + (row + 1) +
                         * " is to the cell at index " + index
                         * + " with a value of " + value + ".");
                         */
                        nextMove[1] = column;
                        path.add(nextMove);
                        totalYield += value;
                    }
                    row++;
                }
                // printArrayList(path);
                // System.out.println("The total truffle yield from this path is " + totalYield
                // + ".");

                // If this most recent path is better than the previous ones, record it as the
                // best path so far.
                if (totalYield > maxYield) {
                    maxYield = totalYield;
                    indexOfBestYield = i;
                }
                paths.add(path);
                row = 0;
            }
            System.out.println();
            System.out.println(
                    "Each entry in the following optimal path is in the form [value, column], where value is the value of the cell being moved to, and column is the index of the column that cell is in. e.g. [1, 2] would mean a cell with value 1 in the third column.");
            // printAllPaths(paths);
            System.out.println("The largest yield is " + maxYield + ", from path " + (indexOfBestYield + 1)
                    + ". This path is: " + printArrayList(paths.get(indexOfBestYield)));
        }
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
            if (arr[i] > max) {
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

    // Return a string representation of an arraylist of arrays
    public static String printArrayList(ArrayList<int[]> a) {
        String s = "";
        for (int i = 0; i < a.size(); i++) {
            s += ("Row " + (i + 1) + ": " + Arrays.toString(a.get(i)) + ". ");
        }
        return s;
    }

    // Print out an arraylist of arraylists of arrays
    public static void printAllPaths(ArrayList<ArrayList<int[]>> a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.print("Path " + (i + 1) + ": ");
            for (int j = 0; j < a.get(i).size(); j++) {
                System.out.print(Arrays.toString(a.get(i).get(j)));
            }
            System.out.println();
        }
    }
}