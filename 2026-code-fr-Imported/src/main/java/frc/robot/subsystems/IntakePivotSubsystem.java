package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

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
        if(intakePivot.getEncoder().getPosition() > intakeZero) { //If the intake pivot is below the initialized position, set speed
        intakePivot.set(speed);
        System.out.println("Position: " + intakePivot.getEncoder().getPosition()); //Code to determine the position of the encoder
         } else {
            intakePivot.set(0);
        } 
    }
    
    
    public void intakePivotDown(double speed) {
        if(intakePivot.getEncoder().getPosition() < (intakeZero + 1.6)) { //If the intake pivot is above the lowered position, set speed
        intakePivot.set(speed);
        System.out.println("Position: " + intakePivot.getEncoder().getPosition()); //Code to determine the position of the encoder
         } else {
            intakePivot.set(0);
        } 
    }
}
