import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//FileRunner class takes the positions of the bodies as they updates and stores
// them in a file so they can be drawn by the Video class at a later time
public class FileRunner {

    private BlockingQueue<double[]> positionQueue;
    private File myFile;

    FileRunner(BlockingQueue<double[]> q){
        super();
        this.positionQueue = q;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a file name for storage:");
        String filename = input.nextLine();
        try {
            this.myFile = new File(filename);
            if (myFile.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        }
    }

}
