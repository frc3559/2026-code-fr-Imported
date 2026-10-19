package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkFlex;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class IntakeSnakeSubsystem extends SubsystemBase {
    public SparkFlex intakeFloor;
    public SparkMax intakeSnake;
    public static final int  kIntakeFloorMotorCanId = 19;
    public static final int  kIntakeSnakeMotorCanId = 22;
 
    public IntakeSnakeSubsystem() {
        intakeSnake = new SparkMax(kIntakeSnakeMotorCanId, MotorType.kBrushless);
        intakeFloor = new SparkFlex(kIntakeFloorMotorCanId, MotorType.kBrushless);
    }

    public void intakeSnake(double speed1, double speed2) { //Sets the intake speed
            intakeSnake.set(speed1);
            intakeFloor.set(speed2);
    }


}
//☻