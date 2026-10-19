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
        /*
        --Get field map
        Configure limelight dimensions
        --Position of robot on field
        --Get limelight values in to code
        Track apriltags in code
        Rotate robot towards apriltags
        add LimelightHelpers.SetRobotOrientation/(call every frame)?
        */
    }
}
