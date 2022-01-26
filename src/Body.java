import java.lang.Math;

public class Body {

    public static final double GRAVITY = 6.674; //gravitational constant for simulation
    private static int objectCount = 0;
    private int identifier;
    private double[] position;
    private double[] velocity;
    private double[] acceleration;
    private double mass;

    Body(double[] position, double[] velocity, double mass){

        super();
        this.identifier = objectCount;
        objectCount++;
        this.position = new double[]{position[0], position[1]};
        this.velocity = new double[]{velocity[0], velocity[1]};
        this.acceleration = new double[]{0, 0};
        this.mass = mass;

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

    public void updatePosition(){
        this.velocity[0] += this.acceleration[0];
        this.velocity[1] += this.acceleration[1];
        this.position[0] += this.velocity[0];
        this.position[1] += this.velocity[1];
    }

    public void calculateAcceleration(Body[] bodies){
        double[] forceVector = new double[]{0,0};
        for (int i = 0; i < bodies.length; i++){
            if (i == this.identifier){
                continue;
            }

            //Calculates the unit vector pointing from this body to the body in the bodies array
            double[] radiusUnit = new double[2];
            radiusUnit[0] = bodies[0].getPosition()[0] - this.position[0];
            radiusUnit[1] = bodies[0].getPosition()[1] - this.position[1];
            double radiusMag = Math.sqrt(Math.pow(radiusUnit[0],2) + Math.pow(radiusUnit[1],2));
            radiusUnit[0] /= radiusMag;
            radiusUnit[1] /= radiusMag;

        }
    }


}
