// Importing all the packages required

package org.usfirst.frc.team1257.robot;

import org.usfirst.frc.team1257.robot.auto.*;
import org.usfirst.frc.team1257.robot.subsystems.*;
import org.usfirst.frc.team1257.robot.subsystems.Intake.ClawPosition;
import org.usfirst.frc.team1257.util.EnhancedDashboard;
import org.usfirst.frc.team1257.util.SnailController;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

// Public class Robot is like public statis void Main in Java or int main in C++.
// Virtually all of our robot code is in this class.

public class Robot extends IterativeRobot {

	// We define a lot of stuff in here.

	// DriveTrain is the object we use to drive the robot.
	// Elevator is the object we use to raise and lower the claws.
	// Linkage is the object we use to rotate the claws.
	// Intake is the object we use to take cubes inand out.

	DriveTrain driveTrain;
	Elevator elevator;
	Linkage linkage;
	Intake intake;

	// These controllers all extend an Xbox controller.
	// In other words, this is for the drive team.
	// They can now control the robot using an Xbox controller.

	SnailController driveController;
	SnailController operatorController;
	
	SendableChooser<Constants.AutoPosition> autoPositionChooser;
	SendableChooser<Constants.AutoObjective> autoObjectiveChooser;

	@Override
	public void robotInit() {

		// Reset objects. All of them.

		driveTrain = DriveTrain.getInstance();
		elevator = Elevator.getInstance();
		linkage = Linkage.getInstance();
		intake = Intake.getInstance();

		driveController = new SnailController(0);
		operatorController = new SnailController(1);

		// Auto has multiple permutations. 
		// This function helps ensure that we're running the correct one.
		
		configAutoChoosers();
		
		// Initialize all SmartDashboard values
		outputInfo();
		EnhancedDashboard.putString("Auto Status");
		EnhancedDashboard.putNumber("Auto Delay");
		
		// Configure camera stream in a separate thread
		new Thread(() ->  {
			try {
				AxisCamera camera = CameraServer.getInstance().addAxisCamera("10.12.57.11");
				camera.setResolution(640,  480);
			}
			catch(Exception e) {
				DriverStation.reportError("Can't connect to axis camera", true);
			}
		}).start();
	}
	
	// Configures the SendableChoosers for selecting auto paths
	private void configAutoChoosers() {
		autoPositionChooser = new SendableChooser<Constants.AutoPosition>();
		autoObjectiveChooser = new SendableChooser<Constants.AutoObjective>();

		autoPositionChooser.addObject("Left Start", Constants.AutoPosition.LEFT);
		autoPositionChooser.addDefault("Middle Start", Constants.AutoPosition.MIDDLE);
		autoPositionChooser.addObject("Right Start", Constants.AutoPosition.RIGHT);
		
		autoObjectiveChooser.addDefault("Default", Constants.AutoObjective.DEFAULT);
		autoObjectiveChooser.addObject("Switch", Constants.AutoObjective.SWITCH);
		autoObjectiveChooser.addObject("Scale", Constants.AutoObjective.SCALE);
		autoObjectiveChooser.addObject("Baseline", Constants.AutoObjective.BASELINE);

		EnhancedDashboard.putData(autoPositionChooser);
		EnhancedDashboard.putData(autoObjectiveChooser);
	}

	// This is the code we run in autonomous.
	@Override
	public void autonomousInit() {
		resetAll();
		
		//Grab Game Data
		String gameData = waitForGameData();
		if(gameData.equals("ERROR")) {
			Baseline.run(driveTrain, elevator, intake, linkage);
			return;
		}
		
		Timer.delay(EnhancedDashboard.getNumber("Auto Delay"));
	
		Constants.AutoPosition position = autoPositionChooser.getSelected();

		char switchPosition = gameData.charAt(0);
		char scalePosition = gameData.charAt(1);

		// Run different variations of the same code based on the autonomous permutations.
		// Side Position
		if(position == Constants.AutoPosition.LEFT || position == Constants.AutoPosition.RIGHT) {
			char startPosition = position == Constants.AutoPosition.LEFT ? 'L' : 'R';
			
			switch(autoObjectiveChooser.getSelected()) {
				case SWITCH:
					SideSwitch.run(startPosition, switchPosition, driveTrain, elevator, intake, linkage);
					break;
				case SCALE:
					Scale.run(startPosition, scalePosition, driveTrain, elevator, intake, linkage);
					break;
				case BASELINE:
					Baseline.run(driveTrain, elevator, intake, linkage);
					break;
				case DEFAULT: // Purposely fall through into default case
				default:
					DefaultPath.run(startPosition, switchPosition, scalePosition, 
							driveTrain, elevator, intake, linkage);
					break;
			}
		}
		// Middle Position
		else {
			MiddlePath.run(gameData.charAt(0), driveTrain, elevator, intake, linkage);
		}
	}
	
	private String waitForGameData() {
		String gameData = "";
		Timer gameDataTimer = new Timer();
		gameDataTimer.start();
		while(gameData.length() == 0 && !gameDataTimer.hasPeriodPassed(Constants.GAME_DATA_TIMEOUT_S)) {
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			Timer.delay(0.05);
		}
		gameDataTimer.stop();
		
		if(gameData.length() == 0)
		{
			DriverStation.reportError("Unable to read game data. Driving to Baseline", true);
			return "ERROR";
		}
		
		return gameData;
	}

	@Override
	public void autonomousPeriodic() {
		outputInfo();
	}

	@Override
	public void teleopInit() {
		// We don't want autonomous code running during teleoperated.
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
		if (operatorController.getBumper(Hand.kRight))
			intake.setClaw(ClawPosition.CLOSED);
		else if (operatorController.getBumper(Hand.kLeft)) {
			intake.setIntake(Constants.INTAKE_SPEED);
			intake.setClaw(ClawPosition.OPEN);
		}
		else if (operatorController.getBButton())
			intake.setIntake(Constants.INTAKE_SPEED);
		else if (operatorController.getAButton())
			intake.setIntake(-Constants.INTAKE_SPEED);
		else
			intake.setIntake(Constants.deadband(operatorController.getY(Hand.kRight)));

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
		driveTrain.disablePID();
	}

	private void zeroMotors() {
		driveTrain.arcadeDrive(0, 0);
		elevator.setElevator(0, 0, false);
		linkage.setLinkage(0);
		intake.setIntake(0);
	}
}
