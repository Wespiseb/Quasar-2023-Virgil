package frc.robot.commands;

import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.SwerveSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class BalanceOnBoardCmd extends CommandBase {

  private SwerveSubsystem swerveSubsystem;

  private double currentAngle;
  private double error;
  private double driveSpeed;

  private int balanceTimer;

  private PIDController pidController = new PIDController(DriveConstants.kBoardBalancedDriveKP, 0, DriveConstants.kBoardBalancedDriveKD);

  public BalanceOnBoardCmd(SwerveSubsystem m_SwerveSubsystem) {
    this.swerveSubsystem = m_SwerveSubsystem;
    addRequirements(m_SwerveSubsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    this.currentAngle = swerveSubsystem.getTrueAngle();

    error = DriveConstants.kBoardBalancedGoalDegrees - currentAngle;
    driveSpeed = -DriveConstants.kPhysicalMaxSpeedMetersPerSecond*pidController.calculate(error, DriveConstants.kBoardBalancedGoalDegrees);

    if (Math.abs(driveSpeed) > DriveConstants.kBoardBalancedSpeed) {
      driveSpeed = Math.copySign(DriveConstants.kBoardBalancedSpeed, driveSpeed);
    }

    swerveSubsystem.driveDirection(driveSpeed, 0);

  }

  @Override
  public void end(boolean interrupted) {
    swerveSubsystem.stopModules();
  }

  @Override
  public boolean isFinished() {
    if (error <= 0.1 && error >= -0.1) {
      if (balanceTimer == 0) {
        balanceTimer = DriveConstants.kBalanceCounter;
        return Math.abs(error) < DriveConstants.kBoardBalancedAngleThresholdDegrees; 
      }
      else {
        balanceTimer--;
        return false;
      }
    }
    return false;
  }
}