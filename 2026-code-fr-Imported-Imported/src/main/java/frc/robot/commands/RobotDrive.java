package frc.robot.commands;

import static org.wpilib.units.Units.Rotations;

import org.wpilib.math.util.MathUtil;
import org.wpilib.math.geometry.Translation2d;
import org.wpilib.units.measure.Angle;
import org.wpilib.driverstation.MatchState;
import org.wpilib.driverstation.RobotState;
import org.wpilib.driverstation.Alliance;
import org.wpilib.driverstation.MatchType;
import org.wpilib.driverstation.DriverStationErrors;
import org.wpilib.driverstation.XboxController;
import org.wpilib.driverstation.Alliance;
import org.wpilib.command2.Command;
import frc.robot.Constants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;

public class RobotDrive extends Command{
    private DriveSubsystem m_drive;
    private XboxController m_driverController;
    private Translation2d m_hubPosition;

    public RobotDrive(DriveSubsystem drive, XboxController controller) {
        m_drive = drive;
        m_driverController = controller;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        m_hubPosition = Constants.HubPositions.getHubPosition(MatchState.getAlliance().orElse(Alliance.BLUE));
    }

    @Override
    public void execute() {
        boolean isAiming = m_driverController.getAButton();
    
        if (isAiming) {
            Angle targetHeading = m_drive.getHubHeading(m_hubPosition);
            Angle robotHeading = m_drive.getPose().getRotation().getMeasure();

            Angle error = targetHeading.minus(robotHeading).unaryMinus();
            double factor = error.in(Rotations) * 2.0;

            m_drive.drive(
                -MathUtil.applyDeadband(/*(*/m_driverController.getLeftY()/*  + )*/, OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(/*(*/m_driverController.getLeftX()/*  + )*/, OIConstants.kDriveDeadband),
                factor,
                isAiming
            );
        } else {
    
            m_drive.drive(
                -MathUtil.applyDeadband(/*(*/m_driverController.getLeftY()/*  + )*/, OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(/*(*/m_driverController.getLeftX()/*  + )*/, OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                true
            );
        }
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
