package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.SensorConstants;

public class SwerveSubsystem extends SubsystemBase {
    private final SwerveModule frontLeft = new SwerveModule(
            DriveConstants.kFrontLeftDriveMotorPort,
            DriveConstants.kFrontLeftTurningMotorPort,
            DriveConstants.kFrontLeftDriveEncoderReversed,
            DriveConstants.kFrontLeftTurningEncoderReversed,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderPort,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule frontRight = new SwerveModule(
            DriveConstants.kFrontRightDriveMotorPort,
            DriveConstants.kFrontRightTurningMotorPort,
            DriveConstants.kFrontRightDriveEncoderReversed,
            DriveConstants.kFrontRightTurningEncoderReversed,
            DriveConstants.kFrontRightDriveAbsoluteEncoderPort,
            DriveConstants.kFrontRightDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kFrontRightDriveAbsoluteEncoderReversed);

    private final SwerveModule backLeft = new SwerveModule(
            DriveConstants.kBackLeftDriveMotorPort,
            DriveConstants.kBackLeftTurningMotorPort,
            DriveConstants.kBackLeftDriveEncoderReversed,
            DriveConstants.kBackLeftTurningEncoderReversed,
            DriveConstants.kBackLeftDriveAbsoluteEncoderPort,
            DriveConstants.kBackLeftDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kBackLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule backRight = new SwerveModule(
            DriveConstants.kBackRightDriveMotorPort,
            DriveConstants.kBackRightTurningMotorPort,
            DriveConstants.kBackRightDriveEncoderReversed,
            DriveConstants.kBackRightTurningEncoderReversed,
            DriveConstants.kBackRightDriveAbsoluteEncoderPort,
            DriveConstants.kBackRightDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kBackRightDriveAbsoluteEncoderReversed);

    private final PigeonIMU gyro = new PigeonIMU(SensorConstants.kPigeonID);
    private final SwerveDriveOdometry odometer = new SwerveDriveOdometry(DriveConstants.kDriveKinematics,
            new Rotation2d(0),getModulePositions());
    private GenericEntry headingShuffleBoard, odometerShuffleBoard, rollShuffleBoard, pitchShuffleBoard, trueAngleShuffleBoard;

    public SwerveSubsystem() {
        ShuffleboardTab programmerBoard = Shuffleboard.getTab("Programmer Board");
       
        headingShuffleBoard = programmerBoard.add("Robot Heading", 0).getEntry();
        odometerShuffleBoard = programmerBoard.add("Robot Location", "").getEntry();
        rollShuffleBoard = programmerBoard.add("Robot Roll", 0).getEntry();
        pitchShuffleBoard = programmerBoard.add("Robot Pitch", 0).getEntry();
        trueAngleShuffleBoard = programmerBoard.add("Robot Angle", 0).getEntry();


        new Thread(() -> {
            try {
                Thread.sleep(1000);
                zeroHeading();
            } catch (Exception e) {
            }
        }).start();
    }

    public void zeroHeading() {
        gyro.setYaw(0);
    }

    public double getHeading() {
        return -Math.IEEEremainder(gyro.getYaw(), 360);
    }

    public double getRoll() {
        return -gyro.getRoll();
    }

    public double getPitch() {
        return -gyro.getPitch();
    }

    public double getTrueAngle() {
        if ((getHeading() >= -45 && getHeading() <= 45) || getHeading() <= -135 || getHeading() >= 135) 
            return (180/Math.PI)*Math.atan((1/(Math.cos(getHeading()/180*Math.PI)))*Math.tan(getRoll()/180*Math.PI));
        return (180/Math.PI)*Math.atan((1/(Math.cos(getHeading()/180*Math.PI+Math.PI/2)))*Math.tan(getPitch()/180*Math.PI));
    }

    public Rotation2d getRotation2d() {
        return Rotation2d.fromDegrees(getHeading());
    }

    public Pose2d getPose() {
        return odometer.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        odometer.resetPosition(getRotation2d(), getModulePositions(), pose);
    }

    @Override
    public void periodic() {
        odometer.update(getRotation2d(), getModulePositions());
        headingShuffleBoard.setDouble(getHeading());
        odometerShuffleBoard.setString(getPose().getTranslation().toString());
        rollShuffleBoard.setDouble(getRoll());

        pitchShuffleBoard.setDouble(getPitch());
        trueAngleShuffleBoard.setDouble(getTrueAngle());


    }

    public SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] temp = {frontLeft.getPosition(), frontRight.getPosition(), backLeft.getPosition(), backRight.getPosition()};
        return temp;
    }

    public void stopModules() {
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }

    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[2]);
        backRight.setDesiredState(desiredStates[3]);
    }

    public void driveDirection(double speed, double heading) {
        SwerveModuleState[] desiredStates = new SwerveModuleState[4];

        for (int i = 0; i < 4; i++) {
            desiredStates[i] = new SwerveModuleState(speed, Rotation2d.fromDegrees(heading));
        }

        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[2]);
        backRight.setDesiredState(desiredStates[3]);
    }

    public void resetEncoders() {
        frontLeft.resetEncoders();
        frontRight.resetEncoders();
        backLeft.resetEncoders();
        backRight.resetEncoders();
    }
}
