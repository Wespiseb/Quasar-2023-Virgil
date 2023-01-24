package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ConveyerConstants;

public class Conveyor extends SubsystemBase {
    private final TalonFX conveyer = new TalonFX(ConveyerConstants.kConveyerMotorPort);
    private final PIDController pidControler = new PIDController(ConveyerConstants.kPConveyer, 0, 0);

    public Conveyor() {
        conveyer.setInverted(ConveyerConstants.kConveyerEncoderReversed);
    }

    public double getPosition() {
        return conveyer.getSelectedSensorPosition()*ConveyerConstants.kConveyerEncoderCountstoMeters;
    }

    public double getVelocity() {
        return conveyer.getSelectedSensorVelocity()*ConveyerConstants.kConveyerEncoderRPMS2MPS;
    }

    public void resetEncoders() {
        conveyer.setSelectedSensorPosition(0);
    }

    public void run(double speed) {
        conveyer.set(ControlMode.PercentOutput, speed);
    }

    public void runWithKP(double targetPos) {
        conveyer.set(ControlMode.PercentOutput, pidControler.calculate(getPosition(), targetPos*ConveyerConstants.kConveyerEncoderCountstoMeters));
    }
    
    @Override
    public void periodic() {}
}
