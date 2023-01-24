package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class RunConveyerCmd extends CommandBase {

    private final Conveyor conveyer;
    private final double speed;

    public RunConveyerCmd(Conveyor conveyer, Double speed) {
        this.conveyer = conveyer;
        this.speed = speed;
        addRequirements(conveyer);
    }

    @Override
    public void initialize() {
        System.out.println("RunConveyerCmd Started with Speed: " + speed);
        conveyer.run(speed);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("RunConveyerCmd ended!");
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
