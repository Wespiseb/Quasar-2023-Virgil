// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final Conveyor conveyer = new Conveyor();
  private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  private final Intake intake = new Intake();
  private final Grabber grabber = new Grabber();
  private final CommandSequences commandSequences = new CommandSequences();
  private final Arm arm = new Arm();

  private final Joystick leftJoystick = new Joystick(OIConstants.kLeftJoystickPort);
  private final Joystick rightJoystick = new Joystick(OIConstants.kRightJoystickPort);
  private final XboxController xboxController = new XboxController(OIConstants.kXboxControllerPort);

  public RobotContainer() {
    configureButtonBindings();

    
    swerveSubsystem.setDefaultCommand(new SwerveJoystickCmd(swerveSubsystem,
        () -> -leftJoystick.getRawAxis(OIConstants.kDriverYAxis),
        () -> leftJoystick.getRawAxis(OIConstants.kDriverXAxis),
        () -> rightJoystick.getRawAxis(OIConstants.kDriverRotAxis),
        () -> !rightJoystick.getRawButton(OIConstants.kDriverFieldOrientedButtonIdx)));
    conveyer.setDefaultCommand(new RunConveyerCmd(conveyer, 0.0));
    intake.setDefaultCommand(new RunIntakeCmd(intake, 0.0));
  }

  private void configureButtonBindings() {
    
    new JoystickButton(xboxController, 1).onTrue(new InstantCommand(() -> swerveSubsystem.zeroHeading()));
    new JoystickButton(xboxController, 2).onTrue(new InstantCommand(() -> swerveSubsystem.resetOdometry(new Pose2d())));

    new JoystickButton(xboxController, 4).onTrue(commandSequences.runCollectorAndIntake(intake, conveyer, 1, 1));
    new JoystickButton(xboxController, 6).onTrue(new InstantCommand(() -> intake.toggleIntake()));
    new JoystickButton(xboxController, 3).onTrue(new InstantCommand(() -> grabber.togglePistion()));

  }

  public Command getAutonomousCommand() {
    return new ParallelCommandGroup( //
      commandSequences.genratePath(swerveSubsystem, List.of(new Translation2d(0,5),new Translation2d(0,-5)), new Pose2d(0,0,Rotation2d.fromDegrees(180))),
      new RunConveyerCmd(conveyer,1.0)
    );//
  }

}
