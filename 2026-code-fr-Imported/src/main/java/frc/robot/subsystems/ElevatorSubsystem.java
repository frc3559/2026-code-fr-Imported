package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Elevator/Hook code is not in use
//We never ended up adding an elevator

public class ElevatorSubsystem extends SubsystemBase {
    public SparkMax elevator;
    public static final int  kElevatorMotorCanId = 14;
    public double elevatorZero;

    public ElevatorSubsystem() {
        elevator = new SparkMax(kElevatorMotorCanId, MotorType.kBrushless);
    }

    public void readElevatorEncoder() {
      elevatorZero = elevator.getEncoder().getPosition(); //Resets the elevator encoder to zero/limit to the position it is in when initialized. DO NOT put the robot on the field unless the elevator is up
      System.out.println("elevatorZero = " + elevatorZero);
    }


    public void elevatorUp(double speed) {
        if(elevator.getEncoder().getPosition() < (elevatorZero + 256)) { //The number here is drastically wrong, go check intakePivot for the explanation of this method
        elevator.set(speed);
        System.out.println("Position: " + elevator.getEncoder().getPosition()); //Code to print out the position of the encoder
        } else {
            elevator.set(0);
        }
    }
   
    
    public void elevatorDown(double speed) {
        if(elevator.getEncoder().getPosition() > elevatorZero) { //ditto
        elevator.set(speed);
        System.out.println("Position: " + elevator.getEncoder().getPosition()); //Code to determine the position of the encoder
        } else {
            elevator.set(0);
        }
    }
}
