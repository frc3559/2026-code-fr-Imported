package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class IntakePivotSubsystem extends SubsystemBase {
    public SparkMax intakePivot;
    public static final int  kIntakePivotMotorCanId = 26;

    public IntakePivotSubsystem() {
        intakePivot = new SparkMax(kIntakePivotMotorCanId, MotorType.kBrushless);
    }

    public void intakePivotUp(double speed) {
        intakePivot.set(speed);
    }
    
    public void intakePivotDown(double speed) {
        intakePivot.set(speed);
    }
}
