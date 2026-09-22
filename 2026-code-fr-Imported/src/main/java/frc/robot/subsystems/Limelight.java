package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
