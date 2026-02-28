package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem  extends SubsystemBase {
    private final SparkFlex shooterMotor1 = new SparkFlex(21, MotorType.kBrushless);
   // private final SparkFlex shooterMotor2 = new SparkFlex(23, MotorType.kBrushless);
    private RelativeEncoder shooterEncoder1;
   // private RelativeEncoder shooterEncoder2;
    private final int minSpeed = 1600; //Minimum speed for shooters to run for isReady to return true, tolerance is within .05 of nominal shooter speed
    //Encoder.getVelocity gives you rpm, so minSpeed should be several times higher than the motor speed
    private boolean isRunning = false;
    private int frameIncrementer = 0;
    private double currentSetSpeed;
    private double maxSpeed = 0.8;
    private boolean lastSpeedReady = false;

    public ShooterSubsystem() {
        shooterEncoder1 = shooterMotor1.getEncoder();
       // shooterEncoder2 = shooterMotor2.getEncoder();
    }

    public void shooterSet(double speed) {
       // double shooterMotor2Voltage = shooterMotor2.getBusVoltage();
        shooterMotor1.set(speed);
       // shooterMotor2.set(speed);
       System.out.println("Shooter Motors set to " + speed);
       System.out.println("Shooter Motor 1 running at" + shooterEncoder1.getVelocity());
     //System.out.println("Shooter Motor 2 running at" + shooterEncoder2.getVelocity());
    }

    // This function returns true once motors are spun up and ready to fire
    public boolean isReady() { //Will only return true if the encoder reports sufficient speed twice in a row, meaning reading spikes won't make it fire prematurely
        double shooter1Speed = shooterEncoder1.getVelocity();
      //  double shooter2Speed = shooterEncoder2.getVelocity();
        if (shooter1Speed > minSpeed/* && shooter2Speed > minSpeed*/) { //If both motors are running at least at minSpeed, then isReady returns true
            System.out.println("Shooter at minSpeed");
            if (lastSpeedReady) {
                return true;
            } else {
                lastSpeedReady = true;
                return false;
            }
        } else {
            //System.out.println("Shooter not yet at minSpeed");
            lastSpeedReady = false;
            return false;
        }
    }
    
    
    public void stopShooter() {
        shooterMotor1.set(0);
       // shooterMotor2.set(0);
        isRunning = false;
        System.out.println("Shooter Motors stopped");
    }
    public void accelerateShooter() { //This code will increment the speed of the motor until it is sufficient speed, to a max of .8. Allows shooting with lower voltage.
        if (!isRunning) {
            shooterMotor1.set(.65);
            //shooterMotor2.set(.65);
            frameIncrementer = 0; //Resets the frame incrementer when accelerateShooter is first called at the start of a trigger pull
            isRunning = true; 
            currentSetSpeed = .65;
        } else {
            frameIncrementer++;
            if (frameIncrementer >= 50 && !isReady()) { //if the motors have been running for 10 frames and motors are not yet at minspeed, will increment the motor speed
                /*double shooter1Speed = shooterEncoder1.getVelocity();
                System.out.println("Current speed: " + shooter1Speed); //Shows the speed of motor 1 */
                /*double shooter2Speed = shooterEncoder2.getVelocity();
                System.out.println("Current speed: " + shooter2Speed); //Shows the speed of motor 2 */
                if(currentSetSpeed < maxSpeed) { //Only increments speed if it is not yet at maxSpeed, which is .8
                    currentSetSpeed = currentSetSpeed + 0.05; //This is the increment by which the speed will increase
                    shooterMotor1.set(currentSetSpeed); //applies incremented speed to motor 2
                    //shooterMotor2.set(currentSetSpeed); //applies incremented speed to motor 1
                    frameIncrementer = 0;
                   // System.out.println("Shooter Motors set to " + currentSetSpeed); //Prints the speed that the motors are set to
                }
            }
        }

        
    }

}
