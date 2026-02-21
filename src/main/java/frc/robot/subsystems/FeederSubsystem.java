package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FeederSubsystem  extends SubsystemBase{
    private final SparkMax feederMotor = new SparkMax(22, MotorType.kBrushless);

    public FeederSubsystem(){

    }

    public void feederSet(double speed){
    
    feederMotor.set(speed);
    }

}
 //new feeder code, we need to add delay