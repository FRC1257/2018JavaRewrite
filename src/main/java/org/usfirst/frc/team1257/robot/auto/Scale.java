package org.usfirst.frc.team1257.robot.auto;

import org.usfirst.frc.team1257.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1257.robot.subsystems.Elevator;
import org.usfirst.frc.team1257.robot.subsystems.Intake;
import org.usfirst.frc.team1257.robot.subsystems.Linkage;
import org.usfirst.frc.team1257.util.EnhancedDashboard;

public class Scale {
	public static void run(char startPosition, char scalePosition, DriveTrain driveTrain, 
			Elevator elevator, Intake intake, Linkage linkage) {
		EnhancedDashboard.putString("Auto Path", "Scale from " + startPosition + " to " + scalePosition);
		
		double angle = startPosition == 'L' ? 90 : -90;
		
		if(startPosition == scalePosition) {
			// Drives 257 feet, turns, then drives 10 feet
			
			driveTrain.driveDistance(257);
			driveTrain.turnAngle(angle / 2.0);
			
			driveTrain.driveDistance(10);
			
			//Raise Elevator
			intake.ejectCube();
			//Lower Elevator
		}
		else {
			double secondAngle = startPosition == 'L' ? -120 : 120;
			
			driveTrain.driveDistance(215.4);
			driveTrain.turnAngle(angle);
			
			driveTrain.driveDistance(245);
			driveTrain.turnAngle(secondAngle);
			
			driveTrain.driveDistance(55.25);
			
			//Raise Elevator
			intake.ejectCube();
			//Lower Elevator
		}
		
		EnhancedDashboard.putString("Auto Path", "Finished Scale");
	}
}
