package org.usfirst.frc.team1257.robot.subsystems;

import org.usfirst.frc.team1257.robot.Constants;
import org.usfirst.frc.team1257.util.EnhancedDashboard;
import org.usfirst.frc.team1257.util.EnhancedTalonSRX;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
	
	private static DriveTrain instance = null;
	
	private EnhancedTalonSRX frontLeftDrive;
	private EnhancedTalonSRX frontRightDrive;
	private EnhancedTalonSRX backLeftDrive;
	private EnhancedTalonSRX backRightDrive;
	
	private SpeedControllerGroup leftMotors;
	private SpeedControllerGroup rightMotors;
	
	private DifferentialDrive driveTrain;
	
	private DriveTrain()
	{
		frontLeftDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_FRONT_LEFT);
		frontRightDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_FRONT_RIGHT);
		backLeftDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_BACK_LEFT);
		backRightDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_BACK_RIGHT);
		
		leftMotors = new SpeedControllerGroup(frontLeftDrive, backLeftDrive);
		rightMotors = new SpeedControllerGroup(frontRightDrive, backRightDrive);
		
		driveTrain = new DifferentialDrive(leftMotors, rightMotors);
		
		configMotors();
		resetEncoders();
	}
	
	public static DriveTrain getInstance()
	{
		if(instance == null) instance = new DriveTrain();
		return instance;
	}
	
	private void configMotors()
	{
		frontLeftDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX, Constants.CONT_CURRENT_TIMEOUT_MS);
		frontRightDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX, Constants.CONT_CURRENT_TIMEOUT_MS);
		backLeftDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX, Constants.CONT_CURRENT_TIMEOUT_MS);
		backRightDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX, Constants.CONT_CURRENT_TIMEOUT_MS);
		
		frontLeftDrive.configFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, true);
		frontRightDrive.configFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, true);
	}
	
	public void arcadeDrive(double forwardSpeed, double turnSpeed)
	{
		driveTrain.arcadeDrive(forwardSpeed, turnSpeed);
	}
	
	public void arcadeDrive(double forwardSpeed, double turnSpeed, boolean reducedSpeed)
	{
		driveTrain.arcadeDrive(forwardSpeed * Constants.DRIVE_SPEED_REDUCTION, turnSpeed * Constants.DRIVE_SPEED_REDUCTION);
	}
	
	public double getLeftEncoder()
	{
		return frontLeftDrive.getSensorValueInches();
	}
	
	public double getRightEncoder()
	{
		return frontRightDrive.getSensorValueInches();
	}
	
	public double getLeftEncoderVelocity()
	{
		return frontLeftDrive.getSensorVelocityInchesPerSecond();
	}
	
	public double getRightEncoderVelocity()
	{
		return frontRightDrive.getSensorVelocityInchesPerSecond();
	}
	
	public void resetEncoders()
	{
		frontLeftDrive.zeroSensor();
		frontRightDrive.zeroSensor();
	}
	
	public void outputInfo()
	{
		EnhancedDashboard.putNumber("Drive Left Distance", getLeftEncoder());
		EnhancedDashboard.putNumber("Drive Right Distance", getRightEncoder());
		
		EnhancedDashboard.putNumber("Drive Left Velocity", getLeftEncoderVelocity());
		EnhancedDashboard.putNumber("Drive Right Velocity", getRightEncoderVelocity());
	}
}
