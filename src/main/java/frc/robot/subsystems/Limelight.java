package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase{

    private final static NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");

    public static final int STOP_TAKING_SNAPSHOTS = 0;
    public static final int TAKE_TWO_SNAPSHOTS_A_SECOND = 0;

    // possible LED move values
    public static final int LED_DEFAULT_TO_PIPELINE = 0;
    public static final int LED_FORCE_OFF = 1;
    public static final int LED_FORCE_ON = 3;
    public static final int LED_FORCE_BLINK = 2;

    // pipelines
    public static final int PIPLINE_REDBALL = 0;
    public static final int PIPELINE_BLUEBALL = 1;

    // stream modes
    public static final int STANDARD_STREAM = 0;
    public static final int PIP_MAIN_STREAM = 1;
    public static final int PIP_SECONDARY_STREAM = 2;
    
    public enum Pipeline {}
    
    public boolean isTargetSpotted() {
        return limelight.getEntry("tv").getDouble(0) == 1.0;
    }

    public double targetXAngleFromCenter() {
        return limelight.getEntry("tx").getDouble(Double.NaN);

    }

    public double targetYAngleFromCenter() {
        return limelight.getEntry("ty").getDouble(Double.NaN);
    }

    public double targetArea() {
        return limelight.getEntry("ta").getDouble(Double.NaN);
    }

    public double limelightPipeline() {
        return limelight.getEntry("getpipe").getDouble(-1);
    }
    
    public void setLedMode(int mode) {
        limelight.getEntry("ledMode").setNumber(mode);
    }

    public void setDriverCamMode(boolean yes) {
        limelight.getEntry("ledMode").setNumber(yes ? 1 : 0 );
    }

    public void setPipeLine(int pipelineMode) {
        limelight.getEntry("pipeline").setNumber(pipelineMode);
    }

    public void setStream(int streamMode) {
        limelight.getEntry("stream").setNumber(streamMode);
    }


    public void setSnapshot(int snapshotMode) {
        limelight.getEntry("pipeline").setNumber(snapshotMode);
    }

    public Pose2d getRobotLocation() {
        return limelight.getEntry("botpose");
    }

    public double get_distance_in() {
        double targetAngle = targetYAngleFromCenter();
        double cameraHeight = 6.5;
        double targetHeight = 5;
        double cameraAngle = 0;
        double d = Math.abs((targetHeight-cameraHeight) / (Math.tan(Math.toRadians(cameraAngle+targetAngle))));
        return d;
    }

    @Override
    public void periodic() {
        
    }
}