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
  // Declares arm/hand rotator
  private final TalonSRX rotateMotor = new TalonSRX(ArmConstants.kRotater);
  private final PIDController pidControler = new PIDController(ArmConstants.kRotaterKP, 0, 0);

  public Arm() {
    rotateMotor.setInverted(ArmConstants.kRotaterEncoderReversed);
  }

  public double getPosition() {
    // Changes number to angle measurement
    return rotateMotor.getSelectedSensorPosition()*ArmConstants.kRotaterEncoderCountstoAngle;
  }

  public void resetEncoders() {
    // sets the number of encoder to 0
    rotateMotor.setSelectedSensorPosition(0);
  }

  public void run(Double speed) {
    // makes arm move
    rotateMotor.set(ControlMode.PercentOutput, speed);
  }

  public void runWithKP(double targetPos) {
    // runs arm to a specific position runWithKP(1000);
    rotateMotor.set(ControlMode.PercentOutput, pidControler.calculate(getPosition(), targetPos*ArmConstants.kRotaterEncoderCountstoAngle));
  }

  public void pushPistion() {
    // Pushes arm out
    extendPistion.set(Value.kForward);
  }

  public void retreactPistion() {
    // Pulls arm back in
    extendPistion.set(Value.kReverse);
  }

  public void turnOffPiston() {
    // Empty's piston of all air/pressure
    extendPistion.set(Value.kOff);
  }

  public void togglePistion() {
    //Switiches from reverse to forward or vise versa
    extendPistion.toggle();
  }

  @Override
  public void periodic() {}
}
