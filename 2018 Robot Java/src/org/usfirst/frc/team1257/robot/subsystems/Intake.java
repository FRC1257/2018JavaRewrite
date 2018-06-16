package org.usfirst.frc.team1257.robot.subsystems;

import org.usfirst.frc.team1257.robot.Constants;
import org.usfirst.frc.team1257.util.DoubleSolenoidControllerGroup;
import org.usfirst.frc.team1257.util.EnhancedDoubleSolenoid;
import org.usfirst.frc.team1257.util.EnhancedTalonSRX;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Intake {

	public static enum ClawPosition
	{
		CLOSED,
		OPEN
	}
	
	private static Intake instance = null;
	
	private EnhancedTalonSRX rightIntakeMotor;
	private EnhancedTalonSRX leftIntakeMotor;
	
	private EnhancedDoubleSolenoid rightIntakeSolenoid;
	private EnhancedDoubleSolenoid leftIntakeSolenoid;
	
	private SpeedControllerGroup intakeMotors;
	private DoubleSolenoidControllerGroup solenoidMotors;
	
	private Intake()
	{
		rightIntakeMotor = new EnhancedTalonSRX(Constants.ElectricLayout.MOTOR_INTAKE_RIGHT);
		leftIntakeMotor = new EnhancedTalonSRX(Constants.ElectricLayout.MOTOR_INTAKE_LEFT);
		
		rightIntakeSolenoid = new EnhancedDoubleSolenoid(Constants.ElectricLayout.SOLENOID_INTAKE_RIGHT_FORWARD, 
				Constants.ElectricLayout.SOLENOID_INTAKE_RIGHT_REVERSE);
		leftIntakeSolenoid = new EnhancedDoubleSolenoid(Constants.ElectricLayout.SOLENOID_INTAKE_LEFT_FORWARD, 
				Constants.ElectricLayout.SOLENOID_INTAKE_LEFT_REVERSE);
		
		intakeMotors = new SpeedControllerGroup(rightIntakeMotor, leftIntakeMotor);
		solenoidMotors = new DoubleSolenoidControllerGroup(rightIntakeSolenoid, leftIntakeSolenoid);
				
		configMotors();
	}
	
	public static Intake getInstance()
	{
		if(instance == null) instance = new Intake();
		return instance;
	}
	
	private void configMotors()
	{
		rightIntakeMotor.setNeutralMode(NeutralMode.Brake);
		leftIntakeMotor.setNeutralMode(NeutralMode.Brake);
		
		rightIntakeMotor.enableCurrentLimit(Constants.THIRTY_AMP_FUSE_CONT_MAX, Constants.CONT_CURRENT_TIMEOUT_MS);
		leftIntakeMotor.enableCurrentLimit(Constants.THIRTY_AMP_FUSE_CONT_MAX, Constants.CONT_CURRENT_TIMEOUT_MS);
	}
	
	public void setClaw(ClawPosition position)
	{
		if(position == ClawPosition.CLOSED) solenoidMotors.set(Value.kForward);
		else if(position == ClawPosition.OPEN) solenoidMotors.set(Value.kReverse);
	}

	public void setIntake(double speed)
	{
		intakeMotors.set(speed);
	}
	
	public void outputInfo()
	{
		
	}
}
