import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Matrix {
    private int row;
    private int height;
    private String[][] map;
    Matrix() {
    }

    /**
     * this method reads the input file and creates a 2d array
     * @throws FileNotFoundException
     */
    public void readFile() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scan = new Scanner(file);
        String[] line1 = scan.nextLine().split(" ");
        row = Integer.parseInt(line1[0]);
        height = Integer.parseInt(line1[1]);
        map = new String[height + 1][row + 1];
        // 2d array matrix
        for (int i = 0; i < height; i++){
            if (i<10)
                map[i][0] = "  " + (i) + "";
            else if (i<100)
                map[i][0] = " " + (i) + "";
            else
                map[i][0] = (i) + "";
        }
        map[height][0] = "   ";

        // makes the alphabetical notation at the bottom of the matrix
        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        for (int i = 0; i < row; i++) {
            int index = i / alphabet.length;
            int mod = i % alphabet.length;
            if (index == 0)
                map[height][i + 1] = alphabet[i];
            else {
                String pos = alphabet[index - 1] + alphabet[mod];
                map[height][i + 1] = pos;

            }
        }
        // puts the matrix in the input file into the 2d map array
        for (int i = 0; i < height; i++) {
            String[] tempArray = scan.nextLine().split(" ");
            System.arraycopy(tempArray, 0, map[i], 1, tempArray.length);
        }
        scan.close();
    }

    /**
     * prints the matrix
     */
    public void printMatrix() {
        // prints the matrix
        // if the next element is made of 2 numbers (bigger or equal to 10) put one space else two spaces
        for (int i = 0; i < height + 1; i++) {
            for (int j = 0; j < row + 1; j++) {
                if (i == height){
                    if (row<=26){
                        System.out.print(map[i][j]);
                        if (j != row)
                            System.out.print("  ");
                        else
                            System.out.print(" ");
                    }
                    // if row>26 some coordinates are labeled with 2 letters so just 1 space between them
                    else {
                        System.out.print(map[i][j]);
                        if (j<26)
                            System.out.print("  ");
                        else if (j<=row)
                            System.out.print(" ");
                    }
                }
                else {
                    System.out.print(map[i][j]);
                    if (j != row){
                        if (Integer.parseInt(map[i][j+1].strip()) < 10)
                            System.out.print("  ");
                        else
                            System.out.print(" ");
                    }
                    else
                        System.out.print(" ");

                }
            }
            System.out.println();
        }
    }

    /**
     * prints the last matrix where lakes are labeled with alphabetical notations
     */
    public void printLastMatrix() {
        ArrayList<ArrayList<String>> lakeGroups = flood();
        ArrayList<String> lakes = new ArrayList<>();
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        // put all the lake coordinates in a single array list
        for (ArrayList<String> lake : lakeGroups) {
            for (String i : lake) {
                lakes.add(i);
            }
        }
        for (int i = 0; i < height + 1; i++) {
            for (int j = 0; j < row + 1; j++) {
                if (i == height) {
                    if (row <= 26) {
                        System.out.print(map[i][j]);
                        if (j != row)
                            System.out.print("  ");
                        else
                            System.out.print(" ");
                    } else {
                        System.out.print(map[i][j]);
                        if (j < 26)
                            System.out.print("  ");
                        else if (j <= row)
                            System.out.print(" ");
                    }
                }
                else {
                    if (j<row){
                        // if it is not a lake print the integer value
                        // look att he next elements and put spaces according to it
                        if (!lakes.contains(i + " " + j)) {
                            System.out.print(map[i][j]);
                            if (!lakes.contains(i + " " + (j+1))){
                                if (Integer.parseInt(map[i][j+1]) < 10)
                                    System.out.print("  ");
                                else if (Integer.parseInt(map[i][j+1]) >= 10)
                                    System.out.print(" ");
                            }
                            else if (lakes.contains(i + " " + (j+1))){
                                int lakeIndex = 0;
                                for (int x = 0; x<lakeGroups.size();x++){
                                    if (lakeGroups.get(x).contains(i + " " +(j+1)))
                                        lakeIndex = x;
                                }
                                if (lakeIndex <= 25)
                                    System.out.print("  ");
                                else
                                    System.out.print(" ");
                            }
                        }
                        else {
                            // find which letters should represent the lake
                            int lakeIndex = 0;
                            for (int x = 0; x<lakeGroups.size();x++){
                                if (lakeGroups.get(x).contains(i + " " +j))
                                    lakeIndex = x;
                            }
                            int index = lakeIndex/ alphabet.length;
                            int mod = lakeIndex% alphabet.length;
                            if (lakeIndex <= 25)
                                System.out.print(alphabet[lakeIndex]);
                            else {
                                System.out.print(alphabet[index-1] + alphabet[mod]);
                            }
                            if (!lakes.contains(i + " " + (j+1))){
                                if (Integer.parseInt(map[i][j+1]) < 10)
                                    System.out.print("  ");
                                else if (Integer.parseInt(map[i][j+1]) >= 10)
                                    System.out.print(" ");
                            }
                            else {
                                int lakeIndex2 = 0;
                                for (int x = 0; x<lakeGroups.size();x++){
                                    if (lakeGroups.get(x).contains(i + " " +(j+1)))
                                        lakeIndex2 = x;
                                }
                                if (lakeIndex2 <= 25)
                                    System.out.print("  ");
                                else
                                    System.out.print(" ");
                            }
                        }
                    }
                    else if (j==row){
                        System.out.print(map[i][j] + " ");
                    }

                    }
                }
            System.out.println();
            }
        }

    /**
     * increment the integer values by 1 on the desired locations
     */
    public void addStone() {
        ArrayList<String> allCoordinates = new ArrayList<>();
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<String> letters = new ArrayList<>();
        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        for (int a = 0; a < row; a++) {
            int index = a / alphabet.length;
            int mod = a % alphabet.length;
            if (index == 0)
                letters.add(alphabet[a]);
            else {
                String pos = alphabet[index - 1] + alphabet[mod];
                letters.add(pos);
            }
        }
        for (int j = 0; j < height; j++)
            numbers.add(j+"");
        for (String let:letters){
            for (String num:numbers)
                allCoordinates.add(let+num);
        }
        int i = 1;
        Scanner scan = new Scanner(System.in);
        while (i<=10){
            System.out.print("Add stone " + i + " / 10 to coordinate:");
            String input = scan.nextLine().strip();
            // if the input is not a valid coordinate print not valid and ask again
            if (!allCoordinates.contains(input)){
                System.out.println("Not a valid step!");
                continue;
            }
            // find the indexes of the inputs
            int rowIndex = 0;
            int heightIndex = 0;
            for (int a = 0; a< letters.size();a++){
                for (int b = 0; b< numbers.size();b++){
                    if ((letters.get(a) + numbers.get(b)).equals(input)){
                        rowIndex = a+1;
                        heightIndex = b;
                    }
                }
            }
            // increase the value by 1 and put it into the matrix
            int temp = Integer.parseInt(map[heightIndex][rowIndex]);
            temp+=1;
            map[heightIndex][rowIndex] = temp+"";
            printMatrix();
            System.out.println("---------------");
            i+=1;
        }
    }

    /**
     *
     * @return all the group of lakes
     */
    public ArrayList<ArrayList<String>> flood(){
        ArrayList<ArrayList<String>> returnArrayList = new ArrayList<>();

        for (int i = 1; i<height-1;i++){
            for (int j = 2; j<row; j++){
                boolean[][] table = makeTable();
                int value = Integer.parseInt(map[i][j].strip());
                boolean isIn = false;
                for (ArrayList<String> el: returnArrayList){
                    if (el.contains(i + " " + j))
                        isIn = true;
                }
                if (!isIn && isLake(i,j,value,table)){
                    returnArrayList.add(uniteLakes(i,j,new ArrayList<>()));
                }
            }
        }
        return returnArrayList;
    }

    /**
     *
     * @param heightIndex the height index we are currently in
     * @param rowIndex the row index we are currently in
     * @param value the integer value of the location we are checking
     * @param table 2d array it is used for not repeating the steps
     * @return if the location is a lake return true, else false
     */
    public boolean isLake(int heightIndex, int rowIndex,int value,boolean[][] table) {
        if (rowIndex == row || heightIndex == height - 1 || rowIndex == 1 || heightIndex == 0)
            return false;
        table[heightIndex][rowIndex] = false;
        // if the height of the locations are the same or lower than the value then the liquid will flow that way
        // if the liquid flows out of the matrix then it is not a lake

        if (value >= Integer.parseInt(map[heightIndex + 1][rowIndex].strip()) && table[heightIndex+1][rowIndex]) {
            if (!isLake(heightIndex+1, rowIndex,value,table))
                return false;
        }
        if (value >= Integer.parseInt(map[heightIndex - 1][rowIndex].strip()) && table[heightIndex-1][rowIndex]) {
            if (!isLake(heightIndex-1, rowIndex, value,table))
                return false;
        }
        if (value >= Integer.parseInt(map[heightIndex][rowIndex + 1].strip()) && table[heightIndex][rowIndex+1]) {
            if (!isLake(heightIndex, rowIndex+1,value,table))
                return false;
        }
        if (value >= Integer.parseInt(map[heightIndex][rowIndex - 1].strip()) && table[heightIndex][rowIndex-1]) {
            if (!isLake(heightIndex, rowIndex-1,value,table))
                return false;
        }
        if (value >= Integer.parseInt(map[heightIndex + 1][rowIndex + 1].strip()) && table[heightIndex+1][rowIndex+1]) {
            if (!isLake(heightIndex+1, rowIndex+1,value,table))
                return false;
        }
        if (value >= Integer.parseInt(map[heightIndex + 1][rowIndex - 1].strip()) && table[heightIndex+1][rowIndex-1]) {
            if (!isLake(heightIndex+1, rowIndex-1,value,table))
                return false;
        }
        if (value >= Integer.parseInt(map[heightIndex-1][rowIndex+1].strip()) && table[heightIndex-1][rowIndex+1]){
            if (!isLake(heightIndex-1, rowIndex+1,value,table))
                return false;
        }
        if (value >= Integer.parseInt(map[heightIndex-1][rowIndex-1].strip()) && table[heightIndex-1][rowIndex-1]){
            if (!isLake(heightIndex-1, rowIndex-1,value,table))
                return false;
        }
        return true;
    }

    /**
     * this recursive method unites all the coordinates that makes up the lake
     * @param heightIndex the height index we are currently in
     * @param rowIndex the row index we are currently in
     * @param lake lake coordinates
     * @return
     */

    public ArrayList<String> uniteLakes(int heightIndex, int rowIndex, ArrayList<String> lake){
        boolean[][] table2 = makeTable();

        if (isLake(heightIndex,rowIndex,Integer.parseInt(map[heightIndex][rowIndex].strip()),table2)){
            if(!lake.contains(heightIndex + " " + rowIndex))
                lake.add(heightIndex + " " + rowIndex);
            else
                return lake;
        }
        else
            return lake;
        uniteLakes(heightIndex+1,rowIndex,lake);
        uniteLakes(heightIndex-1,rowIndex,lake);
        uniteLakes(heightIndex,rowIndex+1,lake);
        uniteLakes(heightIndex,rowIndex-1,lake);
        uniteLakes(heightIndex+1,rowIndex+1,lake);
        uniteLakes(heightIndex-1,rowIndex+1,lake);
        uniteLakes(heightIndex+1,rowIndex-1,lake);
        uniteLakes(heightIndex-1,rowIndex-1,lake);
        return lake;
    }

    /**
     * finds the minimum value around a lake that is bigger than all the lake's values
     * @param lake coordinates of a lake
     * @return minimum value bigger than lake's values
     */
    public int findMin(ArrayList<String> lake){
        int min = Integer.MAX_VALUE;
        for (String el : lake){
            String[] tempArray = el.split(" ");
            int heightIndex = Integer.parseInt(tempArray[0]);
            int rowIndex = Integer.parseInt(tempArray[1]);
            boolean[][] table = makeTable();
            int value = Integer.parseInt(map[heightIndex+1][rowIndex].strip());
            if (!isLake(heightIndex+1,rowIndex,value,table) && min>Integer.parseInt(map[heightIndex+1][rowIndex].strip()))
                min = Integer.parseInt(map[heightIndex+1][rowIndex].strip());
            table = makeTable();
            value = Integer.parseInt(map[heightIndex-1][rowIndex].strip());
            if (!isLake(heightIndex-1,rowIndex,value,table) && min>Integer.parseInt(map[heightIndex-1][rowIndex].strip()))
                min = Integer.parseInt(map[heightIndex-1][rowIndex].strip());
            table = makeTable();
            value = Integer.parseInt(map[heightIndex][rowIndex+1].strip());
            if (!isLake(heightIndex,rowIndex+1,value,table) && min>Integer.parseInt(map[heightIndex][rowIndex+1].strip()))
                min = Integer.parseInt(map[heightIndex][rowIndex+1].strip());
            table = makeTable();
            value = Integer.parseInt(map[heightIndex][rowIndex-1].strip());
            if (!isLake(heightIndex,rowIndex-1,value,table) && min>Integer.parseInt(map[heightIndex][rowIndex-1].strip()))
                min = Integer.parseInt(map[heightIndex][rowIndex-1].strip());
            table = makeTable();
            value = Integer.parseInt(map[heightIndex+1][rowIndex+1].strip());
            if (!isLake(heightIndex+1,rowIndex+1,value,table) && min>Integer.parseInt(map[heightIndex+1][rowIndex+1].strip()))
                min = Integer.parseInt(map[heightIndex+1][rowIndex+1].strip());
            table = makeTable();
            value = Integer.parseInt(map[heightIndex+1][rowIndex-1].strip());
            if (!isLake(heightIndex+1,rowIndex-1,value,table) && min>Integer.parseInt(map[heightIndex+1][rowIndex-1].strip()))
                min = Integer.parseInt(map[heightIndex+1][rowIndex-1].strip());
            table = makeTable();
            value = Integer.parseInt(map[heightIndex-1][rowIndex-1].strip());
            if (!isLake(heightIndex-1,rowIndex-1,value,table) && min>Integer.parseInt(map[heightIndex-1][rowIndex-1].strip()))
                min = Integer.parseInt(map[heightIndex-1][rowIndex-1].strip());
            table = makeTable();
            value = Integer.parseInt(map[heightIndex-1][rowIndex+1].strip());
            if (!isLake(heightIndex-1,rowIndex+1,value,table) && min>Integer.parseInt(map[heightIndex-1][rowIndex+1].strip()))
                min = Integer.parseInt(map[heightIndex-1][rowIndex+1].strip());
        }
        return min;
    }

    /**
     * calculates the final score by finding the sums of min-value
     * @param lakeGroups group of lakes
     * @return final score
     */
    public double findScore(ArrayList<ArrayList<String>> lakeGroups){
        double sum = 0;
        for (ArrayList<String> lake : lakeGroups){
            int tempSum = 0;
            int min = findMin(lake);
            for (String i : lake){
                String[] tempArray = i.split(" ");
                int heightIndex = Integer.parseInt(tempArray[0]);
                int rowIndex = Integer.parseInt(tempArray[1]);
                int value = Integer.parseInt(map[heightIndex][rowIndex].strip());
                tempSum += min-value;
            }
            sum += Math.sqrt(tempSum);
        }
        return sum;
    }

    /**
     * makes a 2d boolean array
     * @return 2d boolean array
     */
    public boolean[][] makeTable(){
        boolean[][] table = new boolean[height+1][row+1];
        for (int a = 0; a< height+1;a++){
            for (int b = 0; b< row+1; b++){
                table[a][b] = true;
            }
        }
        return table;

    }
}
