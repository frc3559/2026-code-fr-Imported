package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class HookSubsystem extends SubsystemBase {
    public SparkMax hook;
    public static final int  kHookMotorCanId = 25;

    public HookSubsystem() {
        hook = new SparkMax(kHookMotorCanId, MotorType.kBrushless);

    }

    public void hookOn(double speed) {
        hook.set(speed);
    }
    public void hookOff(double speed) {
        hook.set(speed);
    }
} //new intake floor code