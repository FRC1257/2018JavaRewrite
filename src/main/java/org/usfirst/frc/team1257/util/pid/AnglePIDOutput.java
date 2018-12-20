package org.usfirst.frc.team1257.util.pid;

import org.usfirst.frc.team1257.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1257.util.EnhancedDashboard;

import edu.wpi.first.wpilibj.PIDOutput;

// This is PID. 
// PID helps us run precisely.

public class AnglePIDOutput implements PIDOutput {
	
	private DriveTrain driveTrain;
	private double prevOutput;
	private DistancePIDHelper distancePIDHelper;
	
	public AnglePIDOutput(DriveTrain driveTrain) {
		this.driveTrain = driveTrain;
		this.prevOutput = 0;
		this.distancePIDHelper = null;
	}

	// NEVER write PID like this again. PLEASE
	// This is a recursive formula, because we have two PIDControllers:
	// one that controls direction and one that controls magnitude
	@Override
	public void pidWrite(double output) {
		EnhancedDashboard.putNumber("Angle PID Output", output);

		// drive is the result that this PID control loop outputs

		// if distancePIDHelper is null set to 0
		// Otherwise set it to the previous output, from distancePIDHelper
		double drive = distancePIDHelper == null ? 0 : distancePIDHelper.getPrevOutput();
		driveTrain.arcadeDrive(drive, output, false);

		// output is used between the two PIDControllers
		prevOutput = output;
	}
	
	public double getPrevOutput()
	{
		return prevOutput;
	}
	
	public void setDistancePIDHelper(DistancePIDHelper distancePIDHelper)
	{
		this.distancePIDHelper = distancePIDHelper;
	}
}
