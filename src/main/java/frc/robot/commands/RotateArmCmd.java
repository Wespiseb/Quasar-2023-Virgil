package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
//import frc.robot.subsystems.Conveyor;

public class RotateArmCmd extends CommandBase {

    private final Arm arm;
    private final double angle;

    public RotateArmCmd(Arm arm, Double angle) {
        this.arm = arm;
        this.angle = angle;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        System.out.println("RotateArmCmd Started with angle: " + angle);
        arm.runWithKP(angle);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("RotateArmCmd ended!");
    }

    @Override
    public boolean isFinished() {
        return Math.abs(arm.getPosition() - angle) < 0.2;
    }
}
