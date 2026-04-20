package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem  extends SubsystemBase {
    private final SparkFlex shooterMotor1 = new SparkFlex(21, MotorType.kBrushless);
    private final SparkFlex shooterMotor2 = new SparkFlex(23, MotorType.kBrushless);
    private RelativeEncoder shooterEncoder1;
    private RelativeEncoder shooterEncoder2;
    private int shooterIncrementer = 0; //This is used later for the isReady() method

    public ShooterSubsystem() {
        shooterEncoder1 = shooterMotor1.getEncoder();
        shooterEncoder2 = shooterMotor2.getEncoder();
    }

    //Shooter speed (I think)
    public void shooterSet(double speed) {
        shooterMotor1.set(speed * -1);
        shooterMotor2.set(speed);
       System.out.println("Shooter Motors set to " + speed);
       System.out.println("Shooter Motor 1 running at" + shooterEncoder1.getVelocity());
       System.out.println("Shooter Motor 2 running at" + shooterEncoder2.getVelocity());
    } 


    public boolean isReady() { 
        if (shooterIncrementer >= 100) { //Returns true if it has been 100 frames(2 seconds) or more
            return true;
        } else {
            shooterIncrementer++; //Every frame, increases shooterIncrementer by 1 if it is less than 100
            return false;
        }
    }

    public void resetIncrementer() {
        shooterIncrementer = 0;
    }
    

    public void stopShooter() {
        shooterMotor1.set(0);
        shooterMotor2.set(0);
        //isRunning = false;
        System.out.println("Shooter Motors stopped");
    }


}
