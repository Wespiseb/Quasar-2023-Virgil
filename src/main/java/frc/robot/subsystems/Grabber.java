// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.GrabberConstants;

public class Grabber extends SubsystemBase {
  //the example pistion is included in case we need to use a pistion next year
  private final DoubleSolenoid rightPistion = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, GrabberConstants.kRightPistonForwardPort, GrabberConstants.kRightPistonBackwardPort);
  private final DoubleSolenoid leftPistion = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, GrabberConstants.kLeftPistonForwardPort, GrabberConstants.kLeftPistonBackwardPort);

  public void pushPistion() {
    // push arm pistons out
    rightPistion.set(Value.kForward);
    leftPistion.set(Value.kForward);
  }

  public void retreactPistion() {
    // pulls arm piston back in
    rightPistion.set(Value.kReverse);
    leftPistion.set(Value.kReverse);
  }

  public void turnOffclampL() {
    // Decompress
    rightPistion.set(Value.kOff);
    leftPistion.set(Value.kOff);
  }

  public void togglePistion() {
    //Switiches from reverse to forward or vise versa
    rightPistion.toggle();
    leftPistion.toggle();
  }

  @Override
  public void periodic() {

  }
}
