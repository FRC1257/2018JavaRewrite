package org.usfirst.frc.team1257.robot.subsystems;

import org.usfirst.frc.team1257.robot.Constants;
import org.usfirst.frc.team1257.util.EnhancedDashboard;
import org.usfirst.frc.team1257.util.EnhancedTalonSRX;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
	
	private EnhancedTalonSRX frontLeftDrive;
	private EnhancedTalonSRX frontRightDrive;
	private EnhancedTalonSRX backLeftDrive;
	private EnhancedTalonSRX backRightDrive;
	
	private SpeedControllerGroup leftMotors;
	private SpeedControllerGroup rightMotors;
	
	private DifferentialDrive driveTrain;
	
	public DriveTrain()
	{
		frontLeftDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_FRONT_LEFT);
		frontRightDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_FRONT_RIGHT);
		backLeftDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_BACK_LEFT);
		backRightDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_BACK_RIGHT);
		
		leftMotors = new SpeedControllerGroup(frontLeftDrive, backLeftDrive);
		rightMotors = new SpeedControllerGroup(frontRightDrive, backRightDrive);
		
		driveTrain = new DifferentialDrive(leftMotors, rightMotors);
		
		configMotors();
		outputInfo();
	}
	
	private void configMotors()
	{
		frontLeftDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX);
		frontRightDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX);
		backLeftDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX);
		backRightDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX);
		
		frontLeftDrive.configFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, true);
		frontRightDrive.configFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, true);
	}
	
	public void arcadeDrive(double forwardSpeed, double turnSpeed)
	{
		driveTrain.arcadeDrive(forwardSpeed, turnSpeed);
	}
	
	public double getLeftEncoder()
	{
		return frontLeftDrive.getSensorValueInches();
	}
	
	public double getRightEncoder()
	{
		return frontRightDrive.getSensorValueInches();
	}
	
	public void resetEncoders()
	{
		frontLeftDrive.zeroSensor();
		frontRightDrive.zeroSensor();
	}
	
	public void outputInfo()
	{
		EnhancedDashboard.putNumber("Drive Encoder Left", getLeftEncoder());
		EnhancedDashboard.putNumber("Drive Encoder Right", getRightEncoder());
	}
}
