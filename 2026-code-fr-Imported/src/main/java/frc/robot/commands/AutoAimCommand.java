package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;

public class AutoAimCommand extends CommandBase {

    private final DriveSubsystem drivetrain;
    private final Limelight limelight;

    private final double kP = 0.03; // Tune this

    public AutoAimCommand(DriveSubsystem drivetrain, Limelight limelight) {
        this.drivetrain = drivetrain;
        this.limelight = limelight;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        if (!limelight.hasTarget()) {
            drivetrain.arcadeDrive(0, 0); // No target → stop turning
            return;
        }

        double tx = limelight.getTx();
        double turn = tx * kP;

        drivetrain.arcadeDrive(0, turn);
    }

    @Override
    public boolean isFinished() {
        return limelight.hasTarget() && Math.abs(limelight.getTx()) < 1.0;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }
}
