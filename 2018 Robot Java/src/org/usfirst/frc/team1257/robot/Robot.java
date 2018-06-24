package org.usfirst.frc.team1257.robot;

import org.usfirst.frc.team1257.robot.auto.Baseline;
import org.usfirst.frc.team1257.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1257.robot.subsystems.Elevator;
import org.usfirst.frc.team1257.robot.subsystems.Intake;
import org.usfirst.frc.team1257.robot.subsystems.Linkage;
import org.usfirst.frc.team1257.robot.subsystems.Intake.ClawPosition;
import org.usfirst.frc.team1257.util.EnhancedDashboard;
import org.usfirst.frc.team1257.util.SnailController;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	DriveTrain driveTrain;
	Elevator elevator;
	Linkage linkage;
	Intake intake;

	SnailController driveController;
	SnailController operatorController;

	@Override
	public void robotInit() {
		driveTrain = DriveTrain.getInstance();
		elevator = Elevator.getInstance();
		linkage = Linkage.getInstance();
		intake = Intake.getInstance();

		driveController = new SnailController(0);
		operatorController = new SnailController(1);

		// Initialize all SmartDashboard values
		outputInfo();
	}

	@Override
	public void autonomousInit() {
		resetAll();
		EnhancedDashboard.putString("Auto Status");
		
		Baseline.run(driveTrain, elevator, intake, linkage);
	}

	@Override
	public void autonomousPeriodic() {
		outputInfo();
	}

	@Override
	public void teleopInit() {
		resetAll();
	}

	@Override
	public void teleopPeriodic() {
		// Driving
		driveTrain.arcadeDrive(driveController.getForwardSpeed(), driveController.getTurnSpeed());

		// Elevator
		elevator.setElevator(operatorController.getTriggerAxis(Hand.kRight),
				operatorController.getTriggerAxis(Hand.kLeft), operatorController.getStartButton());

		// Linkage
		linkage.setLinkage(operatorController.getY(Hand.kLeft));

		// Intake
		if (operatorController.getBumper(Hand.kRight)) {
			intake.setClaw(ClawPosition.CLOSED);
		}
		else if (operatorController.getBumper(Hand.kLeft)) {
			intake.setIntake(Constants.INTAKE_SPEED);
			intake.setClaw(ClawPosition.OPEN);
		}
		else if (operatorController.getBButton()) {
			intake.setIntake(Constants.INTAKE_SPEED);
		}
		else if (operatorController.getAButton()) {
			intake.setIntake(-Constants.INTAKE_SPEED);
		}
		else {
			intake.setIntake(Constants.deadband(operatorController.getY(Hand.kRight)));
		}

		outputInfo();
	}

	private void outputInfo() {
		driveTrain.outputInfo();
		elevator.outputInfo();
		linkage.outputInfo();
		intake.outputInfo();
	}

	private void resetAll() {
		resetSensors();
		disablePID();
		zeroMotors();
	}

	private void resetSensors() {
		driveTrain.resetSensors();
		elevator.resetEncoder();
	}

	private void disablePID() {

	}

	private void zeroMotors() {
		driveTrain.arcadeDrive(0, 0);
		elevator.setElevator(0, 0, false);
		linkage.setLinkage(0);
		intake.setIntake(0);
	}
}
