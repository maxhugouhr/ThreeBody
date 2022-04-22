import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

//FileRunner class takes the positions of the bodies as they updates and stores
// them in a file so they can be drawn by the Video class at a later time
public class FileRunner {

    private String fileName;

    FileRunner(){
        super();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a file name for storage:");
        this.fileName = input.nextLine();
        try {
            File nf = new File(this.fileName);
            if (nf.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        }
    }

    void writeToFile(BlockingQueue<ArrayList<Double>> positionQueue){
        ArrayList<Double> line = null;
        try{
            line =  positionQueue.poll(1,TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        try{
            FileWriter writer = new FileWriter(this.fileName);
            BufferedWriter bw = new BufferedWriter(writer);
            while (line != null) {
                for (Double aDouble : line) {
                    bw.write(String.valueOf(aDouble + ','));
                }
                bw.newLine();
                try {
                    line = positionQueue.poll(1,TimeUnit.SECONDS);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
