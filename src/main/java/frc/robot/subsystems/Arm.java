package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;


public class Arm extends SubsystemBase{
  private final DoubleSolenoid extendPistion = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ArmConstants.kArmPneumaticFwd, ArmConstants.kArmPneumaticBwrd);
  private final TalonSRX rotateMotor = new TalonSRX(ArmConstants.kRotater);
  private final PIDController pidControler = new PIDController(ArmConstants.kRotaterKP, 0, 0);

  public Arm() {
    rotateMotor.setInverted(ArmConstants.kRotaterEncoderReversed);
  }

  public double getPosition() {
    return rotateMotor.getSelectedSensorPosition()*ArmConstants.kRotaterEncoderCountstoAngle;
  }

  public void resetEncoders() {
    rotateMotor.setSelectedSensorPosition(0);
  }

  public void run(Double speed) {
    rotateMotor.set(ControlMode.PercentOutput, speed);
  }

  public void runWithKP(double targetPos) {
    rotateMotor.set(ControlMode.PercentOutput, pidControler.calculate(getPosition(), targetPos*ArmConstants.kRotaterEncoderCountstoAngle));
  }

  public void pushPistion() {
    extendPistion.set(Value.kForward);
  }

  public void retreactPistion() {
    extendPistion.set(Value.kReverse);
  }

  public void turnOffPiston() {
    extendPistion.set(Value.kOff);
  }

  public void togglePistion() {
    extendPistion.toggle();
  }

  @Override
  public void periodic() {}
}
