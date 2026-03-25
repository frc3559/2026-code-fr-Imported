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
    private int frameIncrementer = 0; //These three vars are used for the IR detection at the bottom of the file. This one is used for timing
    private boolean lastIRDetects = false; //This one is used for debouncing
    private boolean lastConditional = false; //And this one is used to keep the methods from beating me to death with a lead pipe              (Shouldn't call a method inside of itself)
    
    public IntakeSnakeSubsystem() {
        intakeSnake = new SparkMax(kIntakeSnakeMotorCanId, MotorType.kBrushless);
        intakeFloor = new SparkFlex(kIntakeFloorMotorCanId, MotorType.kBrushless);
        IRSensor = new DigitalInput(0); //This is the pin that the ir sensor is plugged into
    }

    public void intakeSnake(double speed1, double speed2, boolean feederRunning) { //IR Sensor gets if not blocked/ if detecting
        if(IRSensor.get()) {
            System.out.println("IRSensor detects ball");
        } else {
            System.out.println("IRSensor does not detect ball");
        }


     if (!IRDetectsBall() || speed1 == 0 || feederRunning) { //we might have to set IRSensor.get to need to be false based on how the ir sensor works, needs testing
            intakeSnake.set(speed1);
        } else {
            intakeSnake.set(0);
        }

        if (!IRDetectsBall() || speed2 == 0 || feederRunning) { //we might have to set IRSensor.get to need to be false based on how the ir sensor works, needs testing
            intakeFloor.set(speed2);
        } else {
            intakeFloor.set(0);
        }
    }

public boolean IRDetectsBall() { //I have two proposals for different ways to detects if the IR sensor detected a ball every 50 frames. This is proposal 1.
    if (frameIncrementer >= 50) { //Checks once(out of two required checks) every 50 frames
        frameIncrementer = 0; //Resets the timing
        if (DetectionDebounce()) {
            lastConditional = true;
            System.out.println("IR Sensor detects ball");
            return true;
        } else {
            lastConditional = false;
            System.out.println("IR Sensor does not detect ball");
            return false;
        }
    } else {
        frameIncrementer++;
        return lastConditional;
    }
}

public boolean DetectionDebounce() { //If the IR sensor detects a ball twice in a row, returns true
    if (IRSensor.get()) { 
        if (lastIRDetects) {
            lastIRDetects = true;
            return true;
        } else {
            lastIRDetects = true;
            return false;
        }
    } else {
        lastIRDetects = false;
        return false;
    }
} 

/*public boolean IRDetectsBall() { //This is proposal 2
    if (frameIncrementer >= 50) { //Checks once(out of two required checks) every 50 frames
        frameIncrementer = 0; //Resets the timing
        if (IRSensor.get()) {
            if (lastIRDetects) {
                System.out.println("IR sensor detects ball");
                lastConditional = true;
                lastIRDetects = true;
                return true;
            } else {
                System.out.println("IR sensor does not detect ball");
                lastConditional = false;
                lastIRDetects = true;
                return false;
            }
        } else {
            lastIRDetects = false;
            lastConditional = false;
            return false;
        }
    } else {
        frameIncrementer++;
        return lastConditional;
    }
} */
}
//☻