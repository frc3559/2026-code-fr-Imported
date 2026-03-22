package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkFlex;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;


public class IntakeSnakeSubsystem extends SubsystemBase {
    public SparkFlex intakeFloor;
    public SparkMax intakeSnake;
    private DigitalInput IRSensor;
    public static final int  kIntakeFloorMotorCanId = 19;
    public static final int  kIntakeSnakeMotorCanId = 22;

    public IntakeSnakeSubsystem() {
        intakeSnake = new SparkMax(kIntakeSnakeMotorCanId, MotorType.kBrushless);
        intakeFloor = new SparkFlex(kIntakeFloorMotorCanId, MotorType.kBrushless);
        IRSensor = new DigitalInput(0); //This is the pin that the ir sensor is plugged into
    }

    public void intakeSnake(double speed1, double speed2, boolean feederRunning) { //IR Sensor gets if not blocked/ if detecting
        if(IRSensor.get()) {
            System.out.println("IRSensor does not detect ball");
        } else {
            System.out.println("IRSensor detects ball");
        }


     if (IRSensor.get() == true || speed1 == 0 || feederRunning) { //we might have to set IRSensor.get to need to be false based on how the ir sensor works, needs testing
            intakeSnake.set(speed1);
        } else {
            intakeSnake.set(0);
        }

        if (IRSensor.get() == true || speed2 == 0 || feederRunning) { //we might have to set IRSensor.get to need to be false based on how the ir sensor works, needs testing
            intakeFloor.set(speed2);
        } else {
            intakeFloor.set(0);
        }
    }
}
//☻