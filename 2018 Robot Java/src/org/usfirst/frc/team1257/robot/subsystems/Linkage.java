package org.usfirst.frc.team1257.robot.subsystems;

import org.usfirst.frc.team1257.robot.Constants;
import org.usfirst.frc.team1257.util.EnhancedTalonSRX;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Linkage {

	private static Linkage instance = null;

	private EnhancedTalonSRX linkageMotor;

	private Linkage() {
		linkageMotor = new EnhancedTalonSRX(Constants.ElectricLayout.MOTOR_LINKAGE);

		configMotors();
	}

	public static Linkage getInstance() {
		if (instance == null)
			instance = new Linkage();
		return instance;
	}

	private void configMotors() {
		linkageMotor.setNeutralMode(NeutralMode.Brake);
	}

	public void setLinkage(double speed) {
		linkageMotor.set(Constants.deadband(speed));
	}

	public void outputInfo() {

	}
}
