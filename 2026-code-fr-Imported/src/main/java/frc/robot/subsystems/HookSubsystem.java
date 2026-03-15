package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
//import com.revrobotics.spark.config.SparkBaseConfig; unnecessary
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SoftLimitConfig;
//import com.revrobotics.spark.config.SoftLimitConfigAccessor; unnecessary
import com.revrobotics.ResetMode;
import com.revrobotics.PersistMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class HookSubsystem extends SubsystemBase {
    public SparkMax hook;
    public static final int  kHookMotorCanId = 24;
    public double hookZero;

    public HookSubsystem() {
        hook = new SparkMax(kHookMotorCanId, MotorType.kBrushless);
    }

    public void readHookEncoder() {
      hookZero = hook.getEncoder().getPosition(); //Resets the hook encoder to zero/limit to the position it is in when initialized. DO NOT put the robot on the field unless the hook is up
      System.out.println("hookZero = " + hookZero);
    }


    public void hookUp(double speed) {
        if(hook.getEncoder().getPosition() < (hookZero + 0.018)) {
        hook.set(speed);
        System.out.println("Position: " + hook.getEncoder().getPosition()); //Code to determine the position of the encoder
        } else {
            hook.set(0);
        }
    }
   
    
    public void hookDown(double speed) {
        if(hook.getEncoder().getPosition() > hookZero) {
        hook.set(speed);
        System.out.println("Position: " + hook.getEncoder().getPosition()); //Code to determine the position of the encoder
        } else {
            hook.set(0);
        }
    }
}
