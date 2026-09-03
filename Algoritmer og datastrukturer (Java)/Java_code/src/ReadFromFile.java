import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFromFile {

    public static ArrayList<Wine> readFile() {
        String whiteWineFile = "src/data/wine+quality/winequality-white.csv";
        String redWineFile = "src/data/wine+quality/winequality-red.csv";

        ArrayList<Wine> wines = new ArrayList<>();

        try {
            readSingleFile(whiteWineFile, wines, WineType.WHITE);
            readSingleFile(redWineFile, wines, WineType.RED);

        } catch (Exception e) {
            System.err.println("Error while reading wine files: " + e.getMessage());
        }

        return wines;
    }

    private static void readSingleFile(String filePath, ArrayList<Wine> wines, WineType type) {
        try {
            Scanner scanner = new Scanner(new File(filePath));

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                String[] values = line.split(";");

                Wine wine = new Wine(
                        Double.parseDouble(values[0]),
                        Double.parseDouble(values[1]),
                        Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]),
                        Double.parseDouble(values[4]),
                        Double.parseDouble(values[5]),
                        Double.parseDouble(values[6]),
                        Double.parseDouble(values[7]),
                        Double.parseDouble(values[8]),
                        Double.parseDouble(values[9]),
                        Double.parseDouble(values[10]),
                        Integer.parseInt(values[11]),
                        type
                );

                wines.add(wine);
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println("Error while reading from file: " + filePath);
            System.err.println(e.getMessage());
        }
    }
}