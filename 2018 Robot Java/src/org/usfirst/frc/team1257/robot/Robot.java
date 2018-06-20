package org.usfirst.frc.team1257.robot;

import org.usfirst.frc.team1257.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1257.robot.subsystems.Elevator;
import org.usfirst.frc.team1257.robot.subsystems.Intake;
import org.usfirst.frc.team1257.robot.subsystems.Intake.ClawPosition;
import org.usfirst.frc.team1257.util.SnailController;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	DriveTrain driveTrain;
	Elevator elevator;
	Intake intake;

	SnailController driveController;
	SnailController operatorController;

	@Override
	public void robotInit() {
		driveTrain = DriveTrain.getInstance();
		elevator = Elevator.getInstance();
		intake = Intake.getInstance();

		driveController = new SnailController(0);
		operatorController = new SnailController(1);

		// Initialize all SmartDashboard values
		outputInfo();
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
		// Driving
		driveTrain.arcadeDrive(driveController.getForwardSpeed(), driveController.getTurnSpeed());

		// Elevator
		elevator.set(operatorController.getTriggerAxis(Hand.kRight), operatorController.getTriggerAxis(Hand.kLeft),
				operatorController.getStartButton());

		// Intake Pneumatics
		if (operatorController.getBumper(Hand.kLeft))
			intake.setClaw(ClawPosition.CLOSED);
		else if (operatorController.getBumper(Hand.kRight))
			intake.setClaw(ClawPosition.OPEN);

		// Intake Wheels
		double intakeSpeed = Constants.deadband(operatorController.getY(Hand.kRight));
		if (operatorController.getBButton())
			intake.setIntake(Constants.INTAKE_SPEED);
		else if (operatorController.getAButton())
			intake.setIntake(-Constants.INTAKE_SPEED);
		else
			intake.setIntake(intakeSpeed);

		outputInfo();
	}

	private void outputInfo() {
		driveTrain.outputInfo();
		elevator.outputInfo();
		intake.outputInfo();
	}
}
