package frc.robot;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;



public class CommandSequences {
    
    //generates a path via points
    public Command genratePath(SwerveSubsystem swerveSubsystem, List<Translation2d> midPoints, Pose2d endPoint) {
        // 1. Create trajectory settings
        TrajectoryConfig trajectoryConfig = new TrajectoryConfig(
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                        .setKinematics(DriveConstants.kDriveKinematics);

        // 2. Generate trajectory
        //Genrates trajectory need to feed start point, a sereris of inbtween points, and end point
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, new Rotation2d(0)),
                midPoints,
                endPoint,
                trajectoryConfig);

        // 3. Define PID controllers for tracking trajectory
        PIDController xController = new PIDController(AutoConstants.kPXController, 0, 0);
        PIDController yController = new PIDController(AutoConstants.kPYController, 0, 0);
        ProfiledPIDController thetaController = new ProfiledPIDController(
                AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        // 4. Construct command to follow trajectory
        SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
                trajectory,
                //swerveSubsystm::getPose is same as () -> swerveSubsystem.getPose()
                swerveSubsystem::getPose,
                DriveConstants.kDriveKinematics,
                xController,
                yController,
                thetaController,
                swerveSubsystem::setModuleStates,
                swerveSubsystem);

        // 5. Add some init and wrap-up, and return everything
        //creates a Command list that will reset the Odometry, then move the path, then stop
        return new SequentialCommandGroup(
                new InstantCommand(() -> swerveSubsystem.resetOdometry(trajectory.getInitialPose())),
                swerveControllerCommand,
                new InstantCommand(() -> swerveSubsystem.stopModules()));
    }

    public Command driveStrait(SwerveSubsystem swerveSubsystem, Double distance) {
        return genratePath(swerveSubsystem, List.of(new Translation2d(0,0)), null);
    }

    public Command runCollectorAndIntake(Intake intake, Conveyor conveyer, double intakeSpeed, double conveyerSpeed) {
        return new ParallelCommandGroup (new RunIntakeCmd(intake,intakeSpeed), new RunConveyerCmd(conveyer, conveyerSpeed));
    }
    
    public Command armHigh(Arm arm) {
        return new SequentialCommandGroup(new RotateArmCmd(arm,100.0), 
        new InstantCommand(() -> arm.pushPistion()));
    }

    public Command armMid(Arm arm) {
        return new SequentialCommandGroup(new InstantCommand(() -> arm.retreactPistion()),
        new RotateArmCmd(arm,85.0));
    }

    public Command armCollect(Arm arm) {
        return new SequentialCommandGroup(new InstantCommand(() -> arm.retreactPistion()),
        new RotateArmCmd(arm,10.0));
    }

    public Command autonomousExample(Arm arm, SwerveSubsystem swerveSubsystem,Grabber grabber, Intake intake, Conveyor conveyor) {
        return new SequentialCommandGroup(armHigh(arm),
        new InstantCommand(() -> grabber.retreactPistion()),
        new InstantCommand(() -> intake.pushIntake()),
        new ParallelCommandGroup(genratePath(swerveSubsystem, List.of(), new Pose2d(Units.inchesToMeters(16),Units.inchesToMeters(200), new Rotation2d(180))), runCollectorAndIntake(intake, conveyor, 1, 1)),
        driveStrait(swerveSubsystem, Units.inchesToMeters(24)),
        genratePath(swerveSubsystem, List.of(), new Pose2d(Units.inchesToMeters(38.2),Units.inchesToMeters(76.19), new Rotation2d(180))),
        driveStrait(swerveSubsystem, Units.inchesToMeters(36.06)),
        new BalanceOnBoardCmd(swerveSubsystem));
    }

 
}
