package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class IntakeFloorSubsystem extends SubsystemBase {
    public SparkMax intakeFloor;
    public static final int  kIntakeFloorMotorCanId = 25;

    public IntakeFloorSubsystem() {
        intakeFloor = new SparkMax(kIntakeFloorMotorCanId, MotorType.kBrushless);
    }

    public void intakeFloor(double speed) {
        intakeFloor.set(speed);
    }
}
