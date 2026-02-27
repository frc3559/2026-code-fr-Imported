package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;


public class IntakeSnakeSubsystem extends SubsystemBase {
   // public SparkMax intakeFloor;
    public SparkMax intakeSnake;
    private DigitalInput IRSensor;
   // public static final int  kIntakeFloorMotorCanId = 25;
    public static final int  kIntakeSnakeMotorCanId = 22;

    public IntakeSnakeSubsystem() {
        intakeSnake = new SparkMax(kIntakeSnakeMotorCanId, MotorType.kBrushless);
       // intakeFloor = new SparkMax(kIntakeFloorMotorCanId, MotorType.kBrushless);
        IRSensor = new DigitalInput(0); //This is the pin that the ir sensor is plugged into
    }

    public void intakeSnake(double speed, boolean feederRunning) { //IR Sensor gets if not blocked/ if detecting
        /*if(IRSensor.get()) {
            System.out.println("Sensor gets");
        } test code */

     if (IRSensor.get() == true || speed == 0 || feederRunning) { //we might have to set IRSensor.get to need to be false based on how the ir sensor works, needs testing
         //   intakeFloor.set(speed);
            intakeSnake.set(speed);
        } else {
         //   intakeFloor.set(0);
            intakeSnake.set(0);
        }
    }
}