import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CoreCode {
    public static void main(String[] args) {
        File myFile = new File("src/resources/funny.txt");
        try {
            Scanner reader = new Scanner(myFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File newFile = new File("src/resources/newFile.txt");
        try {
            newFile.createNewFile();
            BufferedWriter br = new BufferedWriter(new FileWriter(newFile,true));
            br.write("This is a new Line");
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
