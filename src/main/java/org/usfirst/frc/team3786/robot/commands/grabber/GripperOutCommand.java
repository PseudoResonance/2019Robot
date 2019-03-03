package org.usfirst.frc.team3786.robot.commands.grabber;

import org.usfirst.frc.team3786.robot.subsystems.GrabberSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class GripperOutCommand extends Command {

	public GripperOutCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(GrabberSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		GrabberSubsystem.getInstance().setGripperSpeed(0.75); // tune later
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		GrabberSubsystem.getInstance().setGripperSpeed(0);
	}
}
