import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        ArrayList<int[]> a = new ArrayList<int[]>();
        int[] values = {1, 2, 3}; 
        a.add(values); 
        for(int i = 0; i < a.size(); i++) {
            System.out.println(Arrays.toString(a.get(i)));
        }
    }

    

    //Generates a grid of random numbers of a specified size
    public static void randomGrid(int height, int width) {
        Random r = new Random();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                System.out.print((r.nextInt(9)) + 1 + " ");
            }
            System.out.println();
        }
    }
}
