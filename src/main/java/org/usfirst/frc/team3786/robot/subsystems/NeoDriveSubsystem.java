package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Mappings;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.Dashboard;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class NeoDriveSubsystem extends Subsystem {

	private static NeoDriveSubsystem instance;

	private CANSparkMax left;
	private CANSparkMax right;
	private CANSparkMax leftFollow;
	private CANSparkMax rightFollow;

	private DifferentialDrive differentialDrive;

	private boolean boost = false;
	private boolean kkBrake = false;
	private boolean slowTurn = false;

	public static NeoDriveSubsystem getInstance() {
		if (instance == null)
			instance = new NeoDriveSubsystem();
		return instance;
	}

	public NeoDriveSubsystem() {
		left = new CANSparkMax(Mappings.left1Motor, MotorType.kBrushless);
		left.setIdleMode(IdleMode.kCoast);
		right = new CANSparkMax(Mappings.right1Motor, MotorType.kBrushless);
		right.setIdleMode(IdleMode.kCoast);
		leftFollow = new CANSparkMax(Mappings.leftFollowMotor, MotorType.kBrushless);
		leftFollow.setIdleMode(IdleMode.kCoast);
		rightFollow = new CANSparkMax(Mappings.rightFollowMotor, MotorType.kBrushless);
		rightFollow.setIdleMode(IdleMode.kCoast);
		left.setSmartCurrentLimit(40);
		right.setSmartCurrentLimit(40);
		leftFollow.setSmartCurrentLimit(40);
		rightFollow.setSmartCurrentLimit(40);
		left.setOpenLoopRampRate(0.4);
		right.setOpenLoopRampRate(0.4);
		leftFollow.setOpenLoopRampRate(0.4);
		rightFollow.setOpenLoopRampRate(0.4);

		leftFollow.follow(left);
		rightFollow.follow(right);

		differentialDrive = new DifferentialDrive(left, right);
	}

	public void setMotorSpeeds(double leftSpeed, double rightSpeed) {
		left.set(leftSpeed);
		right.set(-rightSpeed);
		Dashboard.getInstance().putNumber(false, "Left Motor Speed", leftSpeed);
		Dashboard.getInstance().putNumber(false, "Right Motor Speed", rightSpeed);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void arcadeDrive(double speed, double turnRate) {
		if (this.kkBrake) {
			speed *= 0.0;
			turnRate *= 0.0;
		} else if (!this.boost) {
			if (this.slowTurn) {
				speed *= 0.3;
				turnRate *= 0.3;
			}
			else {
				speed *= 0.6;
				turnRate *= 0.8;
			}
		}
		Dashboard.getInstance().putBoolean(true, "Boost", boost);
		Dashboard.getInstance().putBoolean(true, "Break", kkBrake);
		Dashboard.getInstance().putNumber(false, "Driving Speed", speed);
		Dashboard.getInstance().putNumber(false, "TurnRate", turnRate);

		differentialDrive.arcadeDrive(-speed, turnRate);
	}

	public void setkkBrake(boolean kBrake) {
		this.kkBrake = kBrake;
		if (this.kkBrake) {
			left.setIdleMode(IdleMode.kBrake);
			right.setIdleMode(IdleMode.kBrake);
			leftFollow.setIdleMode(IdleMode.kBrake);
			rightFollow.setIdleMode(IdleMode.kBrake);
		} else {
			left.setIdleMode(IdleMode.kCoast);
			right.setIdleMode(IdleMode.kCoast);
			leftFollow.setIdleMode(IdleMode.kCoast);
			rightFollow.setIdleMode(IdleMode.kCoast);
		}
	}

	public void setBoost(boolean boost) {
		this.boost = boost;
	}

	public boolean getBoost() {
		return this.boost;
	}

	public void setSlowTurn(boolean slowTurn) {
		this.slowTurn = slowTurn;
	}

	public boolean getSlowTurn() {
		return this.slowTurn;
	}

	public void gyroStraight(double spd, double tgtHeading) {
		double currHeading = Gyroscope.getInstance().getHeadingContinuous();
		double error = tgtHeading - currHeading;
		double correction = error / 30;
		if(correction > 1.0) {
			correction = 1.0;
		}
		else if(correction < -1.0) {
			correction = -1.0;
		}
		arcadeDrive(spd, correction);
	}
}
