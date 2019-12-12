//I worked on the homework assignment alone, using only course materials.
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
/**
 * Main Histogram class
 * @author shakeeb shams
 * @version 69
 */
public class Histogram {
    /**
     * main method
     * @param args cmd line args
     */
    public static void main(String[] args) {
        int bins;
        String path;
        if (args.length == 0) {
            path = "BadFile.csv";
        } else {
            path = args[0];
        }
        Scanner scan = new Scanner(System.in);
        File file;
        while (true) {
            try {
                file = new File(path);
                if (file.isFile()) {
                    break;
                } else {
                    System.out.println("Please enter a valid csv file:");
                    path = scan.next();
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid csv file:");
                path = scan.next(); // skip the invalid token
                // continue; is not required
            }
        }
        if (args.length > 1) {
            bins = Integer.parseInt(args[1]);
        } else {
            System.out.println("How many bins would you like?");
            bins = scan.nextInt();
        }
        scan.close();
        histCreator(bins, file);


    }
    /**
     * hisotgram printer
     * @param bins the brand to be added to the Dispenser
     * @param file the volume to be added to the Dispenser
     */
    public static void histCreator(int bins, File file) {
        int increase = 100 / bins;
        int[] nums  = new int[bins];
        Scanner scan;
        try {
            scan = new Scanner(file);
            int[][] multiples = new int[bins][2];
            int start = 100;
            for (int i = 0; i < multiples.length; i++) {
                multiples[i][0] = start;
                multiples[i][1] = start - increase + 1;
                start = start - increase;
            }

            if (multiples[bins - 1][1] != 0) {
                multiples[bins - 1][1] = 0;
            }
            scan.useDelimiter(",");
            String data = scan.next();
            while (scan.hasNext()) {
                //read single line, put in string
                data = scan.next();
                //String[] bruh = data.split(",");
                String newString = data.trim();
                String[] lines = newString.split("\\r?\\n");
                int grade = Integer.parseInt(lines[0]);
                for (int i = 0; i < multiples.length; i++) {
                    if (grade >= multiples[i][1] && grade <= multiples[i][0]) {
                        nums[i]++;
                    }
                }

            }

            // after loop, close scanner
            scan.close();
            PrintWriter writer1 = new PrintWriter("GradeDistribution.txt");
            for (int i = 0; i < multiples.length; i++) {
                String one;
                if (multiples[i][0] < 100 && multiples[i][0] > 9) {
                    one = " " + Integer.toString(multiples[i][0]);
                } else if (multiples[i][0] < 10) {
                    one = "  " + Integer.toString(multiples[i][0]);
                } else {
                    one = Integer.toString(multiples[i][0]);
                }
                String two;
                if (multiples[i][1] < 10) {
                    two = " " + Integer.toString(multiples[i][1]);
                } else {
                    two = Integer.toString(multiples[i][1]);
                }


                String yuh = String.format("%s - %s |", one, two);

                String people = " ";
                for (int j = 0; j < nums[i]; j++) {
                    people += "[]";
                }
                yuh += people;

                yuh = yuh.concat("\n");
                writer1.print(yuh);
            }
            System.out.println("A GradeDistribution.txt file has successfully been created.");
            writer1.flush();
            writer1.close();
        } catch (FileNotFoundException e) {
            System.out.println("Bruh");
        }
    }
}