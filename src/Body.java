import java.lang.Math;
import java.util.LinkedList;

public class Body {

    public static final double GRAVITY = 6.674; //gravitational constant for simulation
    private static int objectCount = 0;
    private int identifier;
    private double[] position;
    private double[] velocity; //in units of length per second
    private double[] acceleration; //in units of length per second per second
    private double mass;
    private static LinkedList<Body> bodies;

    Body(double[] position, double[] velocity, double mass){

        super();
        this.identifier = objectCount;
        objectCount++;
        this.position = new double[]{position[0], position[1]};
        this.velocity = new double[]{velocity[0], velocity[1]};
        this.acceleration = new double[]{0, 0};
        this.mass = mass;
        bodies.add(this);

    }

    public double getMass(){
        return this.mass;
    }

    public double[] getPosition(){
        return this.position;
    }

    public double[] getVelocity(){
        return this.velocity;
    }

    public double[] getAcceleration(){
        return this.acceleration;
    }

    public void updatePosition(double timeStep){
        //timeStep is the amount of time that is passing between each round of calculations in seconds
        this.velocity[0] += this.acceleration[0]*timeStep;
        this.velocity[1] += this.acceleration[1]*timeStep;
        this.position[0] += this.velocity[0]*timeStep;
        this.position[1] += this.velocity[1]*timeStep;
    }

    public void calculateAcceleration(){
        double[] forceVector = new double[]{0,0};
        for (int i = 0; i < bodies.size(); i++){
            if (i == this.identifier){
                continue;
            }

            //Calculates the unit vector pointing from this body to the body in the bodies array
            double[] radiusUnit = new double[2];
            radiusUnit[0] = bodies.get(i).getPosition()[0] - this.position[0];
            radiusUnit[1] = bodies.get(i).getPosition()[1] - this.position[1];
            double radiusMag = Math.sqrt(Math.pow(radiusUnit[0],2) + Math.pow(radiusUnit[1],2));
            radiusUnit[0] /= radiusMag;
            radiusUnit[1] /= radiusMag;

            //Calculate the force being applied from the body in the array to this body
            forceVector[0] += GRAVITY*this.mass*bodies.get(i).getMass()*radiusUnit[0]/radiusMag;
            forceVector[1] += GRAVITY*this.mass*bodies.get(i).getMass()*radiusUnit[1]/radiusMag;

        }
        this.acceleration[0] = forceVector[0] / this.mass;
        this.acceleration[1] = forceVector[1] / this.mass;
    }


}
