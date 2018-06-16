package org.usfirst.frc.team1257.robot.subsystems;

import org.usfirst.frc.team1257.robot.Constants;
import org.usfirst.frc.team1257.util.EnhancedDashboard;
import org.usfirst.frc.team1257.util.EnhancedTalonSRX;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Elevator {
	
	private static Elevator instance = null;
	
	private EnhancedTalonSRX rightElevatorMotor;
	private EnhancedTalonSRX leftElevatorMotor;
	
	private SpeedControllerGroup elevatorMotors;
	
	private Elevator()
	{
		rightElevatorMotor = new EnhancedTalonSRX(Constants.ElectricLayout.MOTOR_ELEVATOR_RIGHT);
		leftElevatorMotor = new EnhancedTalonSRX(Constants.ElectricLayout.MOTOR_ELEVATOR_LEFT);
		
		elevatorMotors = new SpeedControllerGroup(rightElevatorMotor, leftElevatorMotor);
		
		configMotors();
		resetEncoder();
	}
	
	public static Elevator getInstance()
	{
		if(instance != null) instance = new Elevator();
		return instance;
	}
	
	private void configMotors()
	{
		rightElevatorMotor.configFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, true);
		rightElevatorMotor.setNeutralMode(NeutralMode.Brake);
		
//		rightElevatorMotor.enableCurrentLimit(Constants.ELEVATOR_CONT_CURRENT_MAX, Constants.ELEVATOR_CONT_CURRENT_TIMEOUT_MS);
	}
	
	public double getEncoder()
	{
		return rightElevatorMotor.getSensorValueInches();
	}
	
	public void resetEncoder()
	{
		rightElevatorMotor.zeroSensor();
	}
	
	public boolean isElevatorHigh()
	{
		return getEncoder() > 12.5;
	}
	
	public void set(double raise, double lower, boolean override)
	{
		double elevatorSpeed = Constants.deadband(Math.abs(raise)) - Constants.deadband(Math.abs(lower));
		if(!override) elevatorSpeed = capElevatorOutput(elevatorSpeed);
		
		elevatorMotors.set(elevatorSpeed);
	}
	
	private double capElevatorOutput(double output)
	{
		double cappedOutput = output;
		if(output < 0 && getEncoder() <= 0) cappedOutput = 0;
		else if(output < 0 && getEncoder() <= 5) cappedOutput *= Constants.ELEVATOR_SPEED_REDUCTION;
		else if(output > 0 && getEncoder() >= Constants.ELEVATOR_MAX_HEIGHT) cappedOutput = 0;
		else if(output > 0 && getEncoder() >= Constants.ELEVATOR_MAX_HEIGHT - 5) cappedOutput *= Constants.ELEVATOR_SPEED_REDUCTION;
		
		return cappedOutput;
	}
	
	public void outputInfo()
	{
		EnhancedDashboard.putNumber("Elevator Height", getEncoder());
	}
}
