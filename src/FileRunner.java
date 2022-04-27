import java.awt.*;
import java.io.BufferedWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

//FileRunner class takes the positions of the bodies as they update and stores
// them in a file, so they can be drawn by the Video class at a later time
public class FileRunner {

    private String fileName;

    FileRunner(){
        super();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a file name for storage (ending in .txt):");
        this.fileName = input.nextLine();
        try {
            File nf = new File(this.fileName);
            int length = this.fileName.length();
            if (nf.createNewFile() && this.fileName.substring(length-3, length).equals(".txt")) {
                System.out.println("File created");
            } else {
                while (!nf.createNewFile() || !this.fileName.substring(length-3, length).equals(".txt")) {
                    System.out.println("File already exists or format not followed, enter new name");
                    this.fileName = input.nextLine();
                    nf = new File(fileName);
                }
                System.out.println("File Created");

            }
        } catch (IOException | StringIndexOutOfBoundsException e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        }
    }

    // writeToFile intakes the queue that is being built by the simulation and adds the positions to the output storage file
    void writeToFile(BlockingQueue<ArrayList<Double[]>> positionQueue){
        ArrayList<Double[]> line = null; //list of body positions at a given timestamp
        try{
            line =  positionQueue.poll(1,TimeUnit.SECONDS); //runs until the simulation is complete
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        try{
            FileWriter writer = new FileWriter(this.fileName, true);
            BufferedWriter bw = new BufferedWriter(writer);
            while (line != null) {
                bw.write('|');
                for (Double[] position : line) {
                    //writes the body positions (comma separated) separated by |
                    bw.write(String.valueOf(position[0]) + ',' + String.valueOf(position[1]) + '|');
                }
                bw.newLine();//time steps are separated by line
                try {
                    line = positionQueue.poll(1,TimeUnit.SECONDS);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes out the masses of each body(so they can be sized) as well as the time information necessary to create a movie
    void writeHeader(ArrayList<Body> bodies, int timeStep, TimeUnit unit) {
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(this.fileName,false);
            bw = new BufferedWriter(fw);
            bw.write(timeStep + ',' + unit.toString()); //write the timestep and the units
            bw.newLine();
            for (Body body : bodies) {
                //writes the mass of each body, this will be used to size the spheres when
                //drawing them into a gif
                bw.write((int) body.getMass()+',');
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        }

    }

}
