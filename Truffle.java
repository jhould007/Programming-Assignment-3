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

        // Populate the grid with values from the file
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

        System.out.println("Starting grid:");
        print2dArray(grid);
        sc.close();
    }

    // The algorithm that finds an optimal path through the truffle field which
    // maximizes the yield.
    public static void findPath(int[][] grid) {
        // Find the optimal starting cell in the first row

    }

    // Counts the number of lines of a file
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

    // Counts the number of values in a single line of a file
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
}