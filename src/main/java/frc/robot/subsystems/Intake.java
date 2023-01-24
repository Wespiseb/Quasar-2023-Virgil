// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private final TalonFX intakeWheel = new TalonFX(IntakeConstants.kMotorPort);
  private final DoubleSolenoid leftPistion = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kLeftPistonForwardPort, IntakeConstants.kLeftPistonBackwardPort);
  private final DoubleSolenoid rightPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kRightPistonForwardPort, IntakeConstants.kLeftPistonBackwardPort);

  public void run(double speed) {
    intakeWheel.set(ControlMode.PercentOutput, speed);
  }

  public void pushIntake() {
    // Pushes intake out to grab cube or cone
    leftPistion.set(Value.kForward);
    rightPiston.set(Value.kForward);
  }

  public void retreactIntake() {
    // Pulls intake in when not grabbing cone or cube
    leftPistion.set(Value.kReverse);
    rightPiston.set(Value.kReverse);
  }

  public void turnOffIntake() {
    // Depressureizes the intake
    leftPistion.set(Value.kOff);
    rightPiston.set(Value.kOff);
  }

  public void toggleIntake() {
    //Switiches from reverse to forward or vise versa
    leftPistion.toggle();
    rightPiston.toggle();
  }

  @Override
  public void periodic() {

  }
}
