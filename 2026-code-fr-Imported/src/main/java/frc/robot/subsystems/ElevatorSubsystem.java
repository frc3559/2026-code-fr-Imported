package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SoftLimitConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.ResetMode;
import com.revrobotics.PersistMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
    public SparkMax elevator;
    public static final int  kElevatorMotorCanId = 14;

    public ElevatorSubsystem() {
        elevator = new SparkMax(kElevatorMotorCanId, MotorType.kBrushless);
        SoftLimitConfig elevatorLimitConfig = new SoftLimitConfig(); //The soft limit code should stop the motor when it hits one of the specified values.
        elevatorLimitConfig.forwardSoftLimit(95); //We do not know whether the limit value is degrees or rotations
        elevatorLimitConfig.reverseSoftLimit(0); //These lines make no forward and reverse limits. tweak values based on testing.
        SparkMaxConfig elevatorConfig = new SparkMaxConfig();
        elevatorConfig.apply(elevatorLimitConfig); //applies the elevatorlimitconfig's limits to the spark max config called elevatorconfig
        elevator.configure(elevatorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters); //Change persist and reset paramaters to no or blank based on what we want
    } //the above line configures the motor elevator based on elevatorConfig

    public void elevate(double speed){
        elevator.set(speed);
        // System.out.println("Position: " + elevator.getEncoder().getPosition()); //Code to determine the position of the encoder
    }
}