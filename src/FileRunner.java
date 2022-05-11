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
        System.out.println("Enter a file name for storage (don't include file extension):");
        this.fileName = input.nextLine();
        try {
            File nf = new File(this.fileName);
            while (!nf.createNewFile() || this.fileName.contains(".")) {
                    System.out.println("File already exists or format not followed, enter new name");
                    this.fileName = input.nextLine();
                    nf = new File(fileName);
            }
            this.fileName += ".txt";
            System.out.println("File Created");
        } catch (IOException | StringIndexOutOfBoundsException e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        }
    }

    // writeToFile intakes the queue that is being built by the simulation and adds the positions to the output storage file
    void writeToFile(BlockingQueue<ArrayList<Double[]>> positionQueue){
        ArrayList<Double[]> line = null; //list of body positions at a given timestamp
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

    //writes out the masses of each body(so they can be sized) as well as the time information necessary to create a movie
    void writeHeader(ArrayList<Body> bodies, int timeStep) {

        //writes the header for the file that will be turned into a gif
        //format has the number of bodies and the time step as the first entry and the mass of each body as the second entry
        try {
            FileOutputStream fw = new FileOutputStream(this.fileName);
            ObjectOutputStream os = new ObjectOutputStream(fw);
            ArrayList<Integer> list = new ArrayList<>();
            list.add(bodies.size());
            list.add(timeStep);
            os.writeObject(list); //write the number of bodies being simulated and the timestep of the simulation
            ArrayList<Double> bodyMass = new ArrayList<>();
            for (Body body : bodies) {
                //writes the mass of each body, this will be used to size the spheres when
                //drawing them into a gif
                bodyMass.add(body.getMass());
            }
            os.writeObject(bodyMass);
            os.flush();
            os.close();
            fw.flush();
            fw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    String getFileName() {
        return this.fileName;
    }

}
