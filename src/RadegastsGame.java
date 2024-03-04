/**
 * This code reads the input file, increments an element of the matrix by one ten times and finds areas surrounded by bigger values with a recursive method
 * @author Hasan Arda Gunes  Student ID: 2021400003
 * @since Date: 29.04.2023
 */
import java.io.FileNotFoundException;
public class RadegastsGame {
    public static void main(String[] args) throws FileNotFoundException {
        Matrix matrix = new Matrix();
        matrix.readFile();
        matrix.printMatrix();
        matrix.addStone();
        matrix.printLastMatrix();
        System.out.printf("Final score: %.2f",matrix.findScore(matrix.flood()));
    }
}