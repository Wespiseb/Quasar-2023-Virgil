package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

public final class Constants {
    /*TODO make all pistons IDs set to 40-59
                all Motor IDs set to 10-29
                all Sensor IDs set to 30-39 */

    //Motors are all assumed to be TalonFX. TalonFX motors have an integrated encoder which is why you don't see encoder ids
    //If Motors are diffents constants need to shifted accordingly

    //All values of -1 are place holders

    public static final class ModuleConstants {
        public static final double kWheelDiameterMeters = Units.inchesToMeters(3.94);
        //gear ratio is inversed (1/gear ratio) so we can multiply instead of dividing the Rot to meter funtion 
        public static final double kDriveMotorGearRatio = 1/8.14;
        public static final double kTurningMotorGearRatio = 1/12.8;
        //2048 is pulses per rotation of the motor
        public static final double kDriveEncoderRot2Meter = kDriveMotorGearRatio * Math.PI * kWheelDiameterMeters*(1.0/2048.0);
        public static final double kTurningEncoderRot2Rad = kTurningMotorGearRatio * 2 * Math.PI*(1.0/2048.0);
        
        //CTRE mesures their velcity in 100ms, so we multiply it by 10 to get 1s
        public static final double kDriveEncoderRPMS2MeterPerSec = kDriveEncoderRot2Meter*10;
        public static final double kTurningEncoderRPMS2RadPerSec = kTurningEncoderRot2Rad*10;
        //use guess and check to find. when the module is overshooting this needs to be fine tuned
        public static final double kPTurning = 0.2;
    }

    public static final class DriveConstants {

        public static final double kTrackWidth = Units.inchesToMeters(18);
        // Distance between right and left wheels
        public static final double kWheelBase = Units.inchesToMeters(30);
        // Distance between front and back wheels
        public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
                new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, kTrackWidth / 2));

        public static final int kFrontLeftDriveMotorPort = 11;
        public static final int kBackLeftDriveMotorPort = 13;
        public static final int kFrontRightDriveMotorPort = 17;
        public static final int kBackRightDriveMotorPort = 15;

        public static final int kFrontLeftTurningMotorPort = 10;
        public static final int kBackLeftTurningMotorPort = 12;
        public static final int kFrontRightTurningMotorPort = 16;
        public static final int kBackRightTurningMotorPort = 14;

        //Positive should be clockwise
        public static final boolean kFrontLeftTurningEncoderReversed = true;
        public static final boolean kBackLeftTurningEncoderReversed = true;
        public static final boolean kFrontRightTurningEncoderReversed = true;
        public static final boolean kBackRightTurningEncoderReversed = true;

        public static final boolean kFrontLeftDriveEncoderReversed = true;
        public static final boolean kBackLeftDriveEncoderReversed = true;
        public static final boolean kFrontRightDriveEncoderReversed = true;
        public static final boolean kBackRightDriveEncoderReversed = true;

        public static final int kFrontLeftDriveAbsoluteEncoderPort = 31;
        public static final int kBackLeftDriveAbsoluteEncoderPort = 32;
        public static final int kFrontRightDriveAbsoluteEncoderPort = 34;
        public static final int kBackRightDriveAbsoluteEncoderPort = 33;

        //Positive should be clockwise
        public static final boolean kFrontLeftDriveAbsoluteEncoderReversed = true;
        public static final boolean kBackLeftDriveAbsoluteEncoderReversed = true;
        public static final boolean kFrontRightDriveAbsoluteEncoderReversed = true;
        public static final boolean kBackRightDriveAbsoluteEncoderReversed = true;

        //To find set the motors forward record the value (don't inverse the value)
        public static final double kFrontLeftDriveAbsoluteEncoderOffsetRad = 2.12;
        public static final double kBackLeftDriveAbsoluteEncoderOffsetRad = 1.90;
        public static final double kFrontRightDriveAbsoluteEncoderOffsetRad = 0.15;
        public static final double kBackRightDriveAbsoluteEncoderOffsetRad = 2.74;

        //To find set the modules to 100% and see what speed cap out at
        public static final double kPhysicalMaxSpeedMetersPerSecond = 4;
        public static final double kPhysicalMaxAngularSpeedRadiansPerSecond = 7 * 2 * Math.PI;

        //arbitrary chosen based on what drivers pick
        public static final double kTeleDriveMaxSpeedMetersPerSecond = kPhysicalMaxSpeedMetersPerSecond / 3;
        public static final double kTeleDriveMaxAngularSpeedRadiansPerSecond = //
                kPhysicalMaxAngularSpeedRadiansPerSecond / 8;
        public static final double kTeleDriveMaxAccelerationUnitsPerSecond = 3;
        public static final double kTeleDriveMaxAngularAccelerationUnitsPerSecond = Math.PI/3;

        //balancing constants
        public static final double kBoardBalancedGoalDegrees = 0;
        public static final double kBoardBalancedAngleThresholdDegrees = 1;
        public static final double kBoardBalancedSpeed = 1.5;
        public static final double kBoardBalancedDriveKP = -1;
        public static final double kBoardBalancedDriveKD = -1;
        public static final int kBalanceCounter = 5000;
    } 

    public static final class IntakeConstants {
        public static final int kMotorPort = 18;

        public static final int kLeftPistonForwardPort = 41;
        public static final int kLeftPistonBackwardPort = 42;

        public static final int kRightPistonForwardPort = 43;
        public static final int kRightPistonBackwardPort = 44;
    }

    public static final class GrabberConstants {

        public static final int kRightPistonForwardPort = 45;
        public static final int kRightPistonBackwardPort = 46;

        public static final int kLeftPistonForwardPort = 74;
        public static final int kLeftPistonBackwardPort = 48;
    }

    public static final class ArmConstants {

        public static final int kRotater = 20;
        public static final double kRotaterKP = 0.2;
        public static final boolean kRotaterEncoderReversed = false;
        public static final int kRotaterEncoderCountstoAngle = -1;

        public static final int kRightPistonForwardPort = 49;
        public static final int kRightPistonBackwardPort = 50;

        public static final int kLeftPistonForwardPort = 51;
        public static final int kLeftPistonBackwardPort = 52;
        
        public static final int kArmPneumaticFwd = -1;
        public static final int kArmPneumaticBwrd = -1;

    }

    public static final class ConveyerConstants {
        public static final int kConveyerMotorPort = 19;

        public static final boolean kConveyerEncoderReversed = false;

        public static final int kConveyerEncoderCountstoMeters = -1;
        //CTRE mesures their velcity in 100ms, so we multiply it by 10 to get 1s
        public static final int kConveyerEncoderRPMS2MPS = kConveyerEncoderCountstoMeters * 10;

        public static final double kPConveyer = -1;
    }

    public static final class ShooterConstants {
        public static final int kFlyWheelMotorPort = 19;

        public static final boolean kFlyWheelEncoderReversed = false;
        
        public static final int kFlyWheelEncoderCountstoMeters = -1;
        //CTRE mesures their velcity in 100ms, so we multiply it by 10 to get 1s
        public static final int kFlyWheelEncoderRPMS2MPS = kFlyWheelEncoderCountstoMeters * 10;
    }

    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = DriveConstants.kPhysicalMaxSpeedMetersPerSecond / 3;
        public static final double kMaxAngularSpeedRadiansPerSecond = //
                DriveConstants.kPhysicalMaxAngularSpeedRadiansPerSecond / 10;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kMaxAngularAccelerationRadiansPerSecondSquared = Math.PI / 3;
        public static final double kPXController = 1.5;
        public static final double kPYController = 1.5;
        public static final double kPThetaController = 3;

        public static final TrapezoidProfile.Constraints kThetaControllerConstraints = //
                new TrapezoidProfile.Constraints(
                        kMaxAngularSpeedRadiansPerSecond,
                        kMaxAngularAccelerationRadiansPerSecondSquared);
    }

    public static final class OIConstants {
        public static final int kLeftJoystickPort = 0;
        public static final int kRightJoystickPort = 1;
        public static final int kXboxControllerPort = 2;

        //axis and buttons id for drive joysticks
        public static final int kDriverYAxis = 0;
        public static final int kDriverXAxis = 1;
        public static final int kDriverRotAxis = 1;

        public static final int kDriverFieldOrientedButtonIdx = 10;
        public static final int kDriverIntakeButtonIdx = 2;
        public static final int kDriverReverseIntakeButtonIdx = 3;

        public static final int kSecondaryDriverControllerPort = 1;


        //area were joysticks will not activate
        public static final double kDeadband = 0.05;
    }

    public static final class SensorConstants {
        public static final int kPigeonID = 30;
    }
}
