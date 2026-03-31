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

public boolean IRDetectsBall() { 
    if (frameIncrementer >= 5) { //Checks once(out of two required checks) every 25 frames
        frameIncrementer = 0; //Resets the timing
        boolean tmpVal = IRSensor.get();
        if (tmpVal && lastIRDetects) { //If a ball is detected
                System.out.println("IR Sensor detects a ball");
                lastConditional = true;
                return true;
        } else if(!tmpVal && !lastIRDetects) { //If a ball is not detected
                System.out.println("IR Sensor does not detect a ball");
                lastConditional = false;
                return false;
        } else if(lastIRDetects != tmpVal) {
            lastIRDetects = tmpVal;
            return lastConditional;
        }
    } else {
        frameIncrementer++;
        return lastConditional;
    }
    return lastConditional;
}


}
//☻