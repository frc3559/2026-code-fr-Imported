package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
//import com.revrobotics.spark.config.SparkBaseConfig; unnecessary
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SoftLimitConfig;
//import com.revrobotics.spark.config.SoftLimitConfigAccessor; unnecessary
import com.revrobotics.ResetMode;
import com.revrobotics.PersistMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class IntakePivotSubsystem extends SubsystemBase {
    public SparkMax intakePivot;
    public static final int  kIntakePivotMotorCanId = 23;

    public IntakePivotSubsystem() {
        intakePivot = new SparkMax(kIntakePivotMotorCanId, MotorType.kBrushless);
    }

    public void intakePivotUp(double speed) {
        if(intakePivot.getEncoder().getPosition() < 13) {
        intakePivot.set(speed);
        System.out.println("Position: " + intakePivot.getEncoder().getPosition()); //Code to determine the position of the encoder
        } else {
            intakePivot.set(0);
        }
    }
   
    
    public void intakePivotDown(double speed) {
        if(intakePivot.getEncoder().getPosition() > 5.5) {
        intakePivot.set(speed);
        System.out.println("Position: " + intakePivot.getEncoder().getPosition()); //Code to determine the position of the encoder
        } else {
            intakePivot.set(0);
        }
    }
}
