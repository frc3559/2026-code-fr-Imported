// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.wpilibj2.command.Commands.runEnd;
import static edu.wpi.first.wpilibj2.command.Commands.runOnce;
import static edu.wpi.first.wpilibj2.command.Commands.sequence;
import static edu.wpi.first.wpilibj2.command.Commands.waitSeconds;

import java.util.List;

import javax.sql.rowset.serial.SerialArray;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
//import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IntakeSnakeSubsystem;
import frc.robot.subsystems.IntakePivotSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.HookSubsystem;
import frc.robot.subsystems.HookSubsystem;
//import com.lib.pathplanner.path.test;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  public final DriveSubsystem m_robotDrive;    //Keep public for Limelight
  private ShooterSubsystem m_robotShoot = new ShooterSubsystem();
  //private final ElevatorSubsystem m_robotElevate //= new ElevatorSubsystem();
  private IntakeSnakeSubsystem m_robotIntakeSnake = new IntakeSnakeSubsystem();
  private IntakePivotSubsystem m_robotIntakePivot; //= new IntakePivotSubsystem();
  private HookSubsystem m_robotHook; //= new HookSubsystem();
  private FeederSubsystem m_robotFeeder = new FeederSubsystem();


  // The driver's controller
  CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

  //private final PathPlannerPath autoChooser;
  

  public RobotContainer() {
    m_robotDrive =  new DriveSubsystem(
      () -> LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight-left"),
      () -> LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight-right")
    );
    //Pathplanner
    /*
    NamedCommands.registerCommand(
      "Btm - Mid",
      Commands.Sequence(
      m_robotShoot.shooterSet(.8),
      Commands.waitSeconds(8),
      m_robotFeeder.feederSet(-1)
      )
    );

      
    autoChooser = AutoBuilder.buildAutoChooser();    //Needed for Pathplanner
    SmartDashboard.putData("Auto Chooser", autoChooser);
    */
    configureButtonBindings();
  }
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public void SetIntakePivot(IntakePivotSubsystem intPvt) {
    // Configure the button bindings
    m_robotIntakePivot = intPvt;

    // Configure default commands
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(/*(*/m_driverController.getLeftY()/*  + )*/, OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(/*(*/m_driverController.getLeftX()/*  + )*/, OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                true),
            m_robotDrive));
  }
//We want to round out the drive curve but don't know where to add it.
  public void SetHook(HookSubsystem hk) {
    m_robotHook = hk;
}

/*public  void SetElevator(ElevatorSubsystem m_elvtr) {
   m_robotElevate = hk;
}*/

/**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
 
  private void configureButtonBindings() {
    /*
    new JoystickButton(m_driverController, Button.kR1.value)
        .whileTrue(new RunCommand(
            () -> m_robotDrive.setX(),
            m_robotDrive));
    
    new JoystickButton(m_driverController, CommandXboxController.Button.kStart.value)
        .onTrue(new InstantCommand(
            () -> m_robotDrive.zeroHeading(),
            m_robotDrive));
    */
    
    /*
    //Limelight Controller Inputs
    m_driverController.leftBumper().whileTrue(runEnd(() -> m_robotDrive.drive
      (m_driverController.getLeftY(),
    //LimelightHelpers.getTY("limelight") * -0.1,
      m_driverController.getLeftX(),
      LimelightHelpers.getTX("limelight") * -0.05, false),
      () -> m_robotDrive.drive(0,0,0,false)));
    */
/*
    m_robotDrive.setDefaultCommand(
      m_robotDrive.applyRequest(() ->
        drive.withVelocityX(-m_driverController.getLeftY() * kMaxSpeedMetersPerSecond)
              .withVelocityY(-m_driverController.getLeftX() * kMaxSpeedMetersPerSecond)
              .withRotationalRate(-m_driverController.getRightX() * kMaxAngularSpeed)
      )
    );
    
    m_driverController.leftBumper().whileTrue(
      new RunCommand(
        () -> {
          int[] allowedTags = {25, 26, 21, 24, 27, 18, 19, 20, 3, 4, 11, 2, 10, 9, 8, 5};

          int currentTagID = (int) LimelightHelpers.getFiducialID("limelight-jasper"); //add 2nd
          double rotationRate = 0;
          double velocityX = -m_driverController.getLeftY() * MaxSpeed;
          double velocityY = -m_driverController.getLeftX() * MaxSpeed;

          boolean isAllowedTag = false;
          for (int allowedTag : allowedTags) {
            if (currentTagID == allowedTag) {
              isAllowedTag = true;
              break;
            }
          }
          if (isAllowedTag && LimelightHelpers.getTV("limelight-jasper")) {
            rotationRate = LimelightHelpers.getTX("limelight-jasper") * -0.06;
          }

          m_robotDrive.setControl(
            drive.xSpeedDelivered(velocityX)
            .ySpeedDelivered(velocityY)
            .rotDelivered(rotationRate)
          );
      },
      m_robotDrive
      )
    );
*/

    //Controller Inputs
    m_operatorController.rightTrigger().whileTrue(runEnd(() -> m_robotShoot.shooterSet(.28), () -> m_robotShoot.stopShooter())); //old shooting code
   //m_driverController.rightTrigger().whileTrue(runEnd(() -> m_robotShoot.accelerateShooter(), () -> m_robotShoot.stopShooter()));
    m_operatorController.rightTrigger().whileTrue(runEnd(() -> shootBall(), () -> dontFeed()));
    m_operatorController.leftTrigger().whileTrue(runEnd(() -> m_robotIntakeSnake.intakeSnake(3.5, .15, m_robotFeeder.isRunning()), () -> m_robotIntakeSnake.intakeSnake(0, 0, false)));//first num is snake, second num is intake
   
   
    //this reverses the snake to unjam 
     m_operatorController.rightBumper().whileTrue(runEnd(() -> unjamBall(), () -> stopUnjamBall()));
    //m_operatorController.leftBumper().whileTrue(runEnd(() -> m_robotIntakeSnake.intakeSnake(-1.5, -.3, m_robotFeeder.isRunning()), () -> m_robotIntakeSnake.intakeSnake(0, 0, false)));
    
    
    m_operatorController.povUp().whileTrue(runEnd(() -> m_robotIntakePivot.intakePivotUp(-0.2), () -> m_robotIntakePivot.intakePivotUp(0)));
    m_operatorController.povDown().whileTrue(runEnd(() -> m_robotIntakePivot.intakePivotDown(0.15), () -> m_robotIntakePivot.intakePivotDown(0)));
    //m_operatorController.a().whileTrue(runEnd(() -> m_robotElevate.elevatorUp(0.25), () -> m_robotElevate.elevatorUp(0)));
    //m_operatorController.y().whileTrue(runEnd(() -> m_robotElevate.elevatorDown(-0.25), () -> m_robotElevate.elevatorDown(0)));
    //m_operatorController.b().whileTrue(runEnd(() -> m_robotHook.hookUp(0.1), () -> m_robotHook.hookUp(-0.1)));
  }
 
private void dontFeed() {
    m_robotShoot.resetIncrementer();
    m_robotFeeder.feederSet(0);
}

  private void shootBall() { //This will run when the shooter motors get up to speed
    if (m_robotShoot.isReady()) {
      m_robotFeeder.feederSet(-.2);
    } else {
      m_robotFeeder.feederSet(0);
    }
  }

  private void unjamBall() { //This will run when the shooter motors get up to speed
        m_robotFeeder.feederSet(0.2);
        m_robotIntakeSnake.intakeSnake(-.25,-0.0, m_robotFeeder.isRunning());//first num is snake, second num is intake
        m_robotShoot.shooterSet(-.2);
  }
private void stopUnjamBall() { //This will run when the shooter motors get up to speed
        m_robotFeeder.feederSet(0);
        m_robotIntakeSnake.intakeSnake(0,0, m_robotFeeder.isRunning());
        //m_robotShoot.shooterSet(0);
        m_robotShoot.stopShooter();
  }

  public Command getAutonomousCommand() {


    Command shootcmd = new ParallelCommandGroup(
      runEnd(() -> m_robotShoot.shooterSet(.3), () -> m_robotShoot.stopShooter()),
      runEnd(() -> shootBall(), () -> dontFeed()),
      waitSeconds(3).andThen(runEnd(() -> m_robotIntakeSnake.intakeSnake(3.5, .15, m_robotFeeder.isRunning()), () -> m_robotIntakeSnake.intakeSnake(0, 0, false)))
    ).withTimeout(10);

    Command drivecmd = new RunCommand(() -> m_robotDrive.drive(-1, 0, 0, false), m_robotDrive).withTimeout(.6).andThen(runOnce(() -> m_robotDrive.drive(0, 0, 0, false), m_robotDrive));

    return sequence(
      drivecmd,
      shootcmd
    );
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  /*public Command getAutonomousCommand() {     -------------------------------------------------------------Auto code
    // Create config for trajectory
    TrajectoryConfig config = new TrajectoryConfig(
        AutoConstants.kMaxSpeedMetersPerSecond,
        AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(DriveConstants.kDriveKinematics);

    // An example trajectory to follow. All units in meters.
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        config);

    var thetaController = new ProfiledPIDController(
        AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
        exampleTrajectory,
        m_robotDrive::getPose, // Functional interface to feed supplier
        DriveConstants.kDriveKinematics,

        // Position controllers
        new PIDController(AutoConstants.kPXController, 0, 0),
        new PIDController(AutoConstants.kPYController, 0, 0),
        thetaController,
        m_robotDrive::setModuleStates,
        m_robotDrive);

    // Reset odometry to the starting pose of the trajectory.
    m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

    // Run path following command, then stop at the end.
    return swerveControllerCommand.andThen(() -> m_robotDrive.drive(0, 0, 0, true));
  }*/
}
