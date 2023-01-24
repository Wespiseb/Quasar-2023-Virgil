package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class RunIntakeCmd extends CommandBase {

    private final Intake intake;
    private final double speed;

    public RunIntakeCmd(Intake intake, Double speed) {
        this.intake = intake;
        this.speed = speed;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        System.out.println("RunIntakeCmd Started with Speed: " + speed);
        intake.run(speed);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("RunInatkeCmd ended!");
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}