package frc.robot.subsystems;

import org.wpilib.networktables.NetworkTable;
import org.wpilib.networktables.NetworkTableInstance;
import org.wpilib.command2.SubsystemBase;

public class Limelight extends SubsystemBase {
    private final NetworkTable table;

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public double getTx() {
        return table.getEntry("tx").getDouble(0.0);
    }

    public boolean hasTarget() {
        return table.getEntry("tv").getDouble(0.0) == 1.0;
    }
}
