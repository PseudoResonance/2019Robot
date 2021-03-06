/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.grabber;

import org.usfirst.frc.team3786.robot.subsystems.GrabberTiltSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class TiltUpCommand extends Command {
	public TiltUpCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(GrabberTiltSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		GrabberTiltSubsystem.getInstance().setTiltSpeed(0.6); // tune later
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
		GrabberTiltSubsystem.getInstance().setTiltSpeed(0.0);
	}
}
