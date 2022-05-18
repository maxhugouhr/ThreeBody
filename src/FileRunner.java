import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//FileRunner class takes the positions of the bodies as they update and stores
// them in a file, so they can be drawn by the Video class at a later time
public class FileRunner {

    private String rawFile;

    FileRunner(String fileName){
        super();

        Scanner input = new Scanner(System.in);
        System.out.println("Enter a file name for storage (including .txt):");
        this.rawFile = "src/resources/" + input.nextLine();
        try {
            //asks for the user to input a file name for the raw storage
            File nf = new File(this.rawFile);
            while (!this.rawFile.contains(".txt") || !nf.createNewFile()) {
                    System.out.println("File already exists or format not followed, enter new name");
                    this.rawFile = input.nextLine();
                    nf = new File(rawFile);
            }
            System.out.println("File Created");
            //uses the same name for the output gif
        } catch (IOException | StringIndexOutOfBoundsException e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        }
    }

    // writeRawFile intakes the queue that is being built by the simulation and writes the array
    // of bodies to the output file in a serialized format
    void writeRawFile(BlockingQueue<ArrayList<Body>> positionQueue){
        ArrayList<Body> line = null; //list of body positions at a given timestamp
        try{
            line =  positionQueue.poll(5,TimeUnit.SECONDS); //runs until the simulation is complete
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        try{
            FileWriter writer = new FileWriter(this.rawFile);
            while (line != null) {
                writer.write(stringer(line));
                writer.write(System.lineSeparator());
                try {
                    line = positionQueue.poll(5,TimeUnit.SECONDS);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    String getRawFileName() {
        return this.rawFile;
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
        ArrayList<Double[]> out = null;
        Double[] entry;
        String[] get = line.split(",");
        for (int i = 0; i < get.length; i+=3) {
            out = new ArrayList<>();
            entry = new Double[3];
            entry[0] = Double.valueOf(get[i]);
            entry[1] = Double.valueOf(get[i + 1]);
            entry[2] = Double.valueOf(get[i + 2]);
            out.add(entry);
        }
        return out;
    }


}
