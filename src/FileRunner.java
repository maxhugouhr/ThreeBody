import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//FileRunner class takes the positions of the bodies as they update and stores
// them in a file, so they can be drawn by the Video class at a later time
public class FileRunner {

    private String fileName;

    FileRunner(){
        super();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a file name for storage (including .txt):");
        this.fileName = "src/resources/" + input.nextLine();
        try {
            File nf = new File(this.fileName);
            while (!this.fileName.contains(".txt") || !nf.createNewFile()) {
                    System.out.println("File already exists or format not followed, enter new name");
                    this.fileName = input.nextLine();
                    nf = new File(fileName);
            }
            System.out.println("File Created");
        } catch (IOException | StringIndexOutOfBoundsException e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        }
    }

    // writeToFile intakes the queue that is being built by the simulation and writes the array
    // of bodies to the output file in a serialized format
    void writeToFile(BlockingQueue<ArrayList<Body>> positionQueue){
        ArrayList<Body> line = null; //list of body positions at a given timestamp
        try{
            line =  positionQueue.poll(5,TimeUnit.SECONDS); //runs until the simulation is complete
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        try{
            FileOutputStream writer = new FileOutputStream(this.fileName, true);
            ObjectOutputStream os = new ObjectOutputStream(writer);
            while (line != null) {
                os.writeObject(line);
                try {
                    line = positionQueue.poll(5,TimeUnit.SECONDS);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            os.flush();
            os.close();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getFileName() {
        return this.fileName;
    }

    String stringer(ArrayList<Body> bodies) {
    //takes an array of bodies and outputs a string that contains the position and size of each body
    //in a format that can be then written to a file.
        StringBuilder output = new StringBuilder();
        for (Body body : bodies) {
            output.append(body.getPosition()[0] + ',' + body.getPosition()[1] + ',' + body.getMass() + ',');
        }
        return output.toString();
    }

    ArrayList<Double[]> deStringer(String line) {
    //takes in a string line from a file and outputs the position and size of a body in an array.
    //undoes the method stringer
        ArrayList<Double[]> out = new ArrayList<>();
        Double[] entry = new Double[3];
        String[] get = line.split(",");
        for (int i = 0; i < get.length; i+=3) {
            entry[0] = Double.valueOf(get[i]);
            entry[1] = Double.valueOf(get[i + 1]);
            entry[2] = Double.valueOf(get[i + 2]);
            out.add(entry);
        }
        return out;
    }

}
