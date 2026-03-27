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
    public static final int  kIntakePivotMotorCanId = 25;
    public double intakeZero;

    public IntakePivotSubsystem() {
        intakePivot = new SparkMax(kIntakePivotMotorCanId, MotorType.kBrushless);
    }

    public void readIntakePivotEncoder() {
      intakeZero = intakePivot.getEncoder().getPosition(); //Resets the intake pivot encoder top zero/limit to the position it is in when initialized. DO NOT put the robot on the field unless the intake pivot is up
      System.out.println("intakeZero = " + intakeZero);
    }


    public void intakePivotUp(double speed) {
        if(intakePivot.getEncoder().getPosition() > intakeZero) {
        intakePivot.set(speed);
        System.out.println("Position: " + intakePivot.getEncoder().getPosition()); //Code to determine the position of the encoder
         } else {
            intakePivot.set(0);
        } 
    }
    
    
    public void intakePivotDown(double speed) {
        if(intakePivot.getEncoder().getPosition() < (intakeZero + 1.6)) {
        intakePivot.set(speed);
        System.out.println("Position: " + intakePivot.getEncoder().getPosition()); //Code to determine the position of the encoder
         } else {
            intakePivot.set(0);
        } 
    }
}
