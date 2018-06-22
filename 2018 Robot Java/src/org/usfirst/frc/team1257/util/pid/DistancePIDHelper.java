package org.usfirst.frc.team1257.util.pid;

import org.usfirst.frc.team1257.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1257.util.EnhancedDashboard;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DistancePIDHelper implements PIDSource, PIDOutput {

	private DriveTrain driveTrain;
	private double prevOutput;
	private AnglePIDOutput anglePIDOutput;
	
	public DistancePIDHelper(DriveTrain driveTrain)
	{
		this.driveTrain = driveTrain;
		this.prevOutput = 0;
		this.anglePIDOutput = null;
	}

	@Override
	public void pidWrite(double output) {
		EnhancedDashboard.putNumber("Distance PID Output", output);
		
		double turn = anglePIDOutput == null ? 0 : anglePIDOutput.getPrevOutput();
		driveTrain.arcadeDrive(output, turn, false);
		prevOutput = output;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return driveTrain.getLeftEncoder();
	}
	
	public double getPrevOutput()
	{
		return prevOutput;
	}
	
	public void setAnglePIDOutput(AnglePIDOutput anglePIDOutput)
	{
		this.anglePIDOutput = anglePIDOutput;
	}
}
