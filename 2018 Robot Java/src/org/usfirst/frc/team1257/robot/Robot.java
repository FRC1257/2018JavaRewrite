package org.usfirst.frc.team1257.robot;

import org.usfirst.frc.team1257.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	DriveTrain driveTrain;
	
	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {
		outputInfo();
	}
	
	@Override
	public void teleopInit() {
		
	}

	@Override
	public void teleopPeriodic() {
		outputInfo();
	}
	
	private void outputInfo()
	{
		driveTrain.outputInfo();
	}
}
