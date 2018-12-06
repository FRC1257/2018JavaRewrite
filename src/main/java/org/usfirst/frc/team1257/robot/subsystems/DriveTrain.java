package org.usfirst.frc.team1257.robot.subsystems;

import org.usfirst.frc.team1257.robot.Constants;
import org.usfirst.frc.team1257.util.AngleSensorGroup;
import org.usfirst.frc.team1257.util.EnhancedDashboard;
import org.usfirst.frc.team1257.util.EnhancedTalonSRX;
import org.usfirst.frc.team1257.util.pid.AnglePIDOutput;
import org.usfirst.frc.team1257.util.pid.DistancePIDHelper;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

// This is important. This makes the robot drive. That is very important.
// Again: if this code is wrong our robot will not drive properly.

public class DriveTrain {

	private static DriveTrain instance = null;

	// Defining motors for driving.

	private EnhancedTalonSRX frontLeftDrive;
	private EnhancedTalonSRX frontRightDrive;
	private EnhancedTalonSRX backLeftDrive;
	private EnhancedTalonSRX backRightDrive;

	// Separating the motors into left and right. 
	// Also, DifferentialDrive can only take in two inputs.

	private SpeedControllerGroup leftMotors;
	private SpeedControllerGroup rightMotors;

	private DifferentialDrive driveTrain;

	// Determining the direction we drive at.

	private AngleSensorGroup angleSensorGroup;

	// This is PID stuff. 
	// PID helps us move precisely.

	private PIDController distanceController;
	private PIDController maintainAngleController;
	private PIDController turnAngleController;
	private DistancePIDHelper distancePIDHelper;
	private AnglePIDOutput anglePIDOutput;

	private DriveTrain() {
		// Defining the motors

		frontLeftDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_FRONT_LEFT);
		frontRightDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_FRONT_RIGHT);
		backLeftDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_BACK_LEFT);
		backRightDrive = new EnhancedTalonSRX(Constants.ElectricLayout.DRIVE_BACK_RIGHT);

		// And the SpeedControllerGroups

		leftMotors = new SpeedControllerGroup(frontLeftDrive, backLeftDrive);
		rightMotors = new SpeedControllerGroup(frontRightDrive, backRightDrive);

		driveTrain = new DifferentialDrive(leftMotors, rightMotors);

		angleSensorGroup = new AngleSensorGroup(SPI.Port.kMXP, SPI.Port.kOnboardCS0);

		configMotors();
		configPIDControllers();
		resetSensors();
	}

	// Singleton pattern so only one instance is created
	public static DriveTrain getInstance() {
		if (instance == null)
			instance = new DriveTrain();
		return instance;
	}

	private void configMotors() {
		frontLeftDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX, Constants.CONT_CURRENT_TIMEOUT_MS);
		frontRightDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX, Constants.CONT_CURRENT_TIMEOUT_MS);
		backLeftDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX, Constants.CONT_CURRENT_TIMEOUT_MS);
		backRightDrive.enableCurrentLimit(Constants.FORTY_AMP_FUSE_CONT_MAX, Constants.CONT_CURRENT_TIMEOUT_MS);

		frontLeftDrive.configFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, true);
		frontRightDrive.configFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, true);
	}

	private void configPIDControllers() {
		distancePIDHelper = new DistancePIDHelper(this);
		anglePIDOutput = new AnglePIDOutput(this);

		distanceController = new PIDController(Constants.PID.DISTANCE_CONSTANTS[0], Constants.PID.DISTANCE_CONSTANTS[1],
				Constants.PID.DISTANCE_CONSTANTS[2], distancePIDHelper, distancePIDHelper);
		maintainAngleController = new PIDController(Constants.PID.MAINTAIN_CONSTANTS[0],
				Constants.PID.MAINTAIN_CONSTANTS[1], Constants.PID.MAINTAIN_CONSTANTS[2], angleSensorGroup,
				anglePIDOutput);
		turnAngleController = new PIDController(Constants.PID.TURN_CONSTANTS[0], Constants.PID.TURN_CONSTANTS[1],
				Constants.PID.TURN_CONSTANTS[2], angleSensorGroup, anglePIDOutput);
		
		distanceController.setAbsoluteTolerance(Constants.PID.DISTANCE_ABS_TOL);
		distanceController.setOutputRange(-Constants.PID.DISTANCE_OUTPUT_RANGE, Constants.PID.DISTANCE_OUTPUT_RANGE);

		maintainAngleController.setAbsoluteTolerance(Constants.PID.MAINTAIN_ABS_TOL);
		maintainAngleController.setOutputRange(-Constants.PID.MAINTAIN_OUTPUT_RANGE,
				Constants.PID.MAINTAIN_OUTPUT_RANGE);

		turnAngleController.setAbsoluteTolerance(Constants.PID.TURN_ABS_TOL);
		turnAngleController.setOutputRange(-Constants.PID.TURN_OUTPUT_RANGE, Constants.PID.TURN_OUTPUT_RANGE);
	
		// Add all PID Controllers to LiveWindow for configuration
		LiveWindow.add(distanceController);
		LiveWindow.add(maintainAngleController);
		LiveWindow.add(turnAngleController);
	}

	public void arcadeDrive(double forwardSpeed, double turnSpeed) {
		driveTrain.arcadeDrive(forwardSpeed, turnSpeed, false);
	}

	/* Drives forward at the forward speed and turns around its center at the turn speed
	 * If reducedSpeed is true, the drive train will drive at a slower speed
	 */
	public void arcadeDrive(double forwardSpeed, double turnSpeed, boolean reducedSpeed) {
		driveTrain.arcadeDrive(forwardSpeed * (reducedSpeed ? Constants.DRIVE_SPEED_REDUCTION : 1),
				turnSpeed * (reducedSpeed ? Constants.DRIVE_SPEED_REDUCTION : 1));
	}
	
	public void driveDistance(double distance) {
		driveDistance(distance, Constants.PID_TIMEOUT_S);
	}

	public void driveDistance(double distance, double timeout) {
		driveDistance(distance, timeout, true);
	}

	public void driveDistance(double distance, boolean reset) {
		driveDistance(distance, Constants.PID_TIMEOUT_S, reset);
	}

	/* Drives a certain distance in a straight line
	 * If the timeout is gone over, the command will stop
	 * If reset is true, the drive encoders will be reset before driving
	 */
	public void driveDistance(double distance, double timeout, boolean reset) {
		EnhancedDashboard.putString("Auto Status", "Driving " + distance + " inches");
		if (reset) resetEncoders();
		angleSensorGroup.reset();
		turnAngleController.disable();

		// Set PID values

		distancePIDHelper.setAnglePIDOutput(anglePIDOutput);
		anglePIDOutput.setDistancePIDHelper(distancePIDHelper);

		maintainAngleController.reset();
		maintainAngleController.setSetpoint(0);

		distanceController.reset();
		distanceController.setSetpoint(distance);

		// Drive according to the PID control loop

		maintainAngleController.enable();
		distanceController.enable();

		waitUntilPIDSteady(distanceController, timeout);

		EnhancedDashboard.putString("Auto Status", "Drive Complete");
	}
	
	public void turnAngle(double angle) {
		turnAngle(angle, Constants.PID_TIMEOUT_S);
	}

	public void turnAngle(double angle, double timeout) {
		turnAngle(angle, timeout, true);
	}
	
	public void turnAngle(double angle, boolean reset) {
		turnAngle(angle, Constants.PID_TIMEOUT_S, reset);
	}

	/* Rotates to a certain angle
	 * If the timeout is gone over, the command will stop
	 * If reset is true, the angle sensor will be reset before driving
	 */
	public void turnAngle(double angle, double timeout, boolean reset) {
		EnhancedDashboard.putString("Auto Status", "Turning " + angle + " degrees");
		if (reset) resetAngle();
		distanceController.disable();
		maintainAngleController.disable();

		// Invoking PID sorcery

		distancePIDHelper.setAnglePIDOutput(null);
		anglePIDOutput.setDistancePIDHelper(null);

		turnAngleController.reset();
		turnAngleController.setSetpoint(angle);

		// The below line is what makes this actually turn

		turnAngleController.enable();

		waitUntilPIDSteady(turnAngleController, timeout);
		
		EnhancedDashboard.putString("Auto Status", "Turn Complete");
	}

	// Pauses robot execution until the PIDController finishes or the timeout is gone over
	public void waitUntilPIDSteady(PIDController controller, double timeout) {
		Timer pidTimer = new Timer();
		pidTimer.start();

		while (!controller.onTarget() && !pidTimer.hasPeriodPassed(timeout)) {
			Timer.delay(0.01);
		}

		pidTimer.stop();
		controller.disable();
	}
	
	// Time-based robot drive
	public void driveFor(double seconds, double speed)
	{
		arcadeDrive(speed, 0);
		Timer.delay(seconds);
		arcadeDrive(0, 0);
	}

	public double getLeftEncoder() {
		return frontLeftDrive.getSensorValueInches();
	}

	public double getRightEncoder() {
		return frontRightDrive.getSensorValueInches();
	}

	public double getLeftEncoderVelocity() {
		return frontLeftDrive.getSensorVelocityInchesPerSecond();
	}

	public double getRightEncoderVelocity() {
		return frontRightDrive.getSensorVelocityInchesPerSecond();
	}

	public double getAngle() {
		return angleSensorGroup.getAngle();
	}

	public void resetSensors() {
		resetEncoders();
		resetAngle();
	}

	public void resetEncoders() {
		frontLeftDrive.zeroSensor();
		frontRightDrive.zeroSensor();
	}

	public void resetAngle() {
		angleSensorGroup.reset();
	}
	
	public void disablePID() {
		distanceController.disable();
		maintainAngleController.disable();
		turnAngleController.disable();
	}

	public void outputInfo() {
		EnhancedDashboard.putNumber("Drive Left Distance", getLeftEncoder());
		EnhancedDashboard.putNumber("Drive Right Distance", getRightEncoder());

		EnhancedDashboard.putNumber("Drive Left Velocity", getLeftEncoderVelocity());
		EnhancedDashboard.putNumber("Drive Right Velocity", getRightEncoderVelocity());

		EnhancedDashboard.putNumber("Angle", getAngle());
		
		EnhancedDashboard.putNumber("Angle PID Setpoint", turnAngleController.getSetpoint());
		EnhancedDashboard.putNumber("Distance PID Setpoint", distanceController.getSetpoint());
	}
}
