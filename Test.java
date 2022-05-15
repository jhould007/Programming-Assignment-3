import java.util.Arrays;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        randomGrid(5, 5);
    }

    //Gets a specified subarray from an array (inclusive)
    public static int[] getSubarray(int[] arr, int begin, int end) {
        int[] subarray = new int[(end - begin) + 1];
        int index = 0; 
        for(int i = begin; i <= end; i++) {
            subarray[index] = arr[i]; 
            index++;
        }
        return subarray; 
    }

    //Generates a grid of random numbers of a specified size
    public static void randomGrid(int height, int width) {
        Random r = new Random();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < height; j++) {
                System.out.print((r.nextInt(9)) + 1 + " ");
            }
            System.out.println();
        }
    }
}
