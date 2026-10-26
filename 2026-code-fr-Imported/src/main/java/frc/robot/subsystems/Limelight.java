package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
    private final NetworkTable table;
    private final DriveSubsystem m_driveSubsystem;

    public Limelight(DriveSubsystem driveSubsystem) {
        table = NetworkTableInstance.getDefault().getTable("limelight-right");
        m_driveSubsystem = driveSubsystem;
    }

    public double getTx() {
        return table.getEntry("tx").getDouble(0.0);
    }

    public boolean hasTarget() {
        return table.getEntry("tv").getDouble(0.0) == 1.0;
    }

    public void moveToTag() {
        double botpose[] = table.getEntry("botpose_orb_wpiblue").getDoubleArray(new double[6]);
        double tx = getTx();
        boolean tv = hasTarget();

        if (hasTarget() == true) {
            //Rotate robot tx degrees using rot or rotDelivered??
            System.out.println("Limelight Target In Range");
        } else {
            System.out.println("Limelight Does Not Detect Target");
        }   //No clue if this is the correct spot for this to work

        /*
        =: Complete
        -: In Progress/Could be completed quickly
        _: Haven't Started
        1: Right Limelight
        2: Left Limelight
        ------------------------------------------------------------------
        =-Get field map
        =-Configure limelight dimensions
        =-Position of robot on field
        =_Get limelight values in to code
        -_Track apriltags in code
        __Rotate robot towards apriltags
        __Add LimelightHelpers.SetRobotOrientation/(call every frame)??
        ------------------------------------------------------------------
        Possibly check line 163 in DriveSubsystem for hub distance??
        */
    }
}
