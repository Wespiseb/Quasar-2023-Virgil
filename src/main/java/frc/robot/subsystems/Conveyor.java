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
        // makes conveyor move in opposite direction
        conveyer.setInverted(ConveyerConstants.kConveyerEncoderReversed);
    }

    public double getPosition() {
        // multiply current conveyor position by the encoders counts
        return conveyer.getSelectedSensorPosition()*ConveyerConstants.kConveyerEncoderCountstoMeters;
    }

    public double getVelocity() {
        // Multiply current velocity by Conveyor encoder
        return conveyer.getSelectedSensorVelocity()*ConveyerConstants.kConveyerEncoderRPMS2MPS;
    }

    public void resetEncoders() {
        // Resets encoder value to 0
        conveyer.setSelectedSensorPosition(0);
    }

    public void run(double speed) {
        //Runs conveyor
        conveyer.set(ControlMode.PercentOutput, speed);
    }

    public void runWithKP(double targetPos) {
        // moves until it gets to a certain position and then converts so that Target position so it is in meters
        conveyer.set(ControlMode.PercentOutput, pidControler.calculate(getPosition(), targetPos*ConveyerConstants.kConveyerEncoderCountstoMeters));
    }
    
    @Override
    public void periodic() {}
}
